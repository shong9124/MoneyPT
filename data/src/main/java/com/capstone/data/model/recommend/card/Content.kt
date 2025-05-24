package com.capstone.data.model.recommend.card


import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("strategy")
    val strategy: String
)