package desla.aos.eating.ui.home

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import desla.aos.eating.R
import desla.aos.eating.data.model.Post
import desla.aos.eating.data.repositories.HomeRepository
import desla.aos.eating.databinding.FragmentHomeBinding
import desla.aos.eating.ui.MainActivity
import desla.aos.eating.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment :  BaseFragment<FragmentHomeBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_home

    private lateinit var factory: HomeViewModelFactory
    private lateinit var viewModel: HomeViewModel

    private val TAG = "HomeFragment"

    private val posts: MutableList<Post> = mutableListOf()


    override fun initStartView() {
        (activity as MainActivity).setVisibilityBottomNavigation(true)

        val repository = HomeRepository()
        factory = HomeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        viewDataBinding.vm = viewModel
    }

    override fun initDataBinding() {

        posts.add(Post(1, 100, "공차 같이 드실 분"))
        posts.add(Post(2, 100, "신전 같이 드실 분"))
        posts.add(Post(3, 100, "엽떡 같이 드실 분"))
        posts.add(Post(4, 100, "치킨 같이 드실 분"))
        posts.add(Post(5, 100, "공차 같이 드실 분"))
        posts.add(Post(6, 100, "신전 같이 드실 분"))
        posts.add(Post(7, 100, "엽떡 같이 드실 분"))
        posts.add(Post(8, 100, "치킨 같이 드실 분"))

        initRc()
    }

    override fun initAfterBinding() {

        viewDataBinding.fabPost.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_postActivity)
        }

        viewDataBinding.btnFilter.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_filterFragment)
        }

    }


    private fun initRc(){
        rc_home.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rc_home.setHasFixedSize(true)
        rc_home.adapter = HomeRCAdapter(posts)
    }



}