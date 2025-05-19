package com.capstone.data.model.signUp

import com.google.gson.annotations.SerializedName

data class SignUpRequestDTO (
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)