package com.capstone.data.model
import com.google.gson.annotations.SerializedName

data class SignUpCompleteRequestDTO(
    @SerializedName("asset")
    val asset: Int,
    @SerializedName("birthDate")
    val birthDate: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("salary")
    val salary: Int
)


