package com.capstone.presentation.view.chatBot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.presentation.R
import com.capstone.util.LoggerUtil

class ChatAdapter(private val messages: List<ChatMessage>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_USER = 1
        private const val VIEW_TYPE_BOT = 2
        private const val VIEW_TYPE_LOADING = 3
    }

    override fun getItemViewType(position: Int): Int {
        val message = messages[position]
        return when {
            message.isLoading -> VIEW_TYPE_LOADING
            message.isUser -> VIEW_TYPE_USER
            else -> VIEW_TYPE_BOT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_USER -> {
                val view = inflater.inflate(R.layout.item_chat_user, parent, false)
                UserViewHolder(view)
            }
            VIEW_TYPE_BOT -> {
                val view = inflater.inflate(R.layout.item_chat_bot, parent, false)
                BotViewHolder(view)
            }
            VIEW_TYPE_LOADING -> {
                val view = inflater.inflate(R.layout.item_chat_loading, parent, false)
                LoadingViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chat = messages[position]
        LoggerUtil.d("🪵 바인딩 [$position]: $chat")

        when (holder) {
            is UserViewHolder -> holder.bind(chat.message)
            is BotViewHolder -> holder.bind(chat)
            is LoadingViewHolder -> { /* nothing to bind */ }
        }
    }

    override fun getItemCount(): Int = messages.size

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvMessage: TextView = itemView.findViewById(R.id.tv_message)
        fun bind(message: String) {
            tvMessage.text = message
        }
    }

    class BotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMessage: TextView = itemView.findViewById(R.id.tv_message)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progress_loading)

        fun bind(chatMessage: ChatMessage) {
            if (chatMessage.isLoading) {
                progressBar.visibility = View.VISIBLE
                tvMessage.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                tvMessage.visibility = View.VISIBLE
                tvMessage.text = chatMessage.message
            }
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}