package com.capstone.data.repository

import com.capstone.data.remote.PropensityRemoteDataSource
import com.capstone.domain.model.UserSurveyResult
import com.capstone.domain.repository.PropensityRepository
import javax.inject.Inject

class PropensityRepositoryImpl @Inject constructor(
    private val dataSource: PropensityRemoteDataSource
) : PropensityRepository {
    override suspend fun sendQuestionResult(userSurveyResult: UserSurveyResult): Result<Boolean> {
        return try {
            // 여기서 typeMissMatch 발생
            val response = dataSource.sendQuestionResult(userSurveyResult)
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