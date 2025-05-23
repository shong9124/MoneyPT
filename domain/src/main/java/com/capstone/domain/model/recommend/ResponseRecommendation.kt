package com.capstone.domain.model.recommend

data class ResponseRecommendation(
    val content: String,
    val createdAt: String,
    val id: String,
    val strategy: String
)