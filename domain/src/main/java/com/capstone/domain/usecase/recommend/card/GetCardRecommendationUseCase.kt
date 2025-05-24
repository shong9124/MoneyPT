package com.capstone.domain.usecase.recommend.card

import com.capstone.domain.model.recommend.card.CardRecommendationContent
import com.capstone.domain.repository.CardRecommendationRepository
import javax.inject.Inject

class GetCardRecommendationUseCase @Inject constructor(
    private val cardRecommendationRepository: CardRecommendationRepository
) {
    suspend operator fun invoke(page: Int, size: Int) : Result<List<CardRecommendationContent>> {
        return cardRecommendationRepository.getCardRecommendations(page, size)
    }
}