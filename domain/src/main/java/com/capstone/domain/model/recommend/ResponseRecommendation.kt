package com.capstone.domain.model.recommend

data class ResponseRecommendation(
    val content: RecommendationList,
    val createdAt: String,
    val id: String,
    val strategy: String
)