package com.capstone.domain.model.chat

data class ResponseMessage(
    val conversationId: String,
    val responseMessage: String,
    val summary: String
)
