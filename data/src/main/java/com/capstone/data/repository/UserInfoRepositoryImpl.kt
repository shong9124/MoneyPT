package com.capstone.data.repository

import com.capstone.data.mapper.toDomain
import com.capstone.data.remote.UserInfoRemoteDataSource
import com.capstone.domain.model.GetUserInfo
import com.capstone.domain.model.PatchPropensity
import com.capstone.domain.repository.UserInfoRepository
import com.capstone.util.LoggerUtil
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val dataSource: UserInfoRemoteDataSource
): UserInfoRepository {
    override suspend fun getUserInfo(): Result<GetUserInfo> {
        return try {
            val response = dataSource.getUserInfo()
            val body = response.body() ?: throw Exception("Empty response body")
            if (response.isSuccessful) {
                Result.success(body.toDomain())
            } else {
                throw Exception("Request is failure")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun patchUserPropensity(userPropensityId: String): Result<PatchPropensity> {
        return try {
            val response = dataSource.patchUserPropensity(userPropensityId)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.toDomain())
                } else {
                    // body가 null인 경우, toDomain 호출하지 않고 기본값 반환
                    Result.success(PatchPropensity.default())
                }
            } else {
                throw Exception("Request failed with status: ${response.code()}")
            }
        } catch (e: Exception) {
            LoggerUtil.e("patchUserPropensity 실패: ${e.message}")
            Result.failure(e)
        }
    }
}