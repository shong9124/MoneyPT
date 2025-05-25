package com.capstone.domain.model

data class GetUserInfo(
    val id: String,
    val email: String,
    val resisterCompleted: Boolean,
    val nickname: String,
    val birthDate: String,
    val asset: Int,
    val salary: Int,
    val gender: String,
    val propensity: String
)
