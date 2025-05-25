package com.capstone.data.service

import com.capstone.data.model.recommend.card.GetCardDetailRecommendationResponseDTO
import com.capstone.data.model.recommend.card.GetCardRecommendationsResponseDTO
import com.capstone.data.model.recommend.card.PostPaymentInfoResponseDTO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface CardRecommendService {

    @GET("/card-products/recommendations")
    suspend fun getCardRecommendations(
        @Header("Authorization") accessToken: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<GetCardRecommendationsResponseDTO>

    @Multipart
    @POST("/card-products/recommendations")
    suspend fun uploadEncryptedExcel(
        @Header("Authorization") accessToken: String,
        @Part file: MultipartBody.Part,
        @Part("password") password: RequestBody
    ): Response<PostPaymentInfoResponseDTO>

    @GET("/card-products/recommendations/{recommendation-id}")
    suspend fun getDetailCardRecommendations(
        @Header("Authorization") accessToken: String,
        @Path("recommendation-id") recommendationId: String
    ): Response<GetCardDetailRecommendationResponseDTO>
}