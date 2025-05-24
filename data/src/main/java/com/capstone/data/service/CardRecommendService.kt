package com.capstone.data.service

import com.capstone.data.model.recommend.card.GetCardDetailRecommendationResponseDTO
import com.capstone.data.model.recommend.card.GetCardRecommendationsResponseDTO
import com.capstone.data.model.recommend.card.PostPaymentInfoDTO
import com.capstone.data.model.recommend.card.PostPaymentInfoResponseDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CardRecommendService {

    @GET("/card-products/recommendations")
    suspend fun getCardRecommendations(
        @Header("Authorization") accessToken: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<GetCardRecommendationsResponseDTO>

    @POST("/card-products/recommendations")
    suspend fun sendPaymentRequest(
        @Header("Authorization") accessToken: String,
        @Body body: PostPaymentInfoDTO
    ): Response<PostPaymentInfoResponseDTO>

    @GET("/card-products/recommendations/{recommendation-id}")
    suspend fun getDetailCardRecommendations(
        @Header("Authorization") accessToken: String,
        @Path("recommendation-id") recommendationId: String
    ): Response<GetCardDetailRecommendationResponseDTO>
}