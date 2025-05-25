package com.capstone.domain.repository

import com.capstone.domain.model.GetUserInfo
import com.capstone.domain.model.PatchPropensity

interface UserInfoRepository {

    suspend fun getUserInfo() : Result<GetUserInfo>

    suspend fun patchUserPropensity(userPropensityId: String): Result<PatchPropensity>
}