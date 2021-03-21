package desla.aos.eating.ui.user

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.R
import desla.aos.eating.data.repositories.HomeRepository
import desla.aos.eating.data.repositories.UserRepository
import desla.aos.eating.databinding.FragmentHomeBinding
import desla.aos.eating.databinding.FragmentUserBinding
import desla.aos.eating.ui.MainActivity
import desla.aos.eating.ui.base.BaseFragment
import desla.aos.eating.ui.map.MapActivity
import desla.aos.eating.ui.review.ReviewActivity
import kotlinx.android.synthetic.main.activity_post.*

class UserFragment :  BaseFragment<FragmentUserBinding>() {

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

        //임시

        viewDataBinding.btnSettingUser.setOnClickListener {
            startActivity(Intent(it.context, ReviewActivity::class.java))
        }
    }

    override fun initAfterBinding() {

    }


}