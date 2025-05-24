package com.capstone.data.remote

import com.capstone.data.model.recommend.card.GetCardDetailRecommendationResponseDTO
import com.capstone.data.model.recommend.card.GetCardRecommendationsResponseDTO
import com.capstone.data.model.recommend.card.PostPaymentInfoDTO
import com.capstone.data.model.recommend.card.PostPaymentInfoResponseDTO
import com.capstone.data.service.CardRecommendService
import com.capstone.data.util.MySharedPreferences
import com.capstone.data.util.MySharedPreferences.Companion.KEY_ACCESS_TOKEN
import retrofit2.Response
import javax.inject.Inject

class CardRecommendationRemoteDataSourceImpl @Inject constructor(
    private val sharedPreferences: MySharedPreferences, private val service: CardRecommendService
) : CardRecommendationRemoteDataSource {
    override suspend fun getCardRecommendations(
        page: Int,
        size: Int
    ): Response<GetCardRecommendationsResponseDTO> {
        val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, "")
        val response = service.getCardRecommendations(accessToken, page, size)
        return response
    }

    override suspend fun sendPaymentRequest(body: PostPaymentInfoDTO): Response<PostPaymentInfoResponseDTO> {
        val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, "")
        val response = service.sendPaymentRequest(accessToken, body)
        return response
    }

    override suspend fun getDetailCardRecommendations(recommendationId: String): Response<GetCardDetailRecommendationResponseDTO> {
        val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, "")
        val response = service.getDetailCardRecommendations(accessToken, recommendationId)
        return response
    }
}