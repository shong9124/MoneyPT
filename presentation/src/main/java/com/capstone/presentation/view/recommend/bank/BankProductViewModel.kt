package com.capstone.presentation.view.recommend.bank

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.domain.model.recommend.bank.GetBankData
import com.capstone.domain.model.recommend.bank.PostRecommendation
import com.capstone.domain.model.recommend.bank.RecommendationContent
import com.capstone.domain.model.recommend.bank.ResponseRecommendation
import com.capstone.domain.usecase.recommend.bank.GetBankProductsUseCase
import com.capstone.domain.usecase.recommend.bank.GetDetailBankProductsUseCase
import com.capstone.domain.usecase.recommend.bank.SendBankProductRequestUseCase
import com.capstone.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BankProductViewModel @Inject constructor(
    private val getBankProductsUseCase: GetBankProductsUseCase,
    private val sendBankProductRequestUseCase: SendBankProductRequestUseCase,
    private val getDetailBankProductsUseCase: GetDetailBankProductsUseCase
) : ViewModel() {

    private val _getBankProductsState = MutableLiveData<UiState<GetBankData>>(UiState.Loading)
    private val _sendBankProductRequestState = MutableLiveData<UiState<ResponseRecommendation>>(UiState.Loading)
    private val _getDetailBankProductsState = MutableLiveData<UiState<ResponseRecommendation>>(UiState.Loading)
    private val _recommendationContent = MutableLiveData<ResponseRecommendation>()
    private val _allDetailBankProductsState = MutableLiveData<List<ResponseRecommendation>>() // ✅ 전체 상세 응답 저장용

    val getBankProductsState: LiveData<UiState<GetBankData>> get() = _getBankProductsState
    val sendBankProductRequestState: LiveData<UiState<ResponseRecommendation>> get() = _sendBankProductRequestState
    val getDetailBankProductsState: LiveData<UiState<ResponseRecommendation>> get() = _getDetailBankProductsState
    val recommendationContent: LiveData<ResponseRecommendation> get() = _recommendationContent
    val allDetailBankProductsState: LiveData<List<ResponseRecommendation>> get() = _allDetailBankProductsState

    fun getBankProducts(page: Int, size: Int) {
        _getBankProductsState.value = UiState.Loading

        viewModelScope.launch {
            getBankProductsUseCase.invoke(page, size)
                .onSuccess { data ->
                    _getBankProductsState.value = UiState.Success(data)

                    // ✅ 받아온 리스트의 모든 id로 상세 요청 (비동기 병렬 처리)
                    val detailedList = data.content.map { item ->
                        async {
                            getDetailBankProductsUseCase.invoke(item.id)
                        }
                    }.mapNotNull { it.await().getOrNull() }

                    // ✅ 전체 상세 데이터를 LiveData에 저장
                    _allDetailBankProductsState.value = detailedList

                    // ✅ 필요하면 첫 번째 상세 데이터도 별도로 저장
                    if (detailedList.isNotEmpty()) {
                        _getDetailBankProductsState.value = UiState.Success(detailedList[0])
                    }
                }
                .onFailure {
                    _getBankProductsState.value = UiState.Error(it.message.toString())
                }
        }
    }

    fun sendBankProductRequest(postRecommendation: PostRecommendation) {
        _sendBankProductRequestState.value = UiState.Loading

        viewModelScope.launch {
            sendBankProductRequestUseCase.invoke(postRecommendation)
                .onSuccess {
                    _recommendationContent.value = it
                    _sendBankProductRequestState.value = UiState.Success(it)
                }
                .onFailure {
                    _sendBankProductRequestState.value = UiState.Error(it.message.toString())
                }
        }
    }

    fun getDetailBankProducts(recommendationId: String) {
        _getDetailBankProductsState.value = UiState.Loading

        viewModelScope.launch {
            getDetailBankProductsUseCase.invoke(recommendationId)
                .onSuccess {
                    _getDetailBankProductsState.value = UiState.Success(it)
                }
                .onFailure {
                    _getDetailBankProductsState.value = UiState.Error(it.message.toString())
                }
        }
    }
}
