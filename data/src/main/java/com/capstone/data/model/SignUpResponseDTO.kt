package com.capstone.data.model
import com.google.gson.annotations.SerializedName

data class SignUpResponseDTO(
    @SerializedName("data")
    val data: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: String,
    @SerializedName("timeStamp")
    val timeStamp: String
)

data class Data(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("registerCompleted")
    val registerCompleted: Boolean
)


