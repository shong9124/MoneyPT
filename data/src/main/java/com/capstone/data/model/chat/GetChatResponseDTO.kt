package com.capstone.data.model.chat


import com.google.gson.annotations.SerializedName

data class GetChatResponseDTO(
    @SerializedName("data")
    val data: GetData,
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: String,
    @SerializedName("timeStamp")
    val timeStamp: String
)

data class GetData(
    @SerializedName("conversationLogResponses")
    val conversationLogResponses: List<ConversationLogResponse>
)