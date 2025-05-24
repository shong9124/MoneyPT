package com.capstone.data.repository

import com.capstone.data.mapper.toDTO
import com.capstone.data.mapper.toDomain
import com.capstone.data.remote.BankProductRemoteDataSource
import com.capstone.domain.model.recommend.bank.PostRecommendation
import com.capstone.domain.model.recommend.bank.RecommendationContent
import com.capstone.domain.model.recommend.bank.ResponseRecommendation
import com.capstone.domain.repository.BankRecommendRepository
import javax.inject.Inject

class BankRecommendRepositoryImpl @Inject constructor(
    private val dataSource: BankProductRemoteDataSource
): BankRecommendRepository {
    override suspend fun getBankProducts(
        page: Int,
        size: Int
    ): Result<List<RecommendationContent>> {
        return try {
            val response = dataSource.getBankProducts(page, size)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.data.content.map {
                        RecommendationContent(
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

    override suspend fun sendBankProductRequest(postRecommendation: PostRecommendation): Result<ResponseRecommendation> {
        return try {
            val response = dataSource.sendBankProductRequest(postRecommendation.toDTO)
            if (response.isSuccessful) {
                val body = response.body()
                val content = body?.data
                if (content != null) {
                    Result.success(
                        ResponseRecommendation(
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

    override suspend fun getDetailBankProducts(recommendationId: String): Result<ResponseRecommendation> {
        return try {
            val response = dataSource.getDetailBankProducts(recommendationId)

            if (response.isSuccessful) {
                val body = response.body()
                val content = body?.data
                if (content != null) {
                    Result.success(
                        ResponseRecommendation(
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