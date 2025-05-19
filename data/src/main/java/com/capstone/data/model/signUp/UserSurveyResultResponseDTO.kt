package com.capstone.data.model.signUp


import com.google.gson.annotations.SerializedName

data class UserSurveyResultResponseDTO(
    @SerializedName("data")
    val data: ResultData,
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: String,
    @SerializedName("timeStamp")
    val timeStamp: String
)

data class ResultData(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("propensityAnalysis")
    val propensityAnalysis: PropensityAnalysis
)

data class PropensityAnalysis(
    @SerializedName("description")
    val description: String,
    @SerializedName("precaution")
    val precaution: String,
    @SerializedName("prosAndCons")
    val prosAndCons: String,
    @SerializedName("type")
    val type: String
)