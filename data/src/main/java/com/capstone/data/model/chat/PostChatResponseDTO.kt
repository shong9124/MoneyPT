package com.capstone.data.model.chat


import com.google.gson.annotations.SerializedName

data class PostChatResponseDTO(
    @SerializedName("data")
    val data: SendData,
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: String,
    @SerializedName("timeStamp")
    val timeStamp: String
)