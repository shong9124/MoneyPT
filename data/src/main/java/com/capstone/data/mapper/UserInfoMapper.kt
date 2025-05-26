package com.capstone.data.mapper

import com.capstone.data.model.user.GetUserInfoResponseDTO
import com.capstone.data.model.user.PatchUserPropensityResponseDTO
import com.capstone.data.model.user.UserPropensityData
import com.capstone.domain.model.DomainPropensityData
import com.capstone.domain.model.GetUserInfo
import com.capstone.domain.model.PatchPropensity

fun GetUserInfoResponseDTO.toDomain() : GetUserInfo {
    val data = this.data

    return GetUserInfo(
        data.id,
        data.email,
        data.registerCompleted,
        data.nickname,
        data.birthDate,
        data.asset,
        data.salary,
        data.gender,
        data.propensity
    )
}

fun PatchUserPropensityResponseDTO.toDomain(): PatchPropensity {
    val domainData = this.data?.toDomain()
    return PatchPropensity(
        data = domainData ?: DomainPropensityData(),  // data가 null이면 빈 도메인 객체 반환
        message = this.message,
        statusCode = this.statusCode,
        timeStamp = this.timeStamp
    )
}

fun UserPropensityData.toDomain(): DomainPropensityData {
    return DomainPropensityData()  // 빈 데이터 클래스라도 이렇게 처리 가능
}