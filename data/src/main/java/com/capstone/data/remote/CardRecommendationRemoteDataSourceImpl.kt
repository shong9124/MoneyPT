package com.capstone.data.remote

import com.capstone.data.model.recommend.card.GetCardDetailRecommendationResponseDTO
import com.capstone.data.model.recommend.card.GetCardRecommendationsResponseDTO
import com.capstone.data.model.recommend.card.PostPaymentInfoDTO
import com.capstone.data.model.recommend.card.PostPaymentInfoResponseDTO
import com.capstone.data.service.CardRecommendService
import com.capstone.data.util.MySharedPreferences
import com.capstone.data.util.MySharedPreferences.Companion.KEY_ACCESS_TOKEN
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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

    override suspend fun uploadEncryptedExcel(
        file: MultipartBody.Part,
        password: RequestBody
    ): Response<PostPaymentInfoResponseDTO> {
        val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, "")
        return service.uploadEncryptedExcel(
            accessToken = accessToken, // 필요에 따라 가져오기
            file = file,
            password = password
        )
    }

    override suspend fun getDetailCardRecommendations(recommendationId: String): Response<GetCardDetailRecommendationResponseDTO> {
        val accessToken = sharedPreferences.getString(KEY_ACCESS_TOKEN, "")
        val response = service.getDetailCardRecommendations(accessToken, recommendationId)
        return response
    }
}