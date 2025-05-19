package com.capstone.data.remote

import com.capstone.data.model.signUp.SignUpCompleteRequestDTO
import com.capstone.data.model.signUp.SignUpRequestDTO
import com.capstone.data.model.signUp.SignUpResponseDTO
import okhttp3.ResponseBody
import retrofit2.Response

interface AuthRemoteDataSource {
    suspend fun signUp(body: SignUpRequestDTO): Response<SignUpResponseDTO>

    suspend fun signUpComplete(
        body: SignUpCompleteRequestDTO
    ): Response<ResponseBody>

    suspend fun login(
        body: SignUpRequestDTO
    ): Response<SignUpResponseDTO>
}