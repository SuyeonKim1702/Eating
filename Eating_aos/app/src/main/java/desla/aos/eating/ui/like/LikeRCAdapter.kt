package desla.aos.eating.ui.like

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import desla.aos.eating.R
import desla.aos.eating.data.model.Post
import desla.aos.eating.databinding.RcHomeBinding

class LikeRCAdapter (
        private val chatList : List<Post>,
        private val likeList : List<Post>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object
    {
        private const val VIEW_TYPE_TITLE_CHAT = 0
        private const val VIEW_TYPE_CHAT_DATA = 1
        private const val VIEW_TYPE_TITLE_LIKE = 2
        private const val VIEW_TYPE_LIKE_DATA = 3
    }


    override fun getItemCount() = if (chatList.isEmpty()) likeList.size + 1 else chatList.size + likeList.size + 2

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

        else -> throw IllegalArgumentException("Different View type")
    }

    override fun getItemViewType(position: Int): Int
    {

        if(chatList.isEmpty()){
            if(position == 0) return VIEW_TYPE_TITLE_LIKE
            else if(position <= likeList.size) return VIEW_TYPE_LIKE_DATA
        }else{
            //비어있지 않다면
            if(position == 0) return VIEW_TYPE_TITLE_CHAT
            else if(position < chatList.size + 1) return VIEW_TYPE_CHAT_DATA
            else if(position == chatList.size + 1) return VIEW_TYPE_TITLE_LIKE
        }


        return VIEW_TYPE_LIKE_DATA
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ChatViewHolder)
        {
            holder.homeBinding.post = chatList[position-1]
        }

        if (holder is LikeViewHolder)
        {
            if(chatList.isEmpty()){
                holder.homeBinding.post = likeList[position-1]
            }else{
                holder.homeBinding.post = likeList[position-chatList.size-2]
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


}