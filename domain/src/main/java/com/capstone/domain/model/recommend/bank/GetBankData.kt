package com.capstone.domain.model.recommend.bank


data class GetBankData(
    val content: List<RecommendationContent>,
    val pagination: DomainPagination
)