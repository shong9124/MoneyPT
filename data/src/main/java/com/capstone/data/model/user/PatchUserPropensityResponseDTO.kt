package com.capstone.data.model.user


import com.google.gson.annotations.SerializedName

data class PatchUserPropensityResponseDTO(
    @SerializedName("data")
    val data: UserPropensityData? = null,
    @SerializedName("message")
    val message: String,
    @SerializedName("statusCode")
    val statusCode: String,
    @SerializedName("timeStamp")
    val timeStamp: String
)