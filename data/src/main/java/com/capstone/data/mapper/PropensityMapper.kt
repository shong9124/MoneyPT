package com.capstone.data.mapper

import com.capstone.data.model.signUp.GetPropensityListResponseDTO
import com.capstone.data.model.signUp.PropensityInfoContent
import com.capstone.data.model.signUp.PropensityPagination
import com.capstone.data.model.signUp.UserSurveyResultRequestDTO
import com.capstone.domain.model.DomainPropensityPagination
import com.capstone.domain.model.GetPropensityListData
import com.capstone.domain.model.PropensityInfo
import com.capstone.domain.model.UserSurveyResult

val UserSurveyResult.toRequestDTO
    get() = UserSurveyResultRequestDTO(
        userSurveyResult = this.userSurveyResult
    )

// 예시: com.capstone.data.mapper 패키지에 위치
fun GetPropensityListResponseDTO.toDomainModel(): GetPropensityListData {
    val content = this.data.content.map { it.toDomainModel() }
    val pagination = this.data.pagination.toDomainModel()

    return GetPropensityListData(
        // 각 필드 매핑
        content,
        pagination
    )
}

fun PropensityInfoContent.toDomainModel(): PropensityInfo {
    return PropensityInfo(
        // 각 필드 매핑
        this.id,
        this.propensity,
        this.createdAt
    )
}

fun PropensityPagination.toDomainModel(): DomainPropensityPagination {
    return DomainPropensityPagination(
        this.currentPage,
        this.totalPage
    )
}