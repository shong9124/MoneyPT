package com.capstone.data.model.recommend.card


import com.google.gson.annotations.SerializedName

data class PostPaymentInfoResponseDTO(
    @SerializedName("data")
    val data: PostCardData,
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: String,
    @SerializedName("timeStamp")
    val timeStamp: String
)