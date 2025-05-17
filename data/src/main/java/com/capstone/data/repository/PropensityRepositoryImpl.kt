package com.capstone.data.repository

import com.capstone.data.mapper.toRequestDTO
import com.capstone.data.remote.PropensityRemoteDataSource
import com.capstone.domain.model.UserSurveyResult
import com.capstone.domain.repository.PropensityRepository
import javax.inject.Inject

class PropensityRepositoryImpl @Inject constructor(
    private val dataSource: PropensityRemoteDataSource
) : PropensityRepository {
    override suspend fun sendQuestionResult(userSurveyResult: UserSurveyResult): Result<Boolean> {
        return try {
            val dto = userSurveyResult.toRequestDTO // ✅ 괄호 없이 사용
            val response = dataSource.sendQuestionResult(dto)
            if (response.isSuccessful) {
                Result.success(true)
            } else {
                throw Exception("Request is failure")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}