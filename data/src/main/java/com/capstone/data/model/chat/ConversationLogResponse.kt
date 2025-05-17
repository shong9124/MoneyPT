package com.capstone.data.model.chat


import com.google.gson.annotations.SerializedName

data class ConversationLogResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("requestMessage")
    val requestMessage: String,
    @SerializedName("responseMessage")
    val responseMessage: String,
    @SerializedName("summary")
    val summary: String
)