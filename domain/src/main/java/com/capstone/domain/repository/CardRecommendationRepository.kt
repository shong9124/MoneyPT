package com.capstone.domain.repository

import com.capstone.domain.model.recommend.card.CardRecommendationContent
import com.capstone.domain.model.recommend.card.ResponseCardRecommendation
import java.io.File

interface CardRecommendationRepository {

    suspend fun getCardRecommendations(
        page: Int,
        size: Int
    ): Result<List<CardRecommendationContent>>

    suspend fun uploadEncryptedExcel(
        file: File,
        password: String
    ): Result<ResponseCardRecommendation>

    suspend fun getDetailCardRecommendations(recommendationId: String): Result<ResponseCardRecommendation>
}