package com.capstone.data.model.recommend.card


import com.google.gson.annotations.SerializedName

data class ContentForGetDetail(
    @SerializedName("recommendations")
    val recommendations: List<RecommendationX>
)