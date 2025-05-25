package com.capstone.domain.model

data class GetPropensityListData (
    val content: List<PropensityInfo>,
    val pagination: DomainPropensityPagination
)

data class PropensityInfo(
    val id: String,
    val propensity: String,
    val createdAt: String
)

data class DomainPropensityPagination(
    val currentPage: Int,
    val totalPage: Int
)