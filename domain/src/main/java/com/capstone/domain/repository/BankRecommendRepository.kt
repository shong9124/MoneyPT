package com.capstone.domain.repository

import com.capstone.domain.model.recommend.bank.GetBankData
import com.capstone.domain.model.recommend.bank.PostRecommendation
import com.capstone.domain.model.recommend.bank.ResponseRecommendation

interface BankRecommendRepository {
    suspend fun getBankProducts(page: Int, size: Int) : Result<GetBankData>

    suspend fun sendBankProductRequest(postRecommendation: PostRecommendation): Result<ResponseRecommendation>

    suspend fun getDetailBankProducts(recommendationId: String): Result<ResponseRecommendation>
}