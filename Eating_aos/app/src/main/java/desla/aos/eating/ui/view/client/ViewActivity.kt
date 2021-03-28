package desla.aos.eating.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.R
import desla.aos.eating.data.model.PostsResponse
import desla.aos.eating.data.network.ServerApi
import desla.aos.eating.data.repositories.PostRepository
import desla.aos.eating.data.repositories.ViewRepository
import desla.aos.eating.databinding.ActivityPostBinding
import desla.aos.eating.databinding.ActivityViewBinding
import desla.aos.eating.ui.base.BaseActivity
import desla.aos.eating.ui.post.PostViewModel
import desla.aos.eating.ui.post.PostViewModelFactory

class ViewActivity : BaseActivity<ActivityViewBinding>() {


    override val layoutResourceId: Int
        get() = R.layout.activity_view

    private lateinit var factory: ViewViewModelFactory
    private lateinit var viewModel: ViewViewModel


    override fun initStartView() {

        val server = ServerApi()
        val repository = ViewRepository(server)
        factory = ViewViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(ViewViewModel::class.java)

        viewModel.beforeData = intent.getSerializableExtra("data") as PostsResponse.Data
        viewDataBinding.vm = viewModel

    }

    override fun initDataBinding() {

        viewModel.getDetailPost()
        viewModel.data.observe(this, Observer {
            viewDataBinding.data = it
        })
    }

    override fun initAfterBinding() {

    }


}