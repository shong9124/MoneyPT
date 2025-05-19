package com.capstone.presentation.view.signIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.domain.model.UserSurveyResult
import com.capstone.domain.usecase.signUp.PropensityUseCase
import com.capstone.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PropensityViewModel @Inject constructor(
    private val propensityUseCase: PropensityUseCase
): ViewModel() {
    private val _propensityState = MutableLiveData<UiState<Boolean>>(UiState.Loading)

    val propensityState: LiveData<UiState<Boolean>> get() = _propensityState

    fun sendQuestionResult(userSurveyResult: UserSurveyResult) {
        _propensityState.value = UiState.Loading

        viewModelScope.launch {
            propensityUseCase.invoke(userSurveyResult)
                .onSuccess { _propensityState.value = UiState.Success(it) }
                .onFailure { _propensityState.value = UiState.Error(it.message.toString()) }
        }
    }
}