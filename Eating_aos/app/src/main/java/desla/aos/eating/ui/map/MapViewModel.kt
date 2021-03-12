package desla.aos.eating.ui.map

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import desla.aos.eating.data.model.AddressAPI
import desla.aos.eating.data.model.GeoAPI
import desla.aos.eating.data.repositories.MapRepository
import desla.aos.eating.ui.base.BaseViewModel
import desla.aos.eating.util.getActivity
import desla.aos.eating.util.startGPSSettingActivityForResult
import desla.aos.eating.util.startMainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.daum.mf.map.api.MapView


class MapViewModel(val repository: MapRepository) : BaseViewModel() {

    private val TAG = "MapViewModel"

    lateinit var mapView: MapView

    private val GPS_ENABLE_REQUEST_CODE = 2001
    private val PERMISSIONS_REQUEST_CODE = 2002
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)



    fun initMap(){


        if (!checkLocationServicesStatus(mapView.context)) {
            showDialogForLocationServiceSetting(mapView.context)
        }else {
            checkRunTimePermission(mapView.context)
        }

    }

    fun startMainActivity(v: View){
        v.context.getActivity()?.startMainActivity()
    }

    var searchText = ""

    val clicksListener = object : TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH  -> {
                    getLocationList(searchText)
                }
                else -> return false
            }
            return true
        }

    }


    private val _addressList = MutableLiveData<AddressAPI>()
    val addressList : LiveData<AddressAPI>
        get() = _addressList

    fun getLocationList(query: String){

        val disposable = repository.getAddressWithQuery(query ,
                "KakaoAK fc1e18a34b2c31ec6d45168ef4e15284")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    _addressList.value = it
                }, {
                })
        addDisposable(disposable)

    }

    private val _address = MutableLiveData<GeoAPI>()
    val address : LiveData<GeoAPI>
        get() = _address

    var x = 0.0
    var y = 0.0

    fun getAddressWithGeo(x: Double, y: Double){

        if(this.x == x && this.y == y) return

        this.x = x
        this.y = y

            val disposable = repository.getAddressWithGeo(y, x,
                    "KakaoAK fc1e18a34b2c31ec6d45168ef4e15284")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({

                        _address.value = it

                    }, {

                        println(it.message)

                    })
            addDisposable(disposable)


    }



    private fun checkLocationServicesStatus(context: Context) : Boolean {

        val locationManager = context.getActivity()?.getSystemService(LOCATION_SERVICE) as LocationManager

        return  locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

    }

    private fun showDialogForLocationServiceSetting(context: Context){

        val builder = AlertDialog.Builder(context)
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n")
        builder.setCancelable(true)
        builder.setPositiveButton("설정", DialogInterface.OnClickListener { dialog, which ->

            val callGPSSettingIntent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            context.startGPSSettingActivityForResult()
        })
        builder.setNegativeButton("취소", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel()
        })
        builder.create().show()

    }

    private fun checkRunTimePermission(context: Context){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)

        val activity = context.getActivity()!!

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {
            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
            // 3.  위치 값을 가져올 수 있음

            getLocationData()


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.
            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, REQUIRED_PERMISSIONS[0])) {
                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                _message.value =  "이 앱을 실행하려면 위치 접근 권한이 필요합니다."
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(activity, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(activity, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }
        }


    }

    fun onRequestPermissionsResult(permsRequestCode: Int, permissions: Array<out kotlin.String>, grantResults: IntArray){
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grantResults.size == REQUIRED_PERMISSIONS.size) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            var check_result = true

            // 모든 퍼미션을 허용했는지 체크합니다.
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false
                    break
                }
            }
            if (check_result) {
                Log.d("@@@", "start")
                //위치 값을 가져올 수 있음

                getLocationData()


            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있다
                val activity = mapView.context.getActivity()!!

                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, REQUIRED_PERMISSIONS[0])) {
                    _message.value =  "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요."
                    activity.finish()
                } else {
                    _message.value =  "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다."
                }
            }
        }
    }


    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        when (requestCode) {
            GPS_ENABLE_REQUEST_CODE ->                 //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus(mapView.context)) {
                    if (checkLocationServicesStatus(mapView.context)) {
                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음")
                        checkRunTimePermission(mapView.context)
                        return
                    }
                }
        }
    }


    private fun getLocationData(){
        LocationHelper().startListeningUserLocation(mapView.context , object : LocationHelper.MyLocationListener {
            override fun onLocationChanged(location: Location) {
                // Here you got user location :)
                Log.d("Location","" + location.latitude + "," + location.longitude)
                getAddressWithGeo(location.latitude, location.longitude)
            }
        })

    }



}