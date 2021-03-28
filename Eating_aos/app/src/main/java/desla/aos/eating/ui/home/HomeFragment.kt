package desla.aos.eating.ui.home

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import desla.aos.eating.R
import desla.aos.eating.data.model.Post
import desla.aos.eating.data.model.PostsResponse
import desla.aos.eating.data.network.ServerApi
import desla.aos.eating.data.repositories.HomeRepository
import desla.aos.eating.databinding.FragmentHomeBinding
import desla.aos.eating.ui.MainActivity
import desla.aos.eating.ui.MyApplication
import desla.aos.eating.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment :  BaseFragment<FragmentHomeBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_home

    private lateinit var factory: HomeViewModelFactory
    private lateinit var viewModel: HomeViewModel

    private val TAG = "HomeFragment"

    private val posts: MutableList<PostsResponse.Data> = mutableListOf()


    override fun onResume() {
        super.onResume()

        viewModel.setAddress(MyApplication.prefs.getString("address", ""))
        viewModel.setDistance(1000)
    }

    override fun initStartView() {
        (activity as MainActivity).setVisibilityBottomNavigation(true)

        val server = ServerApi()
        val repository = HomeRepository(server)
        factory = HomeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        viewDataBinding.vm = viewModel


        initRc()
        viewModel.getPost()
    }

    override fun initDataBinding() {

        viewModel.postList.observe(this, Observer {
            posts.clear()
            posts.addAll(it)
            viewDataBinding.rcHome.adapter?.notifyDataSetChanged()
        })

        viewDataBinding.btnMap.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_mapActivity)
        }

        viewModel.address.observe(this, Observer {
            viewDataBinding.tvAddressHome.text = it
        })
        viewModel.distance.observe(this, Observer {
            viewDataBinding.tvDistance.text = "${it}M"
        })


    }

    override fun initAfterBinding() {

        viewDataBinding.fabPost.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_postActivity)
        }

        viewDataBinding.btnFilter.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_filterFragment)
        }

        viewDataBinding.btnSearch.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_searchFragment)
        }

    }


    private fun initRc(){
        rc_home.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rc_home.setHasFixedSize(true)
        rc_home.adapter = HomeRCAdapter(posts)
    }



}