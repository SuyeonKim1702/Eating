package desla.aos.eating.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import desla.aos.eating.R
import desla.aos.eating.data.model.Post
import desla.aos.eating.databinding.RcHomeBinding
import desla.aos.eating.ui.MainActivity
import desla.aos.eating.ui.like.LikeRCAdapter
import desla.aos.eating.ui.view.ViewActivity
import desla.aos.eating.util.getActivity

class HomeRCAdapter (
        private val postList : List<Post>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object
    {
        private const val VIEW_TYPE_DATA = 0
        private const val VIEW_TYPE_LIKE_BOTTOM = 1
    }

    override fun getItemCount() = postList.size + 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)  = when (viewType) {
        VIEW_TYPE_DATA -> HomeViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.rc_home,
                        parent,
                        false
                )
        )
        VIEW_TYPE_LIKE_BOTTOM ->
        {//inflates progressbar layout
            val view = LayoutInflater.from(parent.context).inflate(R.layout.rc_transparent_bottom,parent,false)
            BottomViewHolder(view)
        }

        else -> throw IllegalArgumentException("Different View type")

    }

    override fun getItemViewType(position: Int): Int
    {
        if(position == itemCount-1) return VIEW_TYPE_LIKE_BOTTOM


        return VIEW_TYPE_DATA
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is HomeViewHolder)
        {
            holder.homeBinding.post = postList[position]

            holder.homeBinding.layout.setOnClickListener {
                val context = it.context

                val detailViewIntent = Intent(context, ViewActivity::class.java)
                //detailPostIntent.putExtra("post", photoList!![position])
                //val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context as MainActivity, holder!!.imageView, "profile")
                //context.getActivity()?.startActivityForResult(detailPostIntent, 33,  options.toBundle())
                context.getActivity()?.startActivity(detailViewIntent)
            }
        }

    }

    inner class HomeViewHolder(
            val homeBinding: RcHomeBinding
    ) : RecyclerView.ViewHolder(homeBinding.root)

    inner class BottomViewHolder(
            itemView: View
    ) : RecyclerView.ViewHolder(itemView)

}