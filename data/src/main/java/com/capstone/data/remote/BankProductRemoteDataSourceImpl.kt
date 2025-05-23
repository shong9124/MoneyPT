package com.capstone.data.remote

import com.capstone.data.model.recommend.bank.GetBankProductsResponseDTO
import com.capstone.data.model.recommend.bank.GetDetailBankProductsResponseDTO
import com.capstone.data.model.recommend.bank.SendBankProductResponseDTO
import com.capstone.data.model.recommend.bank.SendBankProductsRequestDTO
import com.capstone.data.service.BankProductService
import com.capstone.data.util.MySharedPreferences
import com.capstone.data.util.MySharedPreferences.Companion.KEY_ACCESS_TOKEN
import retrofit2.Response
import javax.inject.Inject

class BankProductRemoteDataSourceImpl @Inject constructor(
    private val sharedPreferences: MySharedPreferences, private val service: BankProductService
) : BankProductRemoteDataSource {
    override suspend fun getBankProducts(
        page: Int, size: Int
    ): Response<GetBankProductsResponseDTO> {
        val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, "")
        val response = service.getBankProducts(accessToken, page, size)
        return response
    }

    override suspend fun sendBankProductRequest(body: SendBankProductsRequestDTO): Response<SendBankProductResponseDTO> {
        val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, "")
        val response = service.sendBankProductRequest(accessToken, body)
        return response
    }

    override suspend fun getDetailBankProducts(recommendationId: String): Response<GetDetailBankProductsResponseDTO> {
        val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, "")
        val response = service.getDetailBankProducts(accessToken, recommendationId)

        return response
    }
}