package com.capstone.data.remote

import com.capstone.data.model.recommend.card.GetCardDetailRecommendationResponseDTO
import com.capstone.data.model.recommend.card.GetCardRecommendationsResponseDTO
import com.capstone.data.model.recommend.card.PostPaymentInfoDTO
import com.capstone.data.model.recommend.card.PostPaymentInfoResponseDTO
import retrofit2.Response

interface CardRecommendationRemoteDataSource {

    suspend fun getCardRecommendations(
        page: Int,
        size: Int
    ): Response<GetCardRecommendationsResponseDTO>

    suspend fun sendPaymentRequest(
        body: PostPaymentInfoDTO
    ): Response<PostPaymentInfoResponseDTO>

    suspend fun getDetailCardRecommendations(
        recommendationId: String
    ): Response<GetCardDetailRecommendationResponseDTO>
}