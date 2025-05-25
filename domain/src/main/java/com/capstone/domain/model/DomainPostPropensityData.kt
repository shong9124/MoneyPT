package com.capstone.domain.model

data class DomainPostPropensityData (
    val id: String,
    val propensityAnalysis: PropensityContent
)

data class PropensityContent (
    val type: String,
    val description: String,
    val prosAndCons: String,
    val precaution: String
)