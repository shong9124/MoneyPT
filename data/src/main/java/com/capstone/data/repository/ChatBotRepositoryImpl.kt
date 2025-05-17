package com.capstone.data.repository

import com.capstone.data.mapper.toDTO
import com.capstone.data.remote.ChatBotRemoteDataSource
import com.capstone.domain.model.chat.ConversationLogResponses
import com.capstone.domain.model.chat.PostMessage
import com.capstone.domain.model.chat.ResponseMessage
import com.capstone.domain.repository.ChatBotRepository
import javax.inject.Inject

class ChatBotRepositoryImpl @Inject constructor(
    private val dataSource: ChatBotRemoteDataSource
) : ChatBotRepository {
    override suspend fun getChatList(size: Int): Result<List<ConversationLogResponses>> {
        return try {
            val response = dataSource.getChatList(size)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.data.conversationLogResponses.map {
                        ConversationLogResponses(
                            it.id,
                            it.requestMessage,
                            it.responseMessage,
                            it.summary
                        )
                    })
                } else {
                    throw Exception("Body is null")
                }
            } else {
                throw Exception("Request is failure")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendChat(
        embedding: Boolean,
        postMessage: PostMessage
    ): Result<ResponseMessage> {
        return try {
            val response = dataSource.sendChat(embedding = true, postMessage.toDTO)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.data.let {
                        ResponseMessage(
                            it.conversationId,
                            it.responseMessage,
                            it.summary
                        )
                    })
                } else {
                    throw Exception("Body is null")
                }
            } else {
                throw Exception("Request is failure")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}