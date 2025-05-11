package com.capstone.presentation.view.signIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.domain.model.RegisterInfo
import com.capstone.domain.usecase.LoginUseCase
import com.capstone.domain.usecase.SignUpCompleteUseCase
import com.capstone.domain.usecase.SignUpUseCase
import com.capstone.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val signUpCompleteUseCase: SignUpCompleteUseCase
): ViewModel() {
    private val _loginState = MutableLiveData<UiState<Boolean>>(UiState.Loading)
    private val _signUpState = MutableLiveData<UiState<Boolean>>(UiState.Loading)
    private val _signUpCompleteState = MutableLiveData<UiState<RegisterInfo>>(UiState.Loading)

    val loginState: LiveData<UiState<Boolean>> get() = _loginState
    val signUpState: LiveData<UiState<Boolean>> get() = _signUpState
    val signUpCompleteState: LiveData<UiState<RegisterInfo>> get() = _signUpCompleteState

    fun login(email: String, password: String) {
        _loginState.value = UiState.Loading

        viewModelScope.launch {
            loginUseCase.invoke(email, password)
                .onSuccess { _loginState.value = UiState.Success(it) }
                .onFailure { _loginState.value = UiState.Error(it.message.toString()) }
        }
    }

    fun signUp(email: String, password:String) {
        _signUpState.value = UiState.Loading

        viewModelScope.launch {
            signUpUseCase.invoke(email, password)
                .onSuccess { _loginState.value = UiState.Success(it) }
                .onFailure { _loginState.value = UiState.Error(it.message.toString()) }
        }
    }

    fun signUpComplete(info: RegisterInfo) {
        _signUpCompleteState.value = UiState.Loading

        viewModelScope.launch {
            signUpCompleteUseCase.invoke(info)
                .onSuccess { _loginState.value = UiState.Success(it) }
                .onFailure { _loginState.value = UiState.Error(it.message.toString()) }
        }
    }
}