package com.capstone.data.model.recommend.card


import com.google.gson.annotations.SerializedName

data class GetCardData(
    @SerializedName("content")
    val content: List<Content>,
    @SerializedName("pagination")
    val pagination: Pagination
)