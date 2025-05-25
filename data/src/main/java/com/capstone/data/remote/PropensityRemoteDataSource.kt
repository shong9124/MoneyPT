package com.capstone.data.remote

import com.capstone.data.model.signUp.GetPropensityListResponseDTO
import com.capstone.data.model.signUp.UserSurveyResultRequestDTO
import com.capstone.data.model.signUp.UserSurveyResultResponseDTO
import retrofit2.Response

interface PropensityRemoteDataSource {
    suspend fun sendQuestionResult(
        body: UserSurveyResultRequestDTO
    ): Response<UserSurveyResultResponseDTO>

    suspend fun getPropensityList(
        page: Int, size: Int
    ) : Response<GetPropensityListResponseDTO>
}