package com.capstone.data.service

import com.capstone.data.model.chat.ChatBotRequestDTO
import com.capstone.data.model.chat.GetChatResponseDTO
import com.capstone.data.model.chat.PostChatResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ChatBotService {

    @GET("/conversations")
    suspend fun getChatList(
        @Header("Authorization") accessToken: String,
        @Query("cursor") cursor: String,     // conversationId 전달
        @Query("size") size: Int
    ): Response<GetChatResponseDTO>

    @POST("/conversations")
    suspend fun sendChat(
        @Header("Authorization") accessToken: String,
        @Query("embedding") embedding: Boolean,
        @Body body: ChatBotRequestDTO
    ): Response<PostChatResponseDTO>

}