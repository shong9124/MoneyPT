package com.capstone.data.model.recommend.bank


import com.google.gson.annotations.SerializedName

data class GetDetailData(
    @SerializedName("content")
    val content: RecommendationListDTO,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("strategy")
    val strategy: String
)