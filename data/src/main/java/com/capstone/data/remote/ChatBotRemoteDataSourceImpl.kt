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
    // ChatBotRemoteDataSourceImpl.kt
    override suspend fun getChatList(size: Int): Response<GetChatResponseDTO> {
        val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, "")
        val cursor = sharedPreferences.getString(CURSOR, "")
        val response = service.getChatList(accessToken, cursor, size)

        // ✅ 서버에서 받아온 마지막 메시지 ID를 다음 cursor로 저장
        if (response.isSuccessful) {
            val list = response.body()?.data?.conversationLogResponses
            if (!list.isNullOrEmpty()) {
                val lastId = list.last().id
                sharedPreferences.setString(CURSOR, lastId)
            }
        }

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
        }

        return response
    }
}