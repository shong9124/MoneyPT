package com.capstone.presentation.view.chatBot

import android.text.Editable
import android.text.TextWatcher
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.capstone.navigation.NavigationCommand
import com.capstone.navigation.NavigationRoutes
import com.capstone.presentation.R
import com.capstone.presentation.base.BaseFragment
import com.capstone.presentation.databinding.FragmentChatBotBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChatBotFragment : BaseFragment<FragmentChatBotBinding>() {

    private var timeJob: Job? = null

    private lateinit var chatAdapter: ChatAdapter
    private val messages = mutableListOf<ChatMessage>()

    override fun initView() {

        setBottomNav()

        chatAdapter = ChatAdapter(messages)
        binding.rvChat.adapter = chatAdapter

        // ✅ 텍스트 입력 감지하여 버튼 활성/비활성
        binding.etChatInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                binding.btnSend.isEnabled = !s.isNullOrBlank()
                binding.btnSend.setBackgroundResource(R.drawable.ic_send_able)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // ✅ 전송 버튼 클릭 시 메시지 추가
        binding.btnSend.setOnClickListener {
            val userMessage = binding.etChatInput.text.toString()
            messages.add(ChatMessage(userMessage, isUser = true))

            // 예시 챗봇 응답
            messages.add(ChatMessage("추천드릴게요!", isUser = false))

            chatAdapter.notifyItemRangeInserted(messages.size - 2, 2)
            binding.rvChat.scrollToPosition(messages.size - 1)

            binding.etChatInput.text.clear()
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