package com.capstone.data.service

import com.capstone.data.model.user.GetUserInfoResponseDTO
import com.capstone.data.model.user.PatchUserPropensityResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path

interface UserInfoService {

    @GET("/users/me")
    suspend fun getUserInfo(
        @Header("Authorization") accessToken: String
    ): Response<GetUserInfoResponseDTO>

    @PATCH("/users/propensity/{userPropensityId}")
    suspend fun patchUserPropensity(
        @Header("Authorization") accessToken: String,
        @Path("userPropensityId") userPropensityId: String
    ): Response<PatchUserPropensityResponseDTO>
}