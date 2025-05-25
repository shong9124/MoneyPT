package com.capstone.data.service

import com.capstone.data.model.signUp.GetPropensityListResponseDTO
import com.capstone.data.model.signUp.UserSurveyResultRequestDTO
import com.capstone.data.model.signUp.UserSurveyResultResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface PropensityService {

    @POST("/propensity")
    suspend fun sendQuestionResult(
        @Header("Authorization") accessToken: String,
        @Body body: UserSurveyResultRequestDTO
    ) : Response<UserSurveyResultResponseDTO>

    @GET("/propensity/analysis")
    suspend fun getPropensityList(
        @Header("Authorization") accessToken: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<GetPropensityListResponseDTO>

}