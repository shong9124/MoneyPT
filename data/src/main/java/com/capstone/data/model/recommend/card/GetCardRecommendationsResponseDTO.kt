package com.capstone.data.model.recommend.card


import com.google.gson.annotations.SerializedName

data class GetCardRecommendationsResponseDTO(
    @SerializedName("data")
    val data: GetCardData,
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: String,
    @SerializedName("timeStamp")
    val timeStamp: String
)