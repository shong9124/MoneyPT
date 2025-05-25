package com.capstone.domain.usecase.signUp

import com.capstone.domain.model.GetPropensityListData
import com.capstone.domain.repository.PropensityRepository
import javax.inject.Inject

class GetPropensityListUseCase @Inject constructor(
    private val propensityRepository: PropensityRepository
) {
    suspend operator fun invoke(page: Int, size: Int) : Result<GetPropensityListData> {
        return propensityRepository.getPropensityList(page, size)
    }
}