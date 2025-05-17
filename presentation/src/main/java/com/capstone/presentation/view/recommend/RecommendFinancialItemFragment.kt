package com.capstone.presentation.view.recommend

import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.capstone.navigation.NavigationCommand
import com.capstone.navigation.NavigationRoutes
import com.capstone.presentation.R
import com.capstone.presentation.base.BaseFragment
import com.capstone.presentation.databinding.FragmentRecommendFinancialItemBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecommendFinancialItemFragment : BaseFragment<FragmentRecommendFinancialItemBinding>() {

    private var timeJob: Job? = null

    override fun initView() {

        setBottomNav()

    }

    private fun setBottomNav(){
        binding.bottomNav.ivRecommend.setImageResource(R.drawable.ic_list_able)
        binding.bottomNav.tvRecommend.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary))

        binding.bottomNav.menuChat.setOnClickListener {
            timeJob?.cancel()
            lifecycleScope.launch {
                navigationManager.navigate(
                    NavigationCommand.ToRoute(NavigationRoutes.ChatBot)
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

    private fun moveToNext(route: NavigationRoutes) {
        lifecycleScope.launch {
            navigationManager.navigate(
                NavigationCommand.ToRoute(route)
            )
        }
    }
}