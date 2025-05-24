package com.capstone.data.mapper

import com.capstone.data.model.recommend.card.ContentForGetDetail
import com.capstone.data.model.recommend.card.ContentForPost
import com.capstone.data.model.recommend.card.PostPaymentInfoDTO
import com.capstone.data.model.recommend.card.Recommendation
import com.capstone.data.model.recommend.card.RecommendationX
import com.capstone.domain.model.recommend.card.CardRecommendationList
import com.capstone.domain.model.recommend.card.CardRecommendations
import com.capstone.domain.model.recommend.card.PostPaymentInfo

val PostPaymentInfo.toDTO
    get() = PostPaymentInfoDTO(this.file)

fun ContentForPost.toDomain(): CardRecommendationList {
    return CardRecommendationList(
        recommendations = recommendations.map { it.toDomain() }
    )
}

fun ContentForGetDetail.toDomain(): CardRecommendationList {
    return CardRecommendationList(
        recommendations = recommendations.map { it.toDomain() }
    )

}

fun RecommendationX.toDomain(): CardRecommendations {
    return CardRecommendations(
        id = id,
        description = description,
        reason = reason,
        detailUrl = detailUrl,
        imageUrl = imageUrl
    )
}

fun Recommendation.toDomain(): CardRecommendations {
    return CardRecommendations(
        id = id,
        description = description,
        reason = reason,
        detailUrl = detailUrl,
        imageUrl = imageUrl
    )
}