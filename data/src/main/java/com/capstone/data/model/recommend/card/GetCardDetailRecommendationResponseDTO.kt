package com.capstone.data.model.recommend.card


import com.google.gson.annotations.SerializedName

data class GetCardDetailRecommendationResponseDTO(
    @SerializedName("data")
    val data: GetCardDetailData,
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: String,
    @SerializedName("timeStamp")
    val timeStamp: String
)