package com.capstone.data.remote

import com.capstone.data.model.signUp.SignUpCompleteRequestDTO
import com.capstone.data.model.signUp.SignUpRequestDTO
import com.capstone.data.model.signUp.SignUpResponseDTO
import com.capstone.data.service.AuthService
import com.capstone.data.util.MySharedPreferences
import com.capstone.data.util.MySharedPreferences.Companion.KEY_ACCESS_TOKEN
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val sharedPreferences: MySharedPreferences,
    private val service: AuthService
) : AuthRemoteDataSource {
    override suspend fun signUp(body: SignUpRequestDTO): Response<SignUpResponseDTO> {
        val response = service.signUp(body)
        if (response.isSuccessful) {
            sharedPreferences.setString(KEY_ACCESS_TOKEN, response.body()?.data?.accessToken ?: "")
        } else sharedPreferences.delete(KEY_ACCESS_TOKEN)

        return response
    }

    override suspend fun signUpComplete(
        body: SignUpCompleteRequestDTO
    ): Response<ResponseBody> {
        return service.signUpComplete(sharedPreferences.getString(KEY_ACCESS_TOKEN, ""), body)
    }

    override suspend fun login(body: SignUpRequestDTO): Response<SignUpResponseDTO> {
        val response = service.login(body)
        if (response.isSuccessful) {
            sharedPreferences.setString(KEY_ACCESS_TOKEN, response.body()?.data?.accessToken ?: "")
        } else sharedPreferences.delete(KEY_ACCESS_TOKEN)

        return response
    }
}