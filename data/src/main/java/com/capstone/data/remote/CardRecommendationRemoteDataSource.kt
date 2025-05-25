package com.capstone.data.remote

import com.capstone.data.model.recommend.card.GetCardDetailRecommendationResponseDTO
import com.capstone.data.model.recommend.card.GetCardRecommendationsResponseDTO
import com.capstone.data.model.recommend.card.PostPaymentInfoResponseDTO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface CardRecommendationRemoteDataSource {

    suspend fun getCardRecommendations(
        page: Int,
        size: Int
    ): Response<GetCardRecommendationsResponseDTO>

    suspend fun uploadEncryptedExcel(
        file: MultipartBody.Part,
        password: RequestBody
    ): Response<PostPaymentInfoResponseDTO>

    suspend fun getDetailCardRecommendations(
        recommendationId: String
    ): Response<GetCardDetailRecommendationResponseDTO>
}