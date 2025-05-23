package com.capstone.data.service

import com.capstone.data.model.recommend.bank.GetBankProductsResponseDTO
import com.capstone.data.model.recommend.bank.GetDetailBankProductsResponseDTO
import com.capstone.data.model.recommend.bank.SendBankProductResponseDTO
import com.capstone.data.model.recommend.bank.SendBankProductsRequestDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BankProductService {
    @GET("/bank-products/recommendations")
    suspend fun getBankProducts(
        @Header("Authorization") accessToken: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<GetBankProductsResponseDTO>

    @POST("/bank-products/recommendations")
    suspend fun sendBankProductRequest(
        @Header("Authorization") accessToken: String,
        @Body body: SendBankProductsRequestDTO
    ): Response<SendBankProductResponseDTO>

    @GET("/bank-products/recommendations/{recommendation-id}")
    suspend fun getDetailBankProducts(
        @Header("Authorization") accessToken: String,
        @Path("recommendation-id") recommendationId: String
    ): Response<GetDetailBankProductsResponseDTO>
}