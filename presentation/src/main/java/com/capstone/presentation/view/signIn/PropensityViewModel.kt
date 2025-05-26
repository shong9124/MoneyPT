package com.capstone.presentation.view.signIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.domain.model.DomainPostPropensityData
import com.capstone.domain.model.GetPropensityListData
import com.capstone.domain.model.UserSurveyResult
import com.capstone.domain.usecase.signUp.GetPropensityListUseCase
import com.capstone.domain.usecase.signUp.PropensityUseCase
import com.capstone.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropensityViewModel @Inject constructor(
    private val propensityUseCase: PropensityUseCase,
    private val getPropensityListUseCase: GetPropensityListUseCase
) : ViewModel() {
    private val _propensityState =
        MutableLiveData<UiState<DomainPostPropensityData>>(UiState.Loading)
    private val _getPropensityListState =
        MutableLiveData<UiState<GetPropensityListData>>(UiState.Loading)
    private val _recommendationContent = MutableLiveData<DomainPostPropensityData>()

    val propensityState: LiveData<UiState<DomainPostPropensityData>> get() = _propensityState
    val getPropensityListState: LiveData<UiState<GetPropensityListData>> get() = _getPropensityListState
    val recommendationContent: LiveData<DomainPostPropensityData> get() = _recommendationContent

    fun sendQuestionResult(userSurveyResult: UserSurveyResult) {
        _propensityState.value = UiState.Loading

        viewModelScope.launch {
            propensityUseCase.invoke(userSurveyResult)
                .onSuccess {
                    _recommendationContent.value = it
                    _propensityState.value = UiState.Success(it)
                }
                .onFailure { _propensityState.value = UiState.Error(it.message.toString()) }
        }
    }

    fun getPropensityList(page: Int, size: Int) {
        _getPropensityListState.value = UiState.Loading

        viewModelScope.launch {
            getPropensityListUseCase.invoke(page, size)
                .onSuccess { _getPropensityListState.value = UiState.Success(it) }
                .onFailure { _getPropensityListState.value = UiState.Error(it.message.toString()) }
        }
    }

    fun clearData() {
        _propensityState.value = UiState.Loading
    }
}