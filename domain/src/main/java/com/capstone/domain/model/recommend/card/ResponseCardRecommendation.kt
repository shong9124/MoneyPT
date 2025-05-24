package com.capstone.domain.model.recommend.card

import com.capstone.domain.model.recommend.bank.RecommendationList

data class ResponseCardRecommendation(
    val content: CardRecommendationList,
    val createdAt: String,
    val id: String,
    val strategy: String
)
