package com.capstone.domain.model.recommend.bank

data class PostRecommendation(
    val amount: Long,
    val propensity: String,
    val term: Int
)