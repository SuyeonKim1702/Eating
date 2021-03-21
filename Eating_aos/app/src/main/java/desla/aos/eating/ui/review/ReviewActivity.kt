package desla.aos.eating.ui.review

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import desla.aos.eating.R
import desla.aos.eating.databinding.ActivityLoginBinding
import desla.aos.eating.databinding.ActivityReviewBinding
import desla.aos.eating.ui.base.BaseActivity
import desla.aos.eating.ui.login.LoginFragment
import desla.aos.eating.ui.login.LoginViewModel
import desla.aos.eating.ui.login.RegisterFragment
import desla.aos.eating.util.getActivity

class ReviewActivity : BaseActivity<ActivityReviewBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_review

    private val viewModel: ReviewViewModel by viewModels()


    override fun initStartView() {
        viewModel.showFirstDialog(this)

    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {

    }



}