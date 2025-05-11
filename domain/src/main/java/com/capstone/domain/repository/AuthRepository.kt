package com.capstone.domain.repository

import com.capstone.domain.model.RegisterInfo

interface AuthRepository {

    suspend fun signUp(email: String, password: String): Result<Boolean>

    suspend fun signUpComplete(
        registerInfo: RegisterInfo
    ): Result<Boolean>

    suspend fun login(email: String, password: String): Result<Boolean>
}