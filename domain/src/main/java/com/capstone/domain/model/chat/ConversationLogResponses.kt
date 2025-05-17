package com.capstone.domain.model.chat

data class ConversationLogResponses (
    val id: String,
    val requestMessage: String,
    val responseMessage: String,
    val summary: String
)