package com.capstone.domain.usecase.user

import com.capstone.domain.model.GetUserInfo
import com.capstone.domain.repository.UserInfoRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val userInfoRepository: UserInfoRepository
) {
    suspend operator fun invoke() : Result<GetUserInfo> {
        return userInfoRepository.getUserInfo()
    }
}