package com.capstone.data.service

import com.capstone.data.model.UserSurveyResultRequestDTO
import com.capstone.data.model.UserSurveyResultResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PropensityService {

    @POST("/propensity")
    suspend fun sendQuestionResult(
        @Body body: UserSurveyResultRequestDTO
    ) : Response<UserSurveyResultResponseDTO>

}