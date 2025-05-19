package com.capstone.domain.usecase.signUp

import com.capstone.domain.model.RegisterInfo
import com.capstone.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpCompleteUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(registerInfo: RegisterInfo): Result<Boolean> {
        return authRepository.signUpComplete(registerInfo)
    }
}