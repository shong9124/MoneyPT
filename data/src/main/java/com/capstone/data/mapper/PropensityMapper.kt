package com.capstone.data.mapper

import com.capstone.data.model.UserSurveyResultRequestDTO
import com.capstone.domain.model.UserSurveyResult

val UserSurveyResult.toRequestDTO
    get() = UserSurveyResultRequestDTO(
        userSurveyResult = this.userSurveyResult
    )