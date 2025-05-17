package com.capstone.data.remote

import com.capstone.data.model.chat.ChatBotRequestDTO
import com.capstone.data.model.chat.GetChatResponseDTO
import com.capstone.data.model.chat.PostChatResponseDTO
import com.capstone.data.service.ChatBotService
import com.capstone.data.util.MySharedPreferences
import com.capstone.data.util.MySharedPreferences.Companion.CURSOR
import com.capstone.data.util.MySharedPreferences.Companion.KEY_ACCESS_TOKEN
import com.capstone.data.util.MySharedPreferences.Companion.SUMMARY
import retrofit2.Response
import javax.inject.Inject

class ChatBotRemoteDataSourceImpl @Inject constructor(
    private val sharedPreferences: MySharedPreferences,
    private val service: ChatBotService
) : ChatBotRemoteDataSource {
    override suspend fun getChatList(size: Int): Response<GetChatResponseDTO> {
        val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, "")
        val cursor = sharedPreferences.getString(CURSOR, "")
        val response = service.getChatList(accessToken, cursor, size)

        return response
    }

    override suspend fun sendChat(
        embedding: Boolean,
        body: ChatBotRequestDTO
    ): Response<PostChatResponseDTO> {
        val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, "")
        val response = service.sendChat(accessToken, embedding, body)

        // 요청에 성공하면 id랑 요약본 저장하기
        if (response.isSuccessful) {
            sharedPreferences.setString(CURSOR, response.body()?.data?.conversationId ?: "")
            sharedPreferences.setString(SUMMARY, response.body()?.data?.summary ?: "")
        } else sharedPreferences.delete(CURSOR)

        return response
    }
}