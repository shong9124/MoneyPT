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

fun PatchUserPropensityResponseDTO.toDomain() : PatchPropensity {
    val data = this.data.toDomain()

    return PatchPropensity(
        data,
        this.message,
        this.statusCode,
        this.timeStamp
    )
}

fun UserPropensityData.toDomain() : DomainPropensityData {
    return DomainPropensityData()
}