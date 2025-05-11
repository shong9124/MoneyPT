package com.capstone.data.remote

import com.capstone.data.model.SignUpCompleteRequestDTO
import com.capstone.data.model.SignUpRequestDTO
import com.capstone.data.model.SignUpResponseDTO
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthRemoteDataSource {
    suspend fun signUp(body: SignUpRequestDTO): Response<SignUpResponseDTO>

    suspend fun signUpComplete(
        body: SignUpCompleteRequestDTO
    ): Response<ResponseBody>

    suspend fun login(
        body: SignUpRequestDTO
    ): Response<SignUpResponseDTO>
}