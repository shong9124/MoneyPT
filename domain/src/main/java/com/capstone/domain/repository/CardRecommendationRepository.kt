package com.capstone.domain.repository

import com.capstone.domain.model.recommend.card.CardRecommendationContent
import com.capstone.domain.model.recommend.card.PostPaymentInfo
import com.capstone.domain.model.recommend.card.ResponseCardRecommendation

interface CardRecommendationRepository {

    suspend fun getCardRecommendations(
        page: Int,
        size: Int
    ): Result<List<CardRecommendationContent>>

    suspend fun sendPaymentRequest(postPaymentInfo: PostPaymentInfo): Result<ResponseCardRecommendation>

    suspend fun getDetailCardRecommendations(recommendationId: String): Result<ResponseCardRecommendation>
}