package com.capstone.domain.usecase.recommend.card

import com.capstone.domain.model.recommend.card.ResponseCardRecommendation
import com.capstone.domain.repository.CardRecommendationRepository
import javax.inject.Inject

class GetDetailCardRecommendationUseCase @Inject constructor(
    private val cardRecommendationRepository: CardRecommendationRepository
) {
    suspend operator fun invoke(recommendationId: String) : Result<ResponseCardRecommendation> {
        return cardRecommendationRepository.getDetailCardRecommendations(recommendationId)
    }
}