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
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].isUser) VIEW_TYPE_USER else VIEW_TYPE_BOT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_USER) {
            val view = inflater.inflate(R.layout.item_chat_user, parent, false)
            UserViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.item_chat_bot, parent, false)
            BotViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val chat = messages[position]
        LoggerUtil.d("🪵 바인딩 [$position]: $chat")

        if (!chat.isUser) {
            val botHolder = holder as BotViewHolder
            if (chat.isLoading) {
                botHolder.progressBar.visibility = View.VISIBLE
                botHolder.tvMessage.visibility = View.GONE
            } else {
                botHolder.progressBar.visibility = View.GONE
                botHolder.tvMessage.visibility = View.VISIBLE
                botHolder.tvMessage.text = chat.message
            }
        } else {
            val userHolder = holder as UserViewHolder
            userHolder.bind(chat.message)
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
}