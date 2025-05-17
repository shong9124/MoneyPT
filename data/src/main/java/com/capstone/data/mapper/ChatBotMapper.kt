package com.capstone.data.mapper

import com.capstone.data.model.chat.ChatBotRequestDTO
import com.capstone.domain.model.chat.PostMessage

val PostMessage.toDTO
    get() = ChatBotRequestDTO(this.requestMessage, this.summary)