package com.capstone.presentation.view.chatBot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.data.util.MySharedPreferences
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
    private val sendChatUseCase: SendChatUseCase,
    private val sharedPreferences: MySharedPreferences // 추가
): ViewModel() {

    private val _getChatListState = MutableLiveData<UiState<List<ConversationLogResponses>>>(UiState.Loading)
    private val _sendChatState = MutableLiveData<UiState<ResponseMessage>>(UiState.Loading)
    // ChatMessage는 presentation layer의 데이터이므로 여기에 정의 가능
    private val _chatMessages = MutableLiveData<List<ChatMessage>>(emptyList())

    val getChatListState : LiveData<UiState<List<ConversationLogResponses>>> get() = _getChatListState
    val sendChatState : LiveData<UiState<ResponseMessage>> get() = _sendChatState
    val chatMessages: LiveData<List<ChatMessage>> get() = _chatMessages

    fun getChatList(size: Int) {
        _getChatListState.value = UiState.Loading

        viewModelScope.launch {
            getChatListUseCase.invoke(size)
                .onSuccess { list ->
                    _getChatListState.value = UiState.Success(list)

                    if (list.isNotEmpty()) {
                        val chatMessages = list.asReversed().flatMap {
                            listOf(
                                ChatMessage(it.requestMessage, isUser = true),
                                ChatMessage(it.responseMessage, isUser = false)
                            )
                        }
                        updateMessages(chatMessages)
                    }
                }
                .onFailure {
                    _getChatListState.value = UiState.Error(it.message.toString())
                }
        }
    }

    fun sendChat(embedding: Boolean, postMessage: PostMessage) {
        _sendChatState.value = UiState.Loading

        val current = _chatMessages.value.orEmpty().toMutableList()
        current.add(ChatMessage(postMessage.requestMessage, isUser = true))
        current.add(ChatMessage("", isUser = false, isLoading = true))
        _chatMessages.value = current

        viewModelScope.launch {
            sendChatUseCase.invoke(embedding, postMessage)
                .onSuccess {
                    _sendChatState.value = UiState.Success(it)

                    val updated = _chatMessages.value.orEmpty().toMutableList()
                    val loadingIndex = updated.indexOfFirst { msg -> msg.isLoading }
                    if (loadingIndex != -1) {
                        updated[loadingIndex] = ChatMessage(it.responseMessage, isUser = false)
                        _chatMessages.value = updated
                    }
                }
                .onFailure {
                    _sendChatState.value = UiState.Error(it.message.toString())
                }
        }
    }

    // 메시지 리스트 초기화 또는 갱신
    fun updateMessages(newMessages: List<ChatMessage>) {
        _chatMessages.value = newMessages
    }

    fun appendMessages(newMessages: List<ChatMessage>) {
        val current = _chatMessages.value.orEmpty().toMutableList()
        current.addAll(newMessages)
        _chatMessages.value = current
    }

    fun insertMessagesAtTop(newMessages: List<ChatMessage>) {
        val current = _chatMessages.value.orEmpty().toMutableList()
        current.addAll(0, newMessages)
        _chatMessages.value = current
    }

    private var isLoadingMore = false
    private var endOfChats = false // 마지막 채팅까지 다 불러왔는지 여부

    fun loadMoreChats(size: Int) {
        if (isLoadingMore || endOfChats) return

        isLoadingMore = true
        _getChatListState.value = UiState.Loading

        viewModelScope.launch {
            getChatListUseCase.invoke(size)
                .onSuccess { list ->
                    if (list.isEmpty()) {
                        endOfChats = true
                    } else {
                        val chatMessages = list.asReversed().flatMap {
                            listOf(
                                ChatMessage(it.requestMessage, isUser = true),
                                ChatMessage(it.responseMessage, isUser = false)
                            )
                        }
                        insertMessagesAtTop(chatMessages)
                    }
                    _getChatListState.value = UiState.Success(list)
                    isLoadingMore = false
                }
                .onFailure {
                    _getChatListState.value = UiState.Error(it.message ?: "Error")
                    isLoadingMore = false
                }
        }
    }

    fun initChat() {
        sharedPreferences.setString(MySharedPreferences.CURSOR, "") // Cursor 초기화
        getChatList(10)
    }
}