package desla.aos.eating.ui.home.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import desla.aos.eating.R
import desla.aos.eating.databinding.RcRankSearchBinding

class SearchRankRCAdapter (
        private val list : List<String>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            HomeViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.rc_rank_search,
                            parent,
                            false
                    )
            )


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is HomeViewHolder)
        {
            holder.binding.name = list[position]
            holder.binding.num = position

        }

    }

    inner class HomeViewHolder(
            val binding: RcRankSearchBinding
    ) : RecyclerView.ViewHolder(binding.root)


}