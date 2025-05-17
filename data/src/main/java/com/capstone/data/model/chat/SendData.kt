package com.capstone.data.model.chat


import com.google.gson.annotations.SerializedName

data class SendData(
    @SerializedName("conversationId")
    val conversationId: String,
    @SerializedName("responseMessage")
    val responseMessage: String,
    @SerializedName("summary")
    val summary: String
)