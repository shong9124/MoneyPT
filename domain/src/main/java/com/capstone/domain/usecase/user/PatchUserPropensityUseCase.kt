package com.capstone.domain.usecase.user

import com.capstone.domain.model.PatchPropensity
import com.capstone.domain.repository.UserInfoRepository
import javax.inject.Inject

class PatchUserPropensityUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) {
    suspend operator fun invoke(userPropensityId: String): Result<PatchPropensity> {
        return userInfoRepository.patchUserPropensity(userPropensityId)
    }
}