package com.capstone.domain.repository

import com.capstone.domain.model.recommend.PostRecommendation
import com.capstone.domain.model.recommend.RecommendationContent
import com.capstone.domain.model.recommend.ResponseRecommendation

interface BankRecommendRepository {
    suspend fun getBankProducts(page: Int, size: Int) : Result<List<RecommendationContent>>

    suspend fun sendBankProductRequest(postRecommendation: PostRecommendation): Result<ResponseRecommendation>

    suspend fun getDetailBankProducts(recommendationId: String): Result<ResponseRecommendation>
}