package com.capstone.data.model.recommend.bank


import com.google.gson.annotations.SerializedName

data class GetData(
    @SerializedName("content")
    val content: List<Content>,
    @SerializedName("pagination")
    val pagination: Pagination
)