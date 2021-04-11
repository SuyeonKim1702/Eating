package desla.aos.eating.ui.view.host

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.R
import desla.aos.eating.data.model.PostsResponse
import desla.aos.eating.data.network.ServerApi
import desla.aos.eating.data.repositories.ViewRepository
import desla.aos.eating.databinding.ActivityViewBinding
import desla.aos.eating.databinding.ActivityViewHostBinding
import desla.aos.eating.ui.base.BaseActivity
import desla.aos.eating.ui.view.client.ViewViewModel
import desla.aos.eating.ui.view.client.ViewViewModelFactory
import desla.aos.eating.util.MyTimeUtils

class ViewHostActivity : BaseActivity<ActivityViewHostBinding>() {


    override val layoutResourceId: Int
        get() = R.layout.activity_view_host

    private lateinit var factory: ViewHostModelFactory
    private lateinit var viewModel: ViewHostViewModel


    override fun initStartView() {

        val server = ServerApi()
        val repository = ViewRepository(server)
        factory = ViewHostModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(ViewHostViewModel::class.java)

        viewModel.beforeData = intent.getSerializableExtra("data") as PostsResponse.Data
        viewDataBinding.vm = viewModel

    }

    override fun initDataBinding() {

        viewModel.getDetailPost()
        viewModel.data.observe(this, Observer {
            viewDataBinding.data = it

            viewDataBinding.tvTimeViewHost.text = MyTimeUtils.twoDatesBetweenTimeInView(it.orderTime)
        })

        viewModel.message.observe(this, Observer {
            showToast(it)
        })
    }

    override fun initAfterBinding() {

    }


}