package com.capstone.presentation.view.recommend.card

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.domain.model.recommend.card.CardRecommendationContent
import com.capstone.domain.model.recommend.card.PostPaymentInfo
import com.capstone.domain.model.recommend.card.ResponseCardRecommendation
import com.capstone.domain.usecase.recommend.card.GetCardRecommendationUseCase
import com.capstone.domain.usecase.recommend.card.GetDetailCardRecommendationUseCase
import com.capstone.domain.usecase.recommend.card.SendPaymentRequestUseCase
import com.capstone.presentation.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardRecommendationViewModel @Inject constructor(
    private val getCardRecommendationUseCase: GetCardRecommendationUseCase,
    private val sendPaymentRequestUseCase: SendPaymentRequestUseCase,
    private val getDetailCardRecommendationUseCase: GetDetailCardRecommendationUseCase
) : ViewModel() {

    private val _getCardRecommendationState =
        MutableLiveData<UiState<List<CardRecommendationContent>>>(UiState.Loading)
    private val _sendPaymentRequestState =
        MutableLiveData<UiState<ResponseCardRecommendation>>(UiState.Loading)
    private val _getDetailCardRecommendationState =
        MutableLiveData<UiState<ResponseCardRecommendation>>(UiState.Loading)
    private val _recommendationContent = MutableLiveData<ResponseCardRecommendation>()

    // 새로 추가: 전체 상세 카드 추천 목록 상태
    private val _allDetailCardRecommendationsState =
        MutableLiveData<List<ResponseCardRecommendation>>(emptyList())
    val allDetailCardRecommendationsState: LiveData<List<ResponseCardRecommendation>> get() = _allDetailCardRecommendationsState

    val getCardRecommendationState: LiveData<UiState<List<CardRecommendationContent>>> get() = _getCardRecommendationState
    val sendPaymentRequestState: LiveData<UiState<ResponseCardRecommendation>> get() = _sendPaymentRequestState
    val getDetailCardRecommendationState: LiveData<UiState<ResponseCardRecommendation>> get() = _getDetailCardRecommendationState
    val recommendationContent: LiveData<ResponseCardRecommendation> get() = _recommendationContent

    fun getCardRecommendations(page: Int, size: Int) {
        _getCardRecommendationState.value = UiState.Loading

        viewModelScope.launch {
            getCardRecommendationUseCase.invoke(page, size)
                .onSuccess { list ->
                    _getCardRecommendationState.value = UiState.Success(list)

                    // ✅ 받아온 리스트의 모든 id로 상세 요청 (비동기 병렬 처리)
                    val detailedList = list.map { item ->
                        async {
                            getDetailCardRecommendationUseCase.invoke(item.id)
                        }
                    }.mapNotNull { it.await().getOrNull() }

                    // ✅ 전체 상세 데이터를 LiveData에 저장
                    _allDetailCardRecommendationsState.value = detailedList

                    // ✅ 필요하면 첫 번째 상세 데이터도 별도로 저장
                    if (detailedList.isNotEmpty()) {
                        _getDetailCardRecommendationState.value = UiState.Success(detailedList[0])
                    }
                }
                .onFailure {
                    _getCardRecommendationState.value = UiState.Error(it.message.toString())
                }
        }
    }

    fun sendPaymentRequest(postPaymentInfo: PostPaymentInfo) {
        _sendPaymentRequestState.value = UiState.Loading

        viewModelScope.launch {
            sendPaymentRequestUseCase.invoke(postPaymentInfo)
                .onSuccess {
                    _recommendationContent.value = it
                    _sendPaymentRequestState.value = UiState.Success(it)
                }
                .onFailure { _sendPaymentRequestState.value = UiState.Error(it.message.toString()) }
        }
    }

    fun getDetailCardRecommendations(recommendationId: String) {
        _getDetailCardRecommendationState.value = UiState.Loading

        viewModelScope.launch {
            getDetailCardRecommendationUseCase.invoke(recommendationId)
                .onSuccess { _getDetailCardRecommendationState.value = UiState.Success(it) }
                .onFailure {
                    _getDetailCardRecommendationState.value = UiState.Error(it.message.toString())
                }
        }
    }
}
