package com.capstone.data.model.user


import com.google.gson.annotations.SerializedName

data class GetUserInfoResponseDTO(
    @SerializedName("data")
    val data: UserData,
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: String,
    @SerializedName("timeStamp")
    val timeStamp: String
)