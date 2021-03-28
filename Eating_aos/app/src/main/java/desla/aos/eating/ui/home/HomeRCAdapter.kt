package desla.aos.eating.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import desla.aos.eating.R
import desla.aos.eating.data.model.PostsResponse
import desla.aos.eating.databinding.RcHomeBinding
import desla.aos.eating.ui.view.client.ViewActivity
import desla.aos.eating.ui.view.host.ViewHostActivity
import desla.aos.eating.util.MyTimeUtils
import desla.aos.eating.util.getActivity
import java.text.SimpleDateFormat
import java.util.*

class HomeRCAdapter (
        private val postList : List<PostsResponse.Data>
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


    val img = arrayListOf<Int>(R.drawable.cb_0, R.drawable.cb_1, R.drawable.cb_2,R.drawable.cb_3,
            R.drawable.cb_4, R.drawable.cb_5, R.drawable.cb_6, R.drawable.cb_7, R.drawable.cb_8,
            R.drawable.cb_9)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is HomeViewHolder)
        {
            holder.homeBinding.post = postList[position]

            holder.homeBinding.homeLike.isSelected = postList[position].favorite

            holder.homeBinding.thumb.setImageResource(img[postList[position].categoryIdx])
            holder.homeBinding.thumb.clipToOutline = true


            holder.homeBinding.layout.setOnClickListener {
                val context = it.context

                if(postList[position].mine){
                    val detailViewIntent = Intent(context, ViewHostActivity::class.java)
                    detailViewIntent.putExtra("data", postList[position])
                    context.getActivity()?.startActivity(detailViewIntent)
                }else{
                    val detailViewIntent = Intent(context, ViewActivity::class.java)
                    detailViewIntent.putExtra("data", postList[position])
                    context.getActivity()?.startActivity(detailViewIntent)
                }


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