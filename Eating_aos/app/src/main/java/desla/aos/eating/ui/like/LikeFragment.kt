package desla.aos.eating.ui.like

import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.R
import desla.aos.eating.data.repositories.HomeRepository
import desla.aos.eating.data.repositories.LikeRepository
import desla.aos.eating.data.repositories.UserRepository
import desla.aos.eating.databinding.FragmentHomeBinding
import desla.aos.eating.ui.base.BaseFragment

class LikeFragment :  BaseFragment<FragmentHomeBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_like

    private lateinit var factory: LikeViewModelFactory
    private lateinit var viewModel: LikeViewModel

    private val TAG = "HomeFragment"

    override fun initStartView() {
        val repository = LikeRepository()
        factory = LikeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(LikeViewModel::class.java)
        //viewDataBinding.viewModel = viewModel
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }


}