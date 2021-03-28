package desla.aos.eating.ui.home.filter

import android.animation.ValueAnimator
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.R
import desla.aos.eating.data.repositories.FilterRepository
import desla.aos.eating.databinding.FragmentFilterBinding
import desla.aos.eating.ui.MainActivity
import desla.aos.eating.ui.base.BaseFragment
import desla.aos.eating.ui.custom.CustomFilterButton
import kotlinx.android.synthetic.main.activity_view.*
import kotlinx.android.synthetic.main.dialog_category.*
import kotlinx.android.synthetic.main.fragment_filter.*
import kotlinx.android.synthetic.main.fragment_filter.btn1
import kotlinx.android.synthetic.main.fragment_filter.btn10
import kotlinx.android.synthetic.main.fragment_filter.btn2
import kotlinx.android.synthetic.main.fragment_filter.btn3
import kotlinx.android.synthetic.main.fragment_filter.btn4
import kotlinx.android.synthetic.main.fragment_filter.btn5
import kotlinx.android.synthetic.main.fragment_filter.btn6
import kotlinx.android.synthetic.main.fragment_filter.btn7
import kotlinx.android.synthetic.main.fragment_filter.btn8
import kotlinx.android.synthetic.main.fragment_filter.btn9


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



    }

    override fun initAfterBinding() {
        btn1.setOnClickListener {
            clicked(it, 0)
        }

        btn2.setOnClickListener {
            clicked(it, 1)
        }

        btn3.setOnClickListener {
            clicked(it, 2)
        }
        btn4.setOnClickListener {
            clicked(it, 3)
        }

        btn5.setOnClickListener {
            clicked(it, 4)
        }

        btn6.setOnClickListener {
            clicked(it, 5)
        }

        btn7.setOnClickListener {
            clicked(it, 6)
        }

        btn8.setOnClickListener {
            clicked(it, 7)
        }
        btn9.setOnClickListener {
            clicked(it, 8)
        }

        btn10.setOnClickListener {
            clicked(it, 9)
        }
    }


    private fun clicked(view: View, num: Int){
        (view as CustomFilterButton).let {
            it.setNotSelected(!it.getSelected())
        }

    }


}