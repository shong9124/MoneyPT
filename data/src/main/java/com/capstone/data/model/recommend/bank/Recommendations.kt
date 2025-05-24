package com.capstone.data.model.recommend.bank

import com.google.gson.annotations.SerializedName

data class Recommendations(
    @SerializedName("id")
    val id: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("reason")
    val reason: String,
    @SerializedName("detailUrl")
    val detailUrl: String,
)
