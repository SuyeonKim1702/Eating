package desla.aos.eating.ui.home.search

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import desla.aos.eating.R
import desla.aos.eating.data.model.Post
import desla.aos.eating.data.repositories.FilterRepository
import desla.aos.eating.data.repositories.SearchRepository
import desla.aos.eating.databinding.FragmentFilterBinding
import desla.aos.eating.ui.MainActivity
import desla.aos.eating.ui.base.BaseFragment
import desla.aos.eating.ui.map.MapActivity
import desla.aos.eating.ui.map.MapSearchRCAdapter
import desla.aos.eating.util.SpaceDecoration
import kotlinx.android.synthetic.main.activity_view.*
import kotlinx.android.synthetic.main.fragment_filter.*
import kotlinx.android.synthetic.main.fragment_map_search.*
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment :  BaseFragment<FragmentFilterBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_search

    private lateinit var factory: SearchViewModelFactory
    private lateinit var viewModel: SearchViewModel

    private val TAG = "SearchFragment"

    private val recentList: MutableList<String> = mutableListOf()
    private val rankList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recentList.add("한식")
        recentList.add("짜장")
        recentList.add("떡볶이")
        recentList.add("한식")
        recentList.add("짜장")
        recentList.add("떡볶이")
        recentList.add("한식")
        recentList.add("짜장")
        recentList.add("떡볶이")

        rankList.addAll(recentList)



    }

    override fun initStartView() {
        (activity as MainActivity).setVisibilityBottomNavigation(false)

        val repository = SearchRepository()
        factory = SearchViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)
        //viewDataBinding.viewModel = viewModel
    }

    override fun initDataBinding() {
        initRcRecent()
        initRcRank()
    }

    override fun initAfterBinding() {

    }


    private fun initRcRecent(){
        rc_recent_search.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rc_recent_search.setHasFixedSize(true)
        val size = resources.getDimensionPixelSize(R.dimen.margin_horizontal_recent_search)
        val deco = SpaceDecoration(size)
        rc_recent_search.addItemDecoration(deco)
        rc_recent_search.adapter = SearchRecentRCAdapter(recentList)


    }

    private fun initRcRank(){
        rc_rank.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rc_rank.setHasFixedSize(true)
        rc_rank.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        rc_rank.adapter = SearchRankRCAdapter(recentList)


    }



}