package com.capstone.domain.model

data class PatchPropensity(
    val data: DomainPropensityData,
    val message: String,
    val statusCode: String,
    val timeStamp: String
)