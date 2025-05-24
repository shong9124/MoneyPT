package com.capstone.domain.usecase.recommend

import com.capstone.domain.model.recommend.bank.PostRecommendation
import com.capstone.domain.model.recommend.bank.ResponseRecommendation
import com.capstone.domain.repository.BankRecommendRepository
import javax.inject.Inject

class SendBankProductRequestUseCase @Inject constructor(
    private val bankRecommendRepository: BankRecommendRepository
) {
    suspend operator fun invoke(postRecommendation: PostRecommendation) : Result<ResponseRecommendation> {
        return bankRecommendRepository.sendBankProductRequest(postRecommendation)
    }
}