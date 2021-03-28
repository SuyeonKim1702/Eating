package desla.aos.eating.ui.login

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import desla.aos.eating.ui.base.BaseViewModel
import desla.aos.eating.util.CameraUtil
import desla.aos.eating.util.getActivity
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import desla.aos.eating.data.repositories.LoginRepository
import desla.aos.eating.data.repositories.MapRepository
import desla.aos.eating.ui.MyApplication
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody

class LoginViewModel(val repository: LoginRepository) : BaseViewModel()  {

    private val TAG = "LoginViewModel"

    private val _isLogin = MutableLiveData<Int>()
    val isLogin : LiveData<Int>
        get() = _isLogin

    var kakao_id: String? = null
    var nickname: String? = null


    //사진 찍기
    val REQUEST_IMAGE_CAPTURE = 10
    val REQUEST_IMAGE_PICK = 11


    fun startMapActivity(v: View){

        if(MyApplication.prefs.isRegister()){
            sendLogin()
        }else{
            _isLogin.value = 1
        }

    }


    private fun sendLogin(){


        val params:HashMap<String, Any> = HashMap<String, Any>()
        params["kakaoId"] = MyApplication.prefs.getString("id", "id")


        Log.i("eunjin", "전송 ${params}" )

        val disposable = repository.postLogin(
            params
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.isSuccessful){
                    if(it.body()?.status == "OK"){
                        Log.i("eunjin", "로그인 성공 ${it.code()} ${params["kakaoId"]} ${params["nickname"]}" )

                        _isLogin.value = 2
                    }else{
                        Log.i("eunjin", "로그인 실패1 ${it.body()?.status} ${it.body()?.message} ${params["nickname"]}" )
                    }

                }else{
                    Log.i("eunjin", "${it.message()} ${it.code()} 로그인 실패2 ${it.body()?.message} ${it.body()?.status}" )
                }

            }, {

                Log.i("eunjin", "가입 실패3" )
            })
        addDisposable(disposable)

    }




    fun setPermission(v: View) {
        val permission = object : PermissionListener {
            override fun onPermissionGranted() {//설정해 놓은 위험권한(카메라 접근 등)이 허용된 경우 이곳을 실행
                showChoicePhotoDialog(v.context)

            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {//설정해 놓은 위험권한이 거부된 경우 이곳을 실행
                Toast.makeText(v.context,"요청하신 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        TedPermission.with(v.context)
                .setPermissionListener(permission)
                .setRationaleMessage("카메라 앱을 사용하시려면 권한을 허용해주세요.")
                .setDeniedMessage("권한을 거부하셨습니다.앱을 사용하시려면 [앱 설정]-[권한] 항목에서 권한을 허용해주세요.")
                .setPermissions(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA)
                .check()
    }

    private fun showChoicePhotoDialog(context: Context){

        val dlg = CameraDialog(context)
        dlg.setDialogListener(object : CameraDialog.CameraDialogListener{
            override fun onPositiveClicked(pos: Int) {
                when(pos){
                    1 -> CameraUtil.getInstance(context).dispatchTakePictureIntent()
                    2 ->  openGallery(context)
                }
            }

        })
        dlg.start()

    }


    private fun openGallery(context: Context) {
        val activity = context.getActivity()

        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = MediaStore.Images.Media.CONTENT_TYPE
        //galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        activity?.startActivityForResult(galleryIntent, REQUEST_IMAGE_PICK)
    }


    fun kakaoSignIn(v: View){
        // 로그인 공통 callback 구성

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->

            if (error != null) {
                Log.e(TAG, "로그인 실패", error)
                _message.value = "로그인 실패"
            }

            else if (token != null) {
                Log.i(TAG, "로그인 성공 ${token.accessToken}")
                getKakaoUserId()
            }

        }

        val context = v.context

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (LoginClient.instance.isKakaoTalkLoginAvailable(context)) {
            LoginClient.instance.loginWithKakaoTalk(context, callback = callback)
        } else {
            LoginClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }


    private fun getKakaoUserId(){
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Log.e(TAG, "토큰 정보 보기 실패", error)
                _message.value = "로그인 실패 : {$error.message.toString()}"
            }
            else if (tokenInfo != null) {
                Log.i(TAG, "토큰 정보 보기 성공" +
                        "\n회원번호: ${tokenInfo.id}" +
                        "\n만료시간: ${tokenInfo.expiresIn} 초")

                kakao_id = "" + tokenInfo.id
                if(MyApplication.prefs.isRegister()){
                    sendLogin()
                }else{
                    _isLogin.value = 0
                }


            }
        }
    }

}