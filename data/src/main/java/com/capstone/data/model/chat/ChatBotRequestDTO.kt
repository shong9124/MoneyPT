package com.capstone.data.model.chat


import com.google.gson.annotations.SerializedName

data class ChatBotRequestDTO(
    @SerializedName("requestMessage")
    val requestMessage: String,
    @SerializedName("summary")
    val summary: String
)