package com.capstone.presentation.view.recommend

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.domain.model.recommend.PostRecommendation
import com.capstone.domain.model.recommend.RecommendationContent
import com.capstone.domain.model.recommend.ResponseRecommendation
import com.capstone.domain.usecase.recommend.GetBankProductsUseCase
import com.capstone.domain.usecase.recommend.GetDetailBankProductsUseCase
import com.capstone.domain.usecase.recommend.SendBankProductRequestUseCase
import com.capstone.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BankProductViewModel @Inject constructor(
    private val getBankProductsUseCase: GetBankProductsUseCase,
    private val sendBankProductRequestUseCase: SendBankProductRequestUseCase,
    private val getDetailBankProductsUseCase: GetDetailBankProductsUseCase
): ViewModel() {

    private val _getBankProductsState = MutableLiveData<UiState<List<RecommendationContent>>>(UiState.Loading)
    private val _sendBankProductRequestState = MutableLiveData<UiState<ResponseRecommendation>>(UiState.Loading)
    private val _getDetailBankProductsState = MutableLiveData<UiState<ResponseRecommendation>>(UiState.Loading)

    val getBankProductsState : LiveData<UiState<List<RecommendationContent>>> get() = _getBankProductsState
    val sendBankProductRequestState : LiveData<UiState<ResponseRecommendation>> get() = _sendBankProductRequestState
    val getDetailBankProductsState : LiveData<UiState<ResponseRecommendation>> get() = _getDetailBankProductsState

    fun getBankProducts(page: Int, size: Int) {
        _getBankProductsState.value = UiState.Loading

        viewModelScope.launch{
            getBankProductsUseCase.invoke(page, size)
                .onSuccess { _getBankProductsState.value = UiState.Success(it) }
                .onFailure { _getBankProductsState.value = UiState.Error(it.message.toString()) }
        }
    }

    fun sendBankProductRequest(postRecommendation: PostRecommendation) {
        _sendBankProductRequestState.value = UiState.Loading

        viewModelScope.launch{
            sendBankProductRequestUseCase.invoke(postRecommendation)
                .onSuccess { _sendBankProductRequestState.value = UiState.Success(it) }
                .onFailure { _sendBankProductRequestState.value = UiState.Error(it.message.toString()) }
        }
    }

    fun getDetailBankProducts(recommendationId: String) {
        _getDetailBankProductsState.value = UiState.Loading

        viewModelScope.launch{
            getDetailBankProductsUseCase.invoke(recommendationId)
                .onSuccess { _getDetailBankProductsState.value = UiState.Success(it) }
                .onFailure { _getDetailBankProductsState.value = UiState.Error(it.message.toString()) }
        }
    }
}