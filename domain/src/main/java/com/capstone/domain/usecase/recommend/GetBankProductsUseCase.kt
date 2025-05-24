package com.capstone.domain.usecase.recommend

import com.capstone.domain.model.recommend.bank.RecommendationContent
import com.capstone.domain.repository.BankRecommendRepository
import javax.inject.Inject

class GetBankProductsUseCase @Inject constructor(
    private val bankRecommendRepository: BankRecommendRepository
){
    suspend operator fun invoke(page: Int, size: Int): Result<List<RecommendationContent>> {
        return bankRecommendRepository.getBankProducts(page, size)
    }
}