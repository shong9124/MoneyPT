package com.capstone.domain.usecase.recommend.card

import com.capstone.domain.model.recommend.card.PostPaymentInfo
import com.capstone.domain.model.recommend.card.ResponseCardRecommendation
import com.capstone.domain.repository.CardRecommendationRepository
import java.io.File
import javax.inject.Inject

class SendPaymentRequestUseCase @Inject constructor(
    private val cardRecommendationRepository: CardRecommendationRepository
) {
    suspend operator fun invoke(
        file: File,
        password: String
    ): Result<ResponseCardRecommendation> {
        return cardRecommendationRepository.uploadEncryptedExcel(file, password)
    }
}