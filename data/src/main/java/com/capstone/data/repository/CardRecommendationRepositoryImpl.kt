package com.capstone.data.repository

import com.capstone.data.mapper.toDTO
import com.capstone.data.mapper.toDomain
import com.capstone.data.remote.CardRecommendationRemoteDataSource
import com.capstone.domain.model.recommend.card.CardRecommendationContent
import com.capstone.domain.model.recommend.card.PostPaymentInfo
import com.capstone.domain.model.recommend.card.ResponseCardRecommendation
import com.capstone.domain.repository.CardRecommendationRepository
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

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.data.content.map {
                        CardRecommendationContent(
                            it.createdAt,
                            it.id,
                            it.strategy
                        )
                    })
                } else {
                    throw Exception("Body is null")
                }
            } else {
                throw Exception("Request is failure")
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun sendPaymentRequest(postPaymentInfo: PostPaymentInfo): Result<ResponseCardRecommendation> {
        return try {
            val response = dataSource.sendPaymentRequest(postPaymentInfo.toDTO)

            if (response.isSuccessful) {
                val body = response.body()
                val content = body?.data
                if (content != null) {
                    Result.success(
                        ResponseCardRecommendation(
                            content = content.content.toDomain(),
                            createdAt = content.createdAt,
                            id = content.id,
                            strategy = content.strategy
                        )
                    )
                } else {
                    Result.failure(Exception("Body data is null"))
                }
            } else {
                Result.failure(Exception("Response not successful"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getDetailCardRecommendations(recommendationId: String): Result<ResponseCardRecommendation> {
        return try {
            val response = dataSource.getDetailCardRecommendations(recommendationId)

            if (response.isSuccessful) {
                val body = response.body()
                val content = body?.data
                if (content != null) {
                    Result.success(
                        ResponseCardRecommendation(
                            content = content.content.toDomain(),
                            createdAt = content.createdAt,
                            id = content.id,
                            strategy = content.strategy
                        )
                    )
                } else {
                    Result.failure(Exception("Body data is null"))
                }
            } else {
                Result.failure(Exception("Response not successful"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}