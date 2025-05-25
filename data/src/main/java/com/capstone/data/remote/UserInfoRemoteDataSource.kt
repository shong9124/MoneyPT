package com.capstone.data.remote

import com.capstone.data.model.user.GetUserInfoResponseDTO
import com.capstone.data.model.user.PatchUserPropensityResponseDTO
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Path

interface UserInfoRemoteDataSource {
    suspend fun getUserInfo(): Response<GetUserInfoResponseDTO>

    suspend fun patchUserPropensity(
        userPropensityId: String
    ): Response<PatchUserPropensityResponseDTO>
}