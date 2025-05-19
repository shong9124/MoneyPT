package com.capstone.domain.usecase.signUp

import com.capstone.domain.model.UserSurveyResult
import com.capstone.domain.repository.PropensityRepository
import javax.inject.Inject

class PropensityUseCase @Inject constructor(
    private val propensityRepository: PropensityRepository
) {
    suspend operator fun invoke(userSurveyResult: UserSurveyResult): Result<Boolean> {
        return propensityRepository.sendQuestionResult(userSurveyResult)
    }
}