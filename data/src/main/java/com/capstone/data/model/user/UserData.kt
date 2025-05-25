package com.capstone.data.model.user


import com.google.gson.annotations.SerializedName

data class UserData(
    @SerializedName("asset")
    val asset: Int,
    @SerializedName("birthDate")
    val birthDate: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("nickname")
    val nickname: String,
    @SerializedName("propensity")
    val propensity: String,
    @SerializedName("registerCompleted")
    val registerCompleted: Boolean,
    @SerializedName("salary")
    val salary: Int
)