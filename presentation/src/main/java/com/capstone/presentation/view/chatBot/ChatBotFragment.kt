package com.capstone.presentation.view.chatBot

import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager // 수정 부분
import com.capstone.data.util.MySharedPreferences
import com.capstone.domain.model.chat.PostMessage
import com.capstone.navigation.NavigationCommand
import com.capstone.navigation.NavigationRoutes
import com.capstone.presentation.R
import com.capstone.presentation.base.BaseFragment
import com.capstone.presentation.databinding.FragmentChatBotBinding
import com.capstone.presentation.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ChatBotFragment : BaseFragment<FragmentChatBotBinding>() {

    @Inject
    lateinit var sharedPreferences: MySharedPreferences

    private var timeJob: Job? = null
    private val viewModel : ChatBotViewModel by viewModels()

    private lateinit var chatAdapter: ChatAdapter
    private val messages = mutableListOf<ChatMessage>()

    override fun initView() {

        setBottomNav()

        viewModel.getChatList(10)

        chatAdapter = ChatAdapter(messages)
        binding.rvChat.adapter = chatAdapter

        // 텍스트 입력 감지하여 버튼 활성/비활성
        binding.etChatInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.btnSend.isEnabled = !s.isNullOrBlank()
                binding.btnSend.setBackgroundResource(R.drawable.ic_send_able)

                if (binding.etChatInput.text.toString() == "") {
                    binding.btnSend.setBackgroundResource(R.drawable.ic_send_disable)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // 전송 버튼 클릭 시 메시지 추가
        binding.btnSend.setOnClickListener {
            val userMessage = binding.etChatInput.text.toString()
            if (userMessage.isNotBlank()) {
                // 사용자 메시지를 UI에 먼저 추가
                messages.add(ChatMessage(userMessage, isUser = true))
                chatAdapter.notifyItemInserted(messages.size - 1)
                binding.rvChat.scrollToPosition(messages.size - 1)

                // ViewModel을 통해 서버에 메시지 전송
                viewModel.sendChat(
                    embedding = true,
                    postMessage = PostMessage(
                        requestMessage = userMessage,
                        summary = "" // 서버 응답에 포함됨
                    )
                )

                binding.etChatInput.text.clear()
            }
        }
    }

    private fun addInitialBotMessages() {
        val welcomeMessage = """
        안녕하세요! 금융 상품 추천 챗봇입니다 😊
        월 소득, 현재 자산 등 정보를 주시면 맞춤 상품을 추천해드릴게요!
        아래 입력창에 원하는 정보를 자유롭게 입력해보세요.
    """.trimIndent()

        messages.add(ChatMessage(welcomeMessage, isUser = false))
        chatAdapter.notifyItemInserted(messages.size - 1)
        binding.rvChat.scrollToPosition(messages.size - 1)
    }

    override fun setObserver() {
        super.setObserver()

        // 채팅 목록 가져오기
        viewModel.getChatListState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    // 필요 시 로딩 UI 처리
                }
                is UiState.Success -> {
                    // 🔧 모든 메시지 초기화 (로딩 메시지 제거 목적)
                    messages.clear()
                    chatAdapter.notifyDataSetChanged()

                    val chatList = state.data
                    if (chatList.isNullOrEmpty()) {
                        addInitialBotMessages()
                    } else {
                        // ⚠️ 여기서 chatList를 역순으로 처리해서 오래된 메시지가 먼저 오도록
                        chatList.asReversed().forEach { chat ->
                            messages.add(ChatMessage(chat.requestMessage, isUser = true))
                            messages.add(ChatMessage(chat.responseMessage, isUser = false))
                        }
                        chatAdapter.notifyDataSetChanged()
                        // 최신 메시지가 맨 아래이므로 마지막 위치로 스크롤
                        binding.rvChat.scrollToPosition(messages.size - 1)
                    }
                }
                is UiState.Error -> {
                    showToast("채팅 불러오기 실패: ${state.message}")
                    addInitialBotMessages()
                }
            }
        }

        viewModel.sendChatState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    // 중복된 로딩 메시지 추가 방지
                    val hasLoading = messages.any { it.isLoading }
                    if (!hasLoading) {
                        messages.add(ChatMessage(message = "", isUser = false, isLoading = true))
                        chatAdapter.notifyItemInserted(messages.size - 1)
                        binding.rvChat.scrollToPosition(messages.size - 1)
                    }
                }

                is UiState.Success -> {
                    // 마지막 메시지가 로딩이면 제거
                    if (messages.isNotEmpty() && messages.last().isLoading) {
                        val lastIndex = messages.size - 1
                        messages.removeAt(lastIndex)
                        chatAdapter.notifyItemRemoved(lastIndex)
                    }

                    // 봇 응답 메시지 추가
                    val botResponse = state.data.responseMessage
                    messages.add(ChatMessage(botResponse, isUser = false))
                    chatAdapter.notifyItemInserted(messages.size - 1)
                    binding.rvChat.scrollToPosition(messages.size - 1)
                }

                is UiState.Error -> {
                    // 마지막 메시지가 로딩이면 제거
                    if (messages.isNotEmpty() && messages.last().isLoading) {
                        val lastIndex = messages.size - 1
                        messages.removeAt(lastIndex)
                        chatAdapter.notifyItemRemoved(lastIndex)
                    }

                    showToast("챗봇 응답 실패: ${state.message}")
                }
            }
        }
    }


    private fun setBottomNav(){
        binding.bottomNav.ivChat.setImageResource(R.drawable.ic_chat_able)
        binding.bottomNav.tvChat.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary))

        binding.bottomNav.menuRecommend.setOnClickListener {
            timeJob?.cancel()
            lifecycleScope.launch {
                navigationManager.navigate(
                    NavigationCommand.ToRoute(NavigationRoutes.RecommendFinancailItem)
                )
            }
        }

        binding.bottomNav.menuMyPage.setOnClickListener {
            timeJob?.cancel()
            lifecycleScope.launch {
                navigationManager.navigate(
                    NavigationCommand.ToRoute(NavigationRoutes.MyPage)
                )
            }
        }
    }
}
