package com.capstone.presentation.view.chatBot

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

    override fun initView() {

        setBottomNav()

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