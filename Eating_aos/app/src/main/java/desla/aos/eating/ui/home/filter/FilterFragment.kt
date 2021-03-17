package desla.aos.eating.ui.home.filter

import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.R
import desla.aos.eating.data.repositories.FilterRepository
import desla.aos.eating.data.repositories.HomeRepository
import desla.aos.eating.data.repositories.UserRepository
import desla.aos.eating.databinding.FragmentFilterBinding
import desla.aos.eating.databinding.FragmentHomeBinding
import desla.aos.eating.ui.base.BaseFragment
import java.util.logging.Filter

class FilterFragment :  BaseFragment<FragmentFilterBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_filter

    private lateinit var factory: FilterViewModelFactory
    private lateinit var viewModel: FilterViewModel

    private val TAG = "HomeFragment"

    override fun initStartView() {
        val repository = FilterRepository()
        factory = FilterViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(FilterViewModel::class.java)
        //viewDataBinding.viewModel = viewModel
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }


}