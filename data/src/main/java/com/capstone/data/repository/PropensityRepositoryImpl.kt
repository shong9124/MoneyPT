package com.capstone.data.repository

import com.capstone.data.mapper.toDomain
import com.capstone.data.mapper.toDomainModel
import com.capstone.data.mapper.toRequestDTO
import com.capstone.data.remote.PropensityRemoteDataSource
import com.capstone.domain.model.DomainPostPropensityData
import com.capstone.domain.model.GetPropensityListData
import com.capstone.domain.model.UserSurveyResult
import com.capstone.domain.repository.PropensityRepository
import javax.inject.Inject

class PropensityRepositoryImpl @Inject constructor(
    private val dataSource: PropensityRemoteDataSource
) : PropensityRepository {
    override suspend fun sendQuestionResult(userSurveyResult: UserSurveyResult): Result<DomainPostPropensityData> {
        return try {
            val dto = userSurveyResult.toRequestDTO // ✅ 괄호 없이 사용
            val response = dataSource.sendQuestionResult(dto)
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

    override suspend fun getPropensityList(page: Int, size: Int): Result<GetPropensityListData> {
        return try {
            val response = dataSource.getPropensityList(page, size)
            if (response.isSuccessful) {
                val body = response.body() ?: throw Exception("Empty response body")
                val data = body.toDomainModel() // ✅ DTO → Domain Model 변환
                Result.success(data)
            } else {
                throw Exception("Request failed with code ${response.code()}")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


}