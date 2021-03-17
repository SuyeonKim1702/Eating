package desla.aos.eating.ui.like

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import desla.aos.eating.R
import desla.aos.eating.data.model.Post
import desla.aos.eating.data.repositories.HomeRepository
import desla.aos.eating.data.repositories.LikeRepository
import desla.aos.eating.data.repositories.UserRepository
import desla.aos.eating.databinding.FragmentHomeBinding
import desla.aos.eating.ui.base.BaseFragment
import desla.aos.eating.ui.home.HomeRCAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_like.*

class LikeFragment :  BaseFragment<FragmentHomeBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_like

    private lateinit var factory: LikeViewModelFactory
    private lateinit var viewModel: LikeViewModel

    private val TAG = "HomeFragment"

    private val chat: MutableList<Post> = mutableListOf()
    private val like: MutableList<Post> = mutableListOf()

    override fun initStartView() {
        val repository = LikeRepository()
        factory = LikeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(LikeViewModel::class.java)
        //viewDataBinding.viewModel = viewModel
    }

    override fun initDataBinding() {

    }

    override fun initAfterBinding() {
        chat.add(Post(5, 100, "공차 같이 드실 분1"))
        chat.add(Post(6, 100, "신전 같이 드실 분2"))
        chat.add(Post(7, 100, "엽떡 같이 드실 분3"))
        chat.add(Post(8, 100, "치킨 같이 드실 분4"))

        like.add(Post(5, 100, "공차 같이 드실 분1"))
        like.add(Post(6, 100, "신전 같이 드실 분2"))
        like.add(Post(7, 100, "엽떡 같이 드실 분3"))
        like.add(Post(8, 100, "치킨 같이 드실 분4"))

        initRc()
    }

    private fun initRc(){
        rc_chat.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rc_chat.setHasFixedSize(true)
        rc_chat.adapter = LikeRCAdapter(chat, like)

    }

}