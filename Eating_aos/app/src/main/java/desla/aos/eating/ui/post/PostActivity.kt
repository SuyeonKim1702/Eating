package desla.aos.eating.ui.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import desla.aos.eating.R
import desla.aos.eating.databinding.ActivityPostBinding
import desla.aos.eating.ui.base.BaseActivity
import desla.aos.eating.ui.map.MapViewModel

class PostActivity :  BaseActivity<ActivityPostBinding>() {


    override val layoutResourceId: Int
        get() = R.layout.activity_post

    private val viewModel: PostViewModel by viewModels()

    override fun initStartView() {

        viewDataBinding.vm = viewModel
    }

    override fun initDataBinding() {


    }

    override fun initAfterBinding() {

    }

    //배달비
    fun setPrice(v: View){
        when(v){

        }

    }



}