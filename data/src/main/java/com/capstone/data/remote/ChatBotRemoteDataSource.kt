package com.capstone.data.remote

import com.capstone.data.model.chat.ChatBotRequestDTO
import com.capstone.data.model.chat.GetChatResponseDTO
import com.capstone.data.model.chat.PostChatResponseDTO
import retrofit2.Response

interface ChatBotRemoteDataSource {

    suspend fun getChatList(
        size: Int
    ): Response<GetChatResponseDTO>

    suspend fun sendChat(
        embedding: Boolean,
        body: ChatBotRequestDTO
    ): Response<PostChatResponseDTO>
}