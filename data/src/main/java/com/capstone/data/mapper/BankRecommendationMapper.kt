package com.capstone.data.mapper

import com.capstone.data.model.recommend.bank.GetData
import com.capstone.data.model.recommend.bank.Pagination
import com.capstone.data.model.recommend.bank.RecommendationListDTO
import com.capstone.data.model.recommend.bank.SendBankProductsRequestDTO
import com.capstone.domain.model.recommend.bank.DomainPagination
import com.capstone.domain.model.recommend.bank.GetBankData
import com.capstone.domain.model.recommend.bank.PostRecommendation
import com.capstone.domain.model.recommend.bank.RecommendationList
import com.capstone.domain.model.recommend.bank.Recommendations
import com.capstone.data.model.recommend.bank.Content as DataContent
import com.capstone.domain.model.recommend.bank.RecommendationContent

// 확장 함수: Pagination(data) → Pagination(domain)
fun Pagination.toDomain(): DomainPagination {
    return DomainPagination(
        currentPage = this.currentPage,
        totalPage = this.totalPage
    )
}

// 확장 함수: GetData → GetBankData
fun GetData.toGetBankData(): GetBankData {
    val mappedContent = content.map { dataContent: DataContent ->
        RecommendationContent(
            createdAt = dataContent.createdAt,
            id = dataContent.id,
            strategy = dataContent.strategy
        )
    }

    return GetBankData(
        content = mappedContent,
        pagination = pagination.toDomain() // ✅ 인스턴스로 호출해야 함
    )
}

val PostRecommendation.toDTO
    get() = SendBankProductsRequestDTO(this.amount, this.propensity, this.term)

fun com.capstone.data.model.recommend.bank.Recommendations.toDomain(): Recommendations {
    return Recommendations(
        id = this.id,
        description = this.description,
        reason = this.reason,
        detailUrl = this.detailUrl
    )
}

fun List<com.capstone.data.model.recommend.bank.Recommendations>.toDomainList(): List<Recommendations> {
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
