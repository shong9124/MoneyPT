package com.capstone.data.service

import com.capstone.data.model.signUp.SignUpCompleteRequestDTO
import com.capstone.data.model.signUp.SignUpRequestDTO
import com.capstone.data.model.signUp.SignUpResponseDTO
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {

    @POST("/auth/sign-up")
    suspend fun signUp(
        @Body body: SignUpRequestDTO
    ): Response<SignUpResponseDTO>

    @POST("/auth/sign-up/complete")
    suspend fun signUpComplete(
        @Header("Authorization") accessToken: String,
        @Body body: SignUpCompleteRequestDTO
    ): Response<ResponseBody>

    @POST("/auth/login")
    suspend fun login(
        @Body body: SignUpRequestDTO
    ): Response<SignUpResponseDTO>

}