package desla.aos.eating.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import desla.aos.eating.R
import desla.aos.eating.data.model.Post
import desla.aos.eating.databinding.RcHomeBinding

class HomeRCAdapter (
    private val postList : List<Post>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object
    {
        private const val VIEW_TYPE_DATA = 0
    }


    override fun getItemCount() = postList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType)
    {
        VIEW_TYPE_DATA ->
        {//inflates row layout
            HomeViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.rc_home,
                    parent,
                    false
                )
            )
        }
         else -> throw IllegalArgumentException("Different View type")
    }

    override fun getItemViewType(position: Int): Int
    {
        return VIEW_TYPE_DATA
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is HomeViewHolder)
        {
            holder.homeBinding.post = postList[position]
        }

    }

    inner class HomeViewHolder(
        val homeBinding: RcHomeBinding
    ) : RecyclerView.ViewHolder(homeBinding.root)

}