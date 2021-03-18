package desla.aos.eating.ui.home.filter

import android.animation.ValueAnimator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.R
import desla.aos.eating.data.repositories.FilterRepository
import desla.aos.eating.databinding.FragmentFilterBinding
import desla.aos.eating.ui.MainActivity
import desla.aos.eating.ui.base.BaseFragment
import kotlinx.android.synthetic.main.activity_view.*
import kotlinx.android.synthetic.main.fragment_filter.*


class FilterFragment :  BaseFragment<FragmentFilterBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_filter

    private lateinit var factory: FilterViewModelFactory
    private lateinit var viewModel: FilterViewModel

    private val TAG = "HomeFragment"

    override fun initStartView() {
        (activity as MainActivity).setVisibilityBottomNavigation(false)

        val repository = FilterRepository()
        factory = FilterViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(FilterViewModel::class.java)
        //viewDataBinding.viewModel = viewModel
    }

    override fun initDataBinding() {
        initAnimation()
    }

    override fun initAfterBinding() {

    }

    private fun initAnimation(){
        val anim = ValueAnimator.ofFloat(1.0f, 0.3f)
        anim.duration = 1000
        anim.addUpdateListener { animation ->
            val set = ConstraintSet()
            set.clone(activity_constraint)
            set.setHorizontalBias(R.id.red, animation.animatedValue as Float)
            set.applyTo(activity_constraint)



        }
        anim.start()
    }


}