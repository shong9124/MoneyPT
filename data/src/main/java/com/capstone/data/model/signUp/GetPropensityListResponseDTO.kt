package com.capstone.data.model.signUp


import com.google.gson.annotations.SerializedName

data class GetPropensityListResponseDTO(
    @SerializedName("data")
    val data: GetPropensityData,
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: String,
    @SerializedName("timeStamp")
    val timeStamp: String
)

data class GetPropensityData(
    @SerializedName("content")
    val content: List<PropensityInfoContent>,
    @SerializedName("pagination")
    val pagination: PropensityPagination
)

data class PropensityInfoContent(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("propensity")
    val propensity: String
)

data class PropensityPagination(
    @SerializedName("currentPage")
    val currentPage: Int,
    @SerializedName("totalPage")
    val totalPage: Int
)