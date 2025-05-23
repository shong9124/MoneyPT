package com.capstone.data.remote

import com.capstone.data.model.recommend.bank.GetBankProductsResponseDTO
import com.capstone.data.model.recommend.bank.GetDetailBankProductsResponseDTO
import com.capstone.data.model.recommend.bank.SendBankProductResponseDTO
import com.capstone.data.model.recommend.bank.SendBankProductsRequestDTO
import retrofit2.Response

interface BankProductRemoteDataSource {
    suspend fun getBankProducts(page: Int, size: Int): Response<GetBankProductsResponseDTO>

    suspend fun sendBankProductRequest(body: SendBankProductsRequestDTO): Response<SendBankProductResponseDTO>

    suspend fun getDetailBankProducts(recommendationId: String): Response<GetDetailBankProductsResponseDTO>
}