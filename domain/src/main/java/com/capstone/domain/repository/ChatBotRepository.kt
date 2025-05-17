package com.capstone.domain.repository

import com.capstone.domain.model.chat.ConversationLogResponses
import com.capstone.domain.model.chat.PostMessage
import com.capstone.domain.model.chat.ResponseMessage

interface ChatBotRepository {

    suspend fun getChatList(
        size: Int
    ): Result<List<ConversationLogResponses>>

    suspend fun sendChat(
        embedding: Boolean,
        postMessage: PostMessage
    ): Result<ResponseMessage>
}