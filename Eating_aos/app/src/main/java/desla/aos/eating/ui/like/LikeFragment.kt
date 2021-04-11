package desla.aos.eating.ui.like

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import desla.aos.eating.R
import desla.aos.eating.data.model.Post
import desla.aos.eating.data.model.PostsResponse
import desla.aos.eating.data.network.ServerApi
import desla.aos.eating.data.repositories.HomeRepository
import desla.aos.eating.data.repositories.LikeRepository
import desla.aos.eating.data.repositories.UserRepository
import desla.aos.eating.databinding.FragmentHomeBinding
import desla.aos.eating.databinding.FragmentLikeBinding
import desla.aos.eating.ui.MainActivity
import desla.aos.eating.ui.base.BaseFragment
import desla.aos.eating.ui.home.HomeRCAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_like.*

class LikeFragment :  BaseFragment<FragmentLikeBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_like

    private lateinit var factory: LikeViewModelFactory
    private lateinit var viewModel: LikeViewModel

    private val TAG = "HomeFragment"

    private val chat: MutableList<PostsResponse.Data> = mutableListOf()
    private val like: MutableList<PostsResponse.Data> = mutableListOf()

    override fun initStartView() {
        (activity as MainActivity).setVisibilityBottomNavigation(true)

        val server = ServerApi()
        val repository = LikeRepository(server)
        factory = LikeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(LikeViewModel::class.java)
        viewDataBinding.vm = viewModel
    }

    override fun initDataBinding() {

        viewModel.partiList.observe(this, Observer {
            chat.clear()
            chat.addAll(it)
            viewDataBinding.rcChat.adapter?.notifyDataSetChanged()
        })

        viewModel.likeList.observe(this, Observer {
            like.clear()
            like.addAll(it)
            viewDataBinding.rcChat.adapter?.notifyDataSetChanged()
        })

    }

    override fun initAfterBinding() {

        viewModel.getPartiPost()
        viewModel.getFavoritePost()
        initRc()
    }

    private fun initRc(){
        rc_chat.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rc_chat.setHasFixedSize(true)
        rc_chat.adapter = LikeRCAdapter(chat, like)

    }

}