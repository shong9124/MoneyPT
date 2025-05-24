package com.capstone.data.model.recommend.card


import com.google.gson.annotations.SerializedName

data class Recommendation(
    @SerializedName("description")
    val description: String,
    @SerializedName("detailUrl")
    val detailUrl: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("reason")
    val reason: String
)