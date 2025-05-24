package com.capstone.data.mapper

import com.capstone.data.model.recommend.bank.RecommendationListDTO
import com.capstone.data.model.recommend.bank.SendBankProductsRequestDTO
import com.capstone.domain.model.recommend.PostRecommendation
import com.capstone.domain.model.recommend.RecommendationList
import com.capstone.domain.model.recommend.Recommendations

val PostRecommendation.toDTO
    get() = SendBankProductsRequestDTO(this.amount, this.propensity, this.term)

fun com.capstone.data.model.recommend.bank.Recommendations.toDomain(): com.capstone.domain.model.recommend.Recommendations {
    return com.capstone.domain.model.recommend.Recommendations(
        id = this.id,
        description = this.description,
        reason = this.reason,
        detailUrl = this.detailUrl
    )
}

fun List<com.capstone.data.model.recommend.bank.Recommendations>.toDomainList(): List<com.capstone.domain.model.recommend.Recommendations> {
    return map { it.toDomain() }
}

fun RecommendationListDTO.toDomain(): RecommendationList {
    return RecommendationList(
        recommendations = recommendations.map { it.toDomain() }
    )
}

fun Recommendations.toDomain(): Recommendations {
    return Recommendations(
        id = id,
        description = description,
        reason = reason,
        detailUrl = detailUrl
    )
}
