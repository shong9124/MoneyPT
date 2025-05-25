package com.capstone.data.remote

import com.capstone.data.model.user.GetUserInfoResponseDTO
import com.capstone.data.model.user.PatchUserPropensityResponseDTO
import com.capstone.data.service.UserInfoService
import com.capstone.data.util.MySharedPreferences
import com.capstone.data.util.MySharedPreferences.Companion.KEY_ACCESS_TOKEN
import retrofit2.Response
import javax.inject.Inject

class UserInfoRemoteDataSourceImpl @Inject constructor(
    private val sharedPreferences: MySharedPreferences,
    private val service: UserInfoService
) : UserInfoRemoteDataSource {
    override suspend fun getUserInfo(): Response<GetUserInfoResponseDTO> {
        val response = service.getUserInfo(sharedPreferences.getString(KEY_ACCESS_TOKEN, ""))

        return response
    }

    override suspend fun patchUserPropensity(userPropensityId: String): Response<PatchUserPropensityResponseDTO> {
        val response = service.patchUserPropensity(
            sharedPreferences.getString(KEY_ACCESS_TOKEN, ""),
            userPropensityId
        )

        return response
    }
}