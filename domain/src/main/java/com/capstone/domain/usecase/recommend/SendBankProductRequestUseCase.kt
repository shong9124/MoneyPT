package com.capstone.domain.usecase.recommend

import com.capstone.domain.model.recommend.PostRecommendation
import com.capstone.domain.model.recommend.ResponseRecommendation
import com.capstone.domain.repository.BankRecommendRepository
import javax.inject.Inject

class SendBankProductRequestUseCase @Inject constructor(
    private val bankRecommendRepository: BankRecommendRepository
) {
    suspend operator fun invoke(postRecommendation: PostRecommendation) : Result<ResponseRecommendation> {
        return bankRecommendRepository.sendBankProductRequest(postRecommendation)
    }
}