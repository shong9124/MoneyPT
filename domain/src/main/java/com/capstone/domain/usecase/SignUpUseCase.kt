package com.capstone.domain.usecase

import com.capstone.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(email: String, password: String): Result<Boolean> {
        return authRepository.signUp(email, password)
    }
}