package desla.aos.eating.ui.user

import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.R
import desla.aos.eating.data.repositories.HomeRepository
import desla.aos.eating.data.repositories.UserRepository
import desla.aos.eating.databinding.FragmentHomeBinding
import desla.aos.eating.ui.MainActivity
import desla.aos.eating.ui.base.BaseFragment

class UserFragment :  BaseFragment<FragmentHomeBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_user

    private lateinit var factory: UserViewModelFactory
    private lateinit var viewModel: UserViewModel

    private val TAG = "HomeFragment"

    override fun initStartView() {
        (activity as MainActivity).setVisibilityBottomNavigation(true)

        val repository = UserRepository()
        factory = UserViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
        //viewDataBinding.viewModel = viewModel
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }


}