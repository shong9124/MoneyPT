package com.capstone.data.repository

import com.capstone.data.mapper.toDomain
import com.capstone.data.remote.CardRecommendationRemoteDataSource
import com.capstone.domain.model.recommend.card.CardRecommendationContent
import com.capstone.domain.model.recommend.card.ResponseCardRecommendation
import com.capstone.domain.repository.CardRecommendationRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class CardRecommendationRepositoryImpl @Inject constructor(
    private val dataSource: CardRecommendationRemoteDataSource
) : CardRecommendationRepository {

    override suspend fun getCardRecommendations(
        page: Int,
        size: Int
    ): Result<List<CardRecommendationContent>> {
        return try {
            val response = dataSource.getCardRecommendations(page, size)
            val body = response.body()

            if (response.isSuccessful && body != null) {
                Result.success(body.data.content.map {
                    CardRecommendationContent(
                        createdAt = it.createdAt,
                        id = it.id,
                        strategy = it.strategy
                    )
                })
            } else {
                Result.failure(Exception("getCardRecommendations failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun uploadEncryptedExcel(
        file: File,
        password: String
    ): Result<ResponseCardRecommendation> {
        return try {
            val fileRequestBody = file.asRequestBody(
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".toMediaTypeOrNull()
            )
            val multipartFile = MultipartBody.Part.createFormData("file", file.name, fileRequestBody)

            val passwordBody = password.toRequestBody("text/plain".toMediaTypeOrNull())

            val response = dataSource.uploadEncryptedExcel(multipartFile, passwordBody)
            val body = response.body()

            if (response.isSuccessful && body != null && body.data != null) {
                val content = body.data
                Result.success(
                    ResponseCardRecommendation(
                        content = content.content.toDomain(),
                        createdAt = content.createdAt,
                        id = content.id,
                        strategy = content.strategy
                    )
                )
            } else {
                Result.failure(Exception("uploadEncryptedExcel failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDetailCardRecommendations(
        recommendationId: String
    ): Result<ResponseCardRecommendation> {
        return try {
            val response = dataSource.getDetailCardRecommendations(recommendationId)
            val body = response.body()

            if (response.isSuccessful && body != null && body.data != null) {
                val content = body.data
                Result.success(
                    ResponseCardRecommendation(
                        content = content.content.toDomain(),
                        createdAt = content.createdAt,
                        id = content.id,
                        strategy = content.strategy
                    )
                )
            } else {
                Result.failure(Exception("getDetailCardRecommendations failed: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
