package com.capstone.domain.repository

import com.capstone.domain.model.UserSurveyResult

interface PropensityRepository {
    suspend fun sendQuestionResult(userSurveyResult: UserSurveyResult) : Result<Boolean>
}