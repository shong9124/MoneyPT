package com.capstone.data.model.signUp

import com.google.gson.annotations.SerializedName

data class UserSurveyResultRequestDTO(
    @SerializedName("userSurveyResult")
    val userSurveyResult: String
)
