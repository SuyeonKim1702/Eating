package desla.aos.eating.ui.view.client

import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.R
import desla.aos.eating.data.model.PostsResponse
import desla.aos.eating.data.network.ServerApi
import desla.aos.eating.data.repositories.ViewRepository
import desla.aos.eating.databinding.ActivityViewBinding
import desla.aos.eating.ui.base.BaseActivity
import desla.aos.eating.util.MyTimeUtils
import kotlinx.android.synthetic.main.fragment_login.*

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
            viewDataBinding.tvTimeView.text = MyTimeUtils.twoDatesBetweenTimeInView(it.orderTime)
        })

        Log.i("eunjin",  "현재" + viewModel.beforeData?.favorite!!+ " ${viewModel.beforeData?.postId}")
        viewDataBinding.btnFavorite.isSelected = viewModel.beforeData?.favorite!!
        viewModel.favorite.observe(this, Observer {
            viewDataBinding.btnFavorite.isSelected = it
        })

        viewModel.message.observe(this, Observer {
            showToast(it)
        })


    }

    override fun initAfterBinding() {

    }


}