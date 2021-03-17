package desla.aos.eating.ui.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import desla.aos.eating.R
import desla.aos.eating.data.model.AddressAPI
import desla.aos.eating.databinding.RcMapBinding
import desla.aos.eating.ui.home.HomeRCAdapter

class MapSearchRCAdapter (
    private val addressList : List<AddressAPI.Document>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    override fun getItemCount() = addressList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HomeViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.rc_map,
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is HomeViewHolder)
        {
            holder.mapBinding.address = addressList[position]
        }

    }

    inner class HomeViewHolder(
        val mapBinding: RcMapBinding
    ) : RecyclerView.ViewHolder(mapBinding.root)


}