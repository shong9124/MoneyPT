package com.capstone.domain.repository

import com.capstone.domain.model.GetPropensityListData
import com.capstone.domain.model.UserSurveyResult

interface PropensityRepository {
    suspend fun sendQuestionResult(userSurveyResult: UserSurveyResult) : Result<Boolean>

    suspend fun getPropensityList(page: Int, size: Int) : Result<GetPropensityListData>
}