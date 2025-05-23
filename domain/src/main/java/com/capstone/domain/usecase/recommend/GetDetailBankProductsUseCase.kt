package com.capstone.domain.usecase.recommend

import com.capstone.domain.model.recommend.ResponseRecommendation
import com.capstone.domain.repository.BankRecommendRepository
import javax.inject.Inject

class GetDetailBankProductsUseCase @Inject constructor(
    private val bankRecommendRepository: BankRecommendRepository
) {
    suspend operator fun invoke(recommendationId: String) : Result<ResponseRecommendation> {
        return bankRecommendRepository.getDetailBankProducts(recommendationId)
    }
}