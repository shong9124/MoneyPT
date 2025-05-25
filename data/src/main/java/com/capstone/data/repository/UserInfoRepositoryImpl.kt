package com.capstone.data.repository

import com.capstone.data.mapper.toDomain
import com.capstone.data.remote.UserInfoRemoteDataSource
import com.capstone.domain.model.GetUserInfo
import com.capstone.domain.model.PatchPropensity
import com.capstone.domain.repository.UserInfoRepository
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
}