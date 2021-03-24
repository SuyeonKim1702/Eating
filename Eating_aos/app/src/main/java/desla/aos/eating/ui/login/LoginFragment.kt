package desla.aos.eating.ui.login

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.R
import desla.aos.eating.data.repositories.UserRepository
import desla.aos.eating.databinding.FragmentLoginBinding
import desla.aos.eating.ui.base.BaseFragment

class LoginFragment :  BaseFragment<FragmentLoginBinding>() {

    //사진 찍기
    val REQUEST_IMAGE_CAPTURE = 10
    val REQUEST_IMAGE_PICK = 11

    //위치 MapActivity에서 결과 값 갖고오기
    val REQUEST_MAP_DATA = 12

    override val layoutResourceId: Int
        get() = R.layout.fragment_login

    val viewModel by activityViewModels<LoginViewModel>()

    private val TAG = "LoginFragment"

    override fun initStartView() {

        viewDataBinding.vm = viewModel
    }

    override fun initDataBinding() {



    }

    override fun initAfterBinding() {

    }




}