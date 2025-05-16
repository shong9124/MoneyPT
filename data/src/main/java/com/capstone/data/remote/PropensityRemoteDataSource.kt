package com.capstone.data.remote

import com.capstone.data.model.UserSurveyResultRequestDTO
import com.capstone.data.model.UserSurveyResultResponseDTO
import retrofit2.Response

interface PropensityRemoteDataSource {
    suspend fun sendQuestionResult(
        body: UserSurveyResultRequestDTO
    ): Response<UserSurveyResultResponseDTO>
}