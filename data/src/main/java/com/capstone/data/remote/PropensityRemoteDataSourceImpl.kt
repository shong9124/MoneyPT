package com.capstone.data.remote

import com.capstone.data.model.UserSurveyResultRequestDTO
import com.capstone.data.model.UserSurveyResultResponseDTO
import com.capstone.data.service.PropensityService
import retrofit2.Response
import javax.inject.Inject

class PropensityRemoteDataSourceImpl @Inject constructor(
    private val service: PropensityService
) : PropensityRemoteDataSource {
    override suspend fun sendQuestionResult(body: UserSurveyResultRequestDTO): Response<UserSurveyResultResponseDTO> {
        val response = service.sendQuestionResult(body)

        return response
    }
}