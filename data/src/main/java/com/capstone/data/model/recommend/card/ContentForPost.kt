package com.capstone.data.model.recommend.card


import com.google.gson.annotations.SerializedName

data class ContentForPost(
    @SerializedName("recommendations")
    val recommendations: List<Recommendation>
)