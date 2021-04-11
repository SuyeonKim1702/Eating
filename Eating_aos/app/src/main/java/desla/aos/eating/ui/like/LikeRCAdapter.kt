package desla.aos.eating.ui.like

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import desla.aos.eating.R
import desla.aos.eating.data.model.PostsResponse
import desla.aos.eating.databinding.RcHomeBinding
import desla.aos.eating.ui.view.client.ViewActivity
import desla.aos.eating.ui.view.host.ViewHostActivity
import desla.aos.eating.util.getActivity

class LikeRCAdapter (
        private val chatList : List<PostsResponse.Data>,
        private val likeList : List<PostsResponse.Data>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object
    {
        private const val VIEW_TYPE_TITLE_CHAT = 0
        private const val VIEW_TYPE_CHAT_DATA = 1
        private const val VIEW_TYPE_TITLE_LIKE = 2
        private const val VIEW_TYPE_LIKE_DATA = 3
        private const val VIEW_TYPE_LIKE_BOTTOM = 4
    }


    override fun getItemCount() = if (chatList.isEmpty()) likeList.size + 3 else chatList.size + likeList.size + 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType)
    {
        VIEW_TYPE_TITLE_CHAT ->
        {//inflates progressbar layout
            val view = LayoutInflater.from(parent.context).inflate(R.layout.like_chat_title,parent,false)
            ChatTitleViewHolder(view)
        }

        VIEW_TYPE_CHAT_DATA ->
        {//inflates row layout
            ChatViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.rc_home,
                            parent,
                            false
                    )
            )
        }

        VIEW_TYPE_TITLE_LIKE ->
        {//inflates progressbar layout
            val view = LayoutInflater.from(parent.context).inflate(R.layout.like_title,parent,false)
            LikeTitleViewHolder(view)
        }

        VIEW_TYPE_LIKE_DATA ->
        {//inflates row layout
            LikeViewHolder(
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.rc_home,
                            parent,
                            false
                    )
            )
        }
        VIEW_TYPE_LIKE_BOTTOM ->
        {//inflates progressbar layout
            val view = LayoutInflater.from(parent.context).inflate(R.layout.rc_transparent_bottom,parent,false)
            BottomViewHolder(view)
        }

        else -> throw IllegalArgumentException("Different View type")
    }

    override fun getItemViewType(position: Int): Int
    {
        if(position == 0) return VIEW_TYPE_TITLE_CHAT
        if(position == itemCount-1) return VIEW_TYPE_LIKE_BOTTOM

        if(chatList.isEmpty()){
            if(position == 1) return VIEW_TYPE_TITLE_LIKE
            else if(position <= likeList.size) return VIEW_TYPE_LIKE_DATA
        }else{
            //비어있지 않다면
            if(position < chatList.size + 1) return VIEW_TYPE_CHAT_DATA
            else if(position == chatList.size + 1) return VIEW_TYPE_TITLE_LIKE
        }


        return VIEW_TYPE_LIKE_DATA
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(holder is ChatTitleViewHolder){
            if(chatList.isEmpty()){
                holder.itemView.findViewById<TextView>(R.id.tv_none).visibility = View.VISIBLE
            }else{
                holder.itemView.findViewById<TextView>(R.id.tv_none).visibility = View.GONE
            }
        }

        if (holder is ChatViewHolder)
        {
            holder.homeBinding.thumb.clipToOutline = true
            holder.homeBinding.post = chatList[position-1]
            holder.homeBinding.homeLike.isSelected = chatList[position-1].favorite

            holder.homeBinding.layout.setOnClickListener {
                val context = it.context

                if(chatList[position-1].mine){
                    val detailViewIntent = Intent(context, ViewHostActivity::class.java)
                    detailViewIntent.putExtra("data", chatList[position-1])
                    context.getActivity()?.startActivity(detailViewIntent)
                }else{
                    val detailViewIntent = Intent(context, ViewActivity::class.java)
                    detailViewIntent.putExtra("data", chatList[position-1])
                    context.getActivity()?.startActivity(detailViewIntent)
                }
            }
        }

        if(holder is LikeTitleViewHolder){
            if(likeList.isEmpty()){
                holder.itemView.findViewById<TextView>(R.id.tv_none).visibility = View.VISIBLE
            }else{
                holder.itemView.findViewById<TextView>(R.id.tv_none).visibility = View.GONE
            }
        }

        if (holder is LikeViewHolder)
        {
            var num = position - 2

            holder.homeBinding.thumb.clipToOutline = true
            if(chatList.isEmpty()){
                holder.homeBinding.post = likeList[position-2]
                holder.homeBinding.homeLike.isSelected = likeList[position-2].favorite
            }else{
                holder.homeBinding.post = likeList[position-chatList.size-2]
                holder.homeBinding.homeLike.isSelected = likeList[position-chatList.size-2].favorite
                num = position - chatList.size - 2
            }

            holder.homeBinding.layout.setOnClickListener {
                val context = it.context

                if(likeList[num].mine){
                    val detailViewIntent = Intent(context, ViewHostActivity::class.java)
                    detailViewIntent.putExtra("data", likeList[num])
                    context.getActivity()?.startActivity(detailViewIntent)
                }else{
                    val detailViewIntent = Intent(context, ViewActivity::class.java)
                    detailViewIntent.putExtra("data", likeList[num])
                    context.getActivity()?.startActivity(detailViewIntent)
                }
            }

        }

    }

    inner class ChatViewHolder(
            val homeBinding: RcHomeBinding
    ) : RecyclerView.ViewHolder(homeBinding.root)

    inner class LikeViewHolder(
            val homeBinding: RcHomeBinding
    ) : RecyclerView.ViewHolder(homeBinding.root)

    inner class ChatTitleViewHolder(
            itemView: View
    ) : RecyclerView.ViewHolder(itemView)

    inner class LikeTitleViewHolder(
            itemView: View
    ) : RecyclerView.ViewHolder(itemView)

    inner class BottomViewHolder(
            itemView: View
    ) : RecyclerView.ViewHolder(itemView)
}