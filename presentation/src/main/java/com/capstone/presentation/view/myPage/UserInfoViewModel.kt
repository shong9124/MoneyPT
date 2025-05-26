package com.capstone.presentation.view.myPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.domain.model.GetUserInfo
import com.capstone.domain.model.PatchPropensity
import com.capstone.domain.usecase.user.GetUserInfoUseCase
import com.capstone.domain.usecase.user.PatchUserPropensityUseCase
import com.capstone.presentation.util.UiState
import com.capstone.util.LoggerUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val userInfoUseCase: GetUserInfoUseCase,
    private val patchUserPropensityUseCase: PatchUserPropensityUseCase
) : ViewModel() {

    private val _userInfoState = MutableLiveData<UiState<GetUserInfo>>(UiState.Loading)
    private val _patchUserPropensityState =
        MutableLiveData<UiState<PatchPropensity>>(UiState.Loading)

    val userInfoState: LiveData<UiState<GetUserInfo>> get() = _userInfoState
    val patchUserPropensityState: LiveData<UiState<PatchPropensity>> get() = _patchUserPropensityState

    fun getUserInfo() {
        _userInfoState.value = UiState.Loading

        viewModelScope.launch {
            userInfoUseCase.invoke()
                .onSuccess { _userInfoState.value = UiState.Success(it) }
                .onFailure { _userInfoState.value = UiState.Error(it.message.toString()) }
        }
    }

    fun patchUserPropensity(userPropensityId: String) {
        _patchUserPropensityState.value = UiState.Loading

        viewModelScope.launch {
            patchUserPropensityUseCase.invoke(userPropensityId)
                .onSuccess {
                    _patchUserPropensityState.value = UiState.Success(it)
                    LoggerUtil.d("금융 성향 분석 갱신 성공.")
                }
                .onFailure {
                    _patchUserPropensityState.value = UiState.Error(it.message.toString())
                }
        }
    }
}