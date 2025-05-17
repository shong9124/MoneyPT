package com.capstone.domain.usecase.chat

import com.capstone.domain.model.chat.ConversationLogResponses
import com.capstone.domain.repository.ChatBotRepository
import javax.inject.Inject

class GetChatListUseCase @Inject constructor(
    private val chatBotRepository: ChatBotRepository
) {
    suspend operator fun invoke(size: Int): Result<List<ConversationLogResponses>> {
        return chatBotRepository.getChatList(size)
    }
}