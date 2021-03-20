package desla.aos.eating.ui.map

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import desla.aos.eating.R
import desla.aos.eating.databinding.FragmentMapBinding
import desla.aos.eating.ui.base.BaseFragment
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


class MapFragment :  BaseFragment<FragmentMapBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_map

    private lateinit var viewModel: MapViewModel

    private val TAG = "MapFragment"

    //마지막 위치
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

    }

    override fun initStartView() {

        viewModel = ViewModelProvider(requireActivity())[MapViewModel::class.java]
        viewDataBinding.vm = viewModel
    }

    override fun initDataBinding() {

        viewModel.mapView = MapView(activity).also {
            viewDataBinding.mapView.addView(it)
        }

        viewModel.initMap()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out kotlin.String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        viewModel.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun initAfterBinding() {

        viewModel.message.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })

        viewDataBinding.button.setOnClickListener {


        }

        viewDataBinding.tvAddress.setOnClickListener {

            val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.map_frame_layout, MapSearchFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        viewModel.selectLocation.observe(this, Observer {v ->

            if(v.Title.isNullOrEmpty()){
                viewDataBinding.etvAddress.text = v.Address
            }else{
                viewDataBinding.etvAddress.text = v.Title
            }

            removeMarker()

            val mapPoint = MapPoint.mapPointWithGeoCoord(v.y, v.x)
            addMarker(mapPoint)
            viewModel.mapView.setMapCenterPointAndZoomLevel(mapPoint, 2 , true)
        })
    }

    private fun removeMarker(){
        viewModel.mapView.removeAllPOIItems()
    }

    private fun addMarker(MARKER_POINT: MapPoint){
        val marker = MapPOIItem()
        marker.itemName = "Custom Marker"
        marker.tag = 0
        marker.mapPoint = MARKER_POINT
        marker.markerType = MapPOIItem.MarkerType.CustomImage // 마커타입을 커스텀 마커로 지정.
        marker.customImageResourceId = R.drawable.ic_my_location2 // 마커 이미지.
        marker.isCustomImageAutoscale = true // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
        marker.setCustomImageAnchor(0.5f, 1.0f) // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.


        viewModel.mapView.addPOIItem(marker)
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onActivityResult(requestCode, resultCode, data)
    }




    override fun onDestroy() {
        super.onDestroy()

        viewDataBinding.mapView.removeAllViews()
    }


}