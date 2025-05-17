package com.capstone.domain.usecase.chat

import com.capstone.domain.model.chat.PostMessage
import com.capstone.domain.model.chat.ResponseMessage
import com.capstone.domain.repository.ChatBotRepository
import javax.inject.Inject

class SendChatUseCase @Inject constructor(
    private val chatBotRepository: ChatBotRepository
) {
    suspend operator fun invoke(embedding: Boolean, postMessage: PostMessage): Result<ResponseMessage> {
        return chatBotRepository.sendChat(embedding, postMessage)
    }
}