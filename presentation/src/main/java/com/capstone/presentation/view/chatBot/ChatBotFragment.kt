package com.capstone.presentation.view.chatBot

import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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

        chatAdapter = ChatAdapter(messages)
        binding.rvChat.adapter = chatAdapter

        // ✅ 화면 진입 시 챗봇 초기 메시지
        addInitialBotMessages()

        // ✅ 텍스트 입력 감지하여 버튼 활성/비활성
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

        // ✅ 전송 버튼 클릭 시 메시지 추가
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

        viewModel.sendChatState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    // 로딩 상태 처리 (필요 시)
                }
                is UiState.Success -> {
                    // 서버에서 저장된 summary 불러오기
                    val summary = sharedPreferences.getString(MySharedPreferences.SUMMARY, "")
                    messages.add(ChatMessage(summary, isUser = false))
                    chatAdapter.notifyItemInserted(messages.size - 1)
                    binding.rvChat.scrollToPosition(messages.size - 1)
                }
                is UiState.Error -> {
                    showToast("챗봇 응답 실패: ${state.message}")
                }
            }
        }


        viewModel.sendChatState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    // 🟢 응답 메시지 채팅창에 추가
                    val botResponse = it.data.responseMessage // 실제 필드명에 맞게 수정
                    messages.add(ChatMessage(botResponse, isUser = false))
                    chatAdapter.notifyItemInserted(messages.size - 1)
                    binding.rvChat.scrollToPosition(messages.size - 1)

                    // 입력창 비우기
                    binding.etChatInput.text.clear()
                }
                is UiState.Error -> {
                    showToast("메시지 전송 중 오류 발생: ${it.message}")
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