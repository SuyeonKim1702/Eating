package desla.aos.eating.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.R
import desla.aos.eating.data.network.ServerApi
import desla.aos.eating.data.repositories.SettingRepository
import desla.aos.eating.data.repositories.UserRepository
import desla.aos.eating.databinding.FragmentMyListBinding
import desla.aos.eating.databinding.FragmentSettingBinding
import desla.aos.eating.ui.MainActivity
import desla.aos.eating.ui.base.BaseFragment
import desla.aos.eating.ui.user.mylist.MyListViewModel
import desla.aos.eating.ui.user.mylist.MyListViewModelFactory


class SettingFragment : BaseFragment<FragmentSettingBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_setting

    private lateinit var factory: SettingViewModelFactory
    private lateinit var viewModel: SettingViewModel

    private val TAG = "HomeFragment"

    override fun initStartView() {
        (activity as MainActivity).setVisibilityBottomNavigation(false)

        val serverApi = ServerApi()
        val repository = SettingRepository(serverApi)
        factory = SettingViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(SettingViewModel::class.java)
        //viewDataBinding.viewModel = viewModel
    }

    override fun initDataBinding() {



    }

    override fun initAfterBinding() {

    }


}