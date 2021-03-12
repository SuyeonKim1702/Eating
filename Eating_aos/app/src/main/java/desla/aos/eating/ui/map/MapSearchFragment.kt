package desla.aos.eating.ui.map

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.R
import desla.aos.eating.data.repositories.UserRepository
import desla.aos.eating.databinding.FragmentLoginBinding
import desla.aos.eating.databinding.FragmentMapSearchBinding
import desla.aos.eating.ui.base.BaseFragment

class MapSearchFragment :  BaseFragment<FragmentMapSearchBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_map_search

    private lateinit var viewModel: MapViewModel

    private val TAG = "LoginFragment"

    override fun initStartView() {

        viewModel = ViewModelProvider(requireActivity())[MapViewModel::class.java]
        viewDataBinding.vm = viewModel
    }

    override fun initDataBinding() {

        viewModel.addressList.observe(this, Observer {

            if(it.meta.totalCount > 0){
                activity?.onBackPressed()
            }

        })

    }

    override fun initAfterBinding() {

    }




}