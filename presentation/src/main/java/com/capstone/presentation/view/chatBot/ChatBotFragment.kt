package com.capstone.presentation.view.chatBot

import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager // 수정 부분
import androidx.recyclerview.widget.RecyclerView
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

        setUpRecyclerView()  // 추가

        // 1. RecyclerView 어댑터와 레이아웃 매니저 초기화 (가장 먼저)
        chatAdapter = ChatAdapter(messages)
        binding.rvChat.adapter = chatAdapter
        binding.rvChat.layoutManager = LinearLayoutManager(requireContext())

        // ViewModel 메시지 리스트 observe
        viewModel.chatMessages.observe(viewLifecycleOwner) { newList ->
            messages.clear()
            messages.addAll(newList)
            chatAdapter.notifyDataSetChanged()
            binding.rvChat.scrollToPosition(messages.size - 1)
        }

        if (viewModel.chatMessages.value.isNullOrEmpty()) {
            viewModel.initChat()  // cursor 초기화하고 chatList 요청
        }

        // 4. EditText 텍스트 변화 감지해서 전송 버튼 활성/비활성 처리
        binding.etChatInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val isNotBlank = !s.isNullOrBlank()
                binding.btnSend.isEnabled = isNotBlank
                binding.btnSend.setBackgroundResource(
                    if (isNotBlank) R.drawable.ic_send_able else R.drawable.ic_send_disable
                )
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // 5. 전송 버튼 클릭 시 메시지 추가 및 서버 전송
        binding.btnSend.setOnClickListener {
            val userMessage = binding.etChatInput.text.toString()
            if (userMessage.isNotBlank()) {
                messages.add(ChatMessage(userMessage, isUser = true))
                chatAdapter.notifyItemInserted(messages.size - 1)
                binding.rvChat.scrollToPosition(messages.size - 1)

                viewModel.sendChat(
                    embedding = true,
                    postMessage = PostMessage(
                        requestMessage = userMessage,
                        summary = ""
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

        viewModel.getChatListState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    val hasLoading = messages.any { it.isLoading }
                    if (!hasLoading) {
                        messages.add(ChatMessage(message = "", isUser = false, isLoading = true))
                        chatAdapter.notifyItemInserted(messages.size - 1)
                        binding.rvChat.scrollToPosition(messages.size - 1)
                    }
                }
                is UiState.Success -> {
                    val loadingIndex = messages.indexOfFirst { it.isLoading }
                    if (loadingIndex != -1) {
                        messages.removeAt(loadingIndex)
                        chatAdapter.notifyItemRemoved(loadingIndex)
                    }

                    // 초기 메시지 보여줄지 판단 (빈 메시지일 때만)
                    if (viewModel.chatMessages.value.isNullOrEmpty()) {
                        addInitialBotMessages()
                    }
                }

                is UiState.Error -> {
                    val loadingIndex = messages.indexOfFirst { it.isLoading }
                    if (loadingIndex != -1) {
                        messages.removeAt(loadingIndex)
                        chatAdapter.notifyItemRemoved(loadingIndex)
                    }
                    showToast("채팅 불러오기 실패: ${state.message}")
                }
            }
        }

        viewModel.sendChatState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    if (messages.none { it.isLoading }) {
                        messages.add(ChatMessage("", isUser = false, isLoading = true))
                        chatAdapter.notifyItemInserted(messages.size - 1)
                        binding.rvChat.scrollToPosition(messages.size - 1)
                    }
                }
                is UiState.Success -> {
                    val loadingIndex = messages.indexOfFirst { it.isLoading }
                    if (loadingIndex != -1) {
                        messages.removeAt(loadingIndex)
                        chatAdapter.notifyItemRemoved(loadingIndex)
                    }
                    messages.add(ChatMessage(state.data.responseMessage, isUser = false))
                    chatAdapter.notifyItemInserted(messages.size - 1)
                    binding.rvChat.scrollToPosition(messages.size - 1)
                }
                is UiState.Error -> {
                    val loadingIndex = messages.indexOfFirst { it.isLoading }
                    if (loadingIndex != -1) {
                        messages.removeAt(loadingIndex)
                        chatAdapter.notifyItemRemoved(loadingIndex)
                    }
                    showToast("챗봇 응답 실패: ${state.message}")
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        chatAdapter = ChatAdapter(messages)
        binding.rvChat.adapter = chatAdapter

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvChat.layoutManager = layoutManager

        // 무한 스크롤 감지
        binding.rvChat.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (firstVisibleItemPosition == 0) {
                    viewModel.loadMoreChats(3)
                }
            }
        })
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
