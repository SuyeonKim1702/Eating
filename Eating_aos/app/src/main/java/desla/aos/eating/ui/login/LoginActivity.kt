package desla.aos.eating.ui.login

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.R
import desla.aos.eating.data.network.KakaoMapApi
import desla.aos.eating.data.network.ServerApi
import desla.aos.eating.data.repositories.LoginRepository
import desla.aos.eating.data.repositories.MapRepository
import desla.aos.eating.databinding.ActivityLoginBinding
import desla.aos.eating.ui.MainActivity
import desla.aos.eating.ui.MyApplication
import desla.aos.eating.ui.base.BaseActivity
import desla.aos.eating.ui.map.MapActivity
import desla.aos.eating.ui.map.MapViewModel
import desla.aos.eating.ui.map.MapViewModelFactory

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_login

    private lateinit var factory: LoginViewModelFactory
    private lateinit var viewModel: LoginViewModel

    private val loginFragment: LoginFragment =
            LoginFragment()
    private val registerFragment: RegisterFragment =
            RegisterFragment()

    //사진 찍기
    val REQUEST_IMAGE_CAPTURE = 10
    val REQUEST_IMAGE_PICK = 11

    override fun initStartView() {

        val server = ServerApi()
        val repository = LoginRepository(server)
        factory = LoginViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        setFragment(loginFragment)
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        viewModel.isLogin.observe(this, Observer {isLogin ->

            println("kakao " + isLogin)

            when(isLogin){
                0 ->  setRegisterFragment()
                1 -> {
                    MyApplication.prefs.setUserInfo(viewModel.kakao_id!!, viewModel.nickname!!)
                    val intent = Intent(this, MapActivity::class.java)
                    intent.putExtra("isRegister", true)
                    startActivity(intent)
                    finish()
                }
                2 -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                else ->{

                }
            }

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            //사진찍은거 파일로 저장하고 가져오기
            registerFragment.onActivityResult(requestCode, resultCode, data)


        }

        //사진을 갤러리에서 갖고왔을 때
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_IMAGE_PICK && data != null) {
            registerFragment.onActivityResult(requestCode, resultCode, data)

        }
    }

    private fun setRegisterFragment(){
        setFragment(registerFragment)
    }

    private fun setFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.login_frame_layout, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft.commit()
    }
}