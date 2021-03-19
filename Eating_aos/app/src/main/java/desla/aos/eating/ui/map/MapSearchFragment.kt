package desla.aos.eating.ui.map

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import desla.aos.eating.R
import desla.aos.eating.data.model.AddressAPI
import desla.aos.eating.data.model.MapSearch
import desla.aos.eating.databinding.FragmentMapSearchBinding
import desla.aos.eating.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_map_search.*

class MapSearchFragment :  BaseFragment<FragmentMapSearchBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_map_search

    private lateinit var viewModel: MapViewModel

    private val TAG = "LoginFragment"
    private val address: MutableList<MapSearch> = mutableListOf()

    override fun initStartView() {

        viewModel = ViewModelProvider(requireActivity())[MapViewModel::class.java]
        viewDataBinding.vm = viewModel
    }

    override fun initDataBinding() {

        initRc()
        viewModel.locationList.observe(this, Observer {

            address.clear()
            address.addAll(it)
            rc_map.adapter?.notifyDataSetChanged()


        })

    }

    override fun initAfterBinding() {

    }


    private fun initRc(){
        rc_map.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rc_map.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        rc_map.setHasFixedSize(true)
        rc_map.adapter = MapSearchRCAdapter(address).apply {
            setLocationItemClickListener(object : MapSearchRCAdapter.OnItemClickListener{
                override fun onClick(position: Int) {
                    viewModel.setSelectLocation(address[position])
                    (activity as MapActivity).onBackPressed()
                }
            })
        }


    }

}