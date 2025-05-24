package com.capstone.data.model.recommend.bank

import com.google.gson.annotations.SerializedName

data class RecommendationListDTO(
    @SerializedName("recommendations")
    val recommendations: List<Recommendations>
)
