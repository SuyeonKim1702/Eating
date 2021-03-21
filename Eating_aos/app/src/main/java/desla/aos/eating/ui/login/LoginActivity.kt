package desla.aos.eating.ui.login

import android.app.Activity
import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import desla.aos.eating.R
import desla.aos.eating.databinding.ActivityLoginBinding
import desla.aos.eating.ui.MainActivity
import desla.aos.eating.ui.base.BaseActivity
import desla.aos.eating.ui.map.MapActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_login

    private val viewModel: LoginViewModel by viewModels()

    private val loginFragment: LoginFragment =
            LoginFragment()
    private val registerFragment: RegisterFragment =
            RegisterFragment()

    //사진 찍기
    val REQUEST_IMAGE_CAPTURE = 10
    val REQUEST_IMAGE_PICK = 11

    override fun initStartView() {
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
                    startActivity(Intent(this, MapActivity::class.java))
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