package com.capstone.data.model.recommend.card


import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("currentPage")
    val currentPage: Int,
    @SerializedName("totalPage")
    val totalPage: Int
)