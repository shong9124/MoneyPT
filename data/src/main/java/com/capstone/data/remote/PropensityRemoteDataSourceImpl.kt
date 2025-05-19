package com.capstone.data.remote

import com.capstone.data.model.signUp.UserSurveyResultRequestDTO
import com.capstone.data.model.signUp.UserSurveyResultResponseDTO
import com.capstone.data.service.PropensityService
import com.capstone.data.util.MySharedPreferences
import com.capstone.data.util.MySharedPreferences.Companion.KEY_ACCESS_TOKEN
import retrofit2.Response
import javax.inject.Inject

class PropensityRemoteDataSourceImpl @Inject constructor(
    private val sharedPreferences: MySharedPreferences,
    private val service: PropensityService
) : PropensityRemoteDataSource {
    override suspend fun sendQuestionResult(body: UserSurveyResultRequestDTO): Response<UserSurveyResultResponseDTO> {
        val response = service.sendQuestionResult(sharedPreferences.getString(KEY_ACCESS_TOKEN, ""), body)

        return response
    }
}