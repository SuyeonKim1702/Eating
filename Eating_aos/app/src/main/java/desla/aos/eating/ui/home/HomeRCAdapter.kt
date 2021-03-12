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
        private const val VIEW_TYPE_BOTTOM = 1
    }


    override fun getItemCount() = postList.size + 1

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
        VIEW_TYPE_BOTTOM ->
        {//inflates progressbar layout
            val view = LayoutInflater.from(parent.context).inflate(R.layout.bottom,parent,false)
            BottomViewHolder(view)
        }
        else -> throw IllegalArgumentException("Different View type")
    }

    override fun getItemViewType(position: Int): Int
    {
        if(position < postList.size) {
            return VIEW_TYPE_DATA
        }

        return VIEW_TYPE_BOTTOM
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

    inner class BottomViewHolder(
            itemView: View
    ) : RecyclerView.ViewHolder(itemView)

}