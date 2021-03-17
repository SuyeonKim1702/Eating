package desla.aos.eating.ui.login

import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.R
import desla.aos.eating.data.repositories.UserRepository
import desla.aos.eating.databinding.FragmentLoginBinding
import desla.aos.eating.ui.base.BaseFragment

class LoginFragment :  BaseFragment<FragmentLoginBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_login

    private lateinit var viewModel: LoginViewModel

    private val TAG = "LoginFragment"

    override fun initStartView() {
        val repository = UserRepository()
        viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
        viewDataBinding.vm = viewModel
    }

    override fun initDataBinding() {



    }

    override fun initAfterBinding() {

    }




}