package com.capstone.presentation.view.chatBot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.domain.model.chat.ConversationLogResponses
import com.capstone.domain.model.chat.PostMessage
import com.capstone.domain.model.chat.ResponseMessage
import com.capstone.domain.usecase.chat.GetChatListUseCase
import com.capstone.domain.usecase.chat.SendChatUseCase
import com.capstone.presentation.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatBotViewModel @Inject constructor(
    private val getChatListUseCase: GetChatListUseCase,
    private val sendChatUseCase: SendChatUseCase
): ViewModel() {
    private val _getChatListState = MutableLiveData<UiState<List<ConversationLogResponses>>>(UiState.Loading)
    private val _sendChatState = MutableLiveData<UiState<ResponseMessage>>(UiState.Loading)

    val getChatListState : LiveData<UiState<List<ConversationLogResponses>>> get() = _getChatListState
    val sendChatState : LiveData<UiState<ResponseMessage>> get() = _sendChatState

    fun getChatList(size: Int) {
        _getChatListState.value = UiState.Loading

        viewModelScope.launch{
            getChatListUseCase.invoke(size)
                .onSuccess { _getChatListState.value = UiState.Success(it) }
                .onFailure { _getChatListState.value = UiState.Error(it.message.toString()) }
        }
    }

    fun sendChat(embedding: Boolean, postMessage: PostMessage) {
        _sendChatState.value = UiState.Loading

        viewModelScope.launch {
            sendChatUseCase.invoke(embedding, postMessage)
                .onSuccess { _sendChatState.value = UiState.Success(it) }
                .onFailure { _getChatListState.value = UiState.Error(it.message.toString()) }
        }
    }
}