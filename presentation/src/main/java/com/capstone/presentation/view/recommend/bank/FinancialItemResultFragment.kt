package com.capstone.presentation.view.recommend.bank

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.navigation.NavigationCommand
import com.capstone.navigation.NavigationRoutes
import com.capstone.presentation.base.BaseFragment
import com.capstone.presentation.databinding.FragmentFinancialItemResultBinding
import com.capstone.presentation.util.UiState
import com.capstone.util.LoggerUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FinancialItemResultFragment : BaseFragment<FragmentFinancialItemResultBinding>() {

    // viewModel의 결과를 공유하기 위함
    private val viewModel: BankProductViewModel by activityViewModels()

    override fun initView() {

        binding.btnItemResultComplete.setOnClickListener {
            viewModel.clearData()

            val route = NavigationRoutes.RecommendFinancailItem
            moveToNext(route)
        }
    }

    override fun setObserver() {
        super.setObserver()

        viewModel.sendBankProductRequestState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    val recommendations = it.data.content
                    val adapter = RecommendationAdapter(recommendations.recommendations)
                    LoggerUtil.d("recommendations size: ${recommendations.recommendations.size}")
                    binding.rvRecommendResult.adapter = adapter
                    binding.rvRecommendResult.layoutManager = LinearLayoutManager(requireContext())
                }
                is UiState.Error -> showToast("결과를 불러오지 못했습니다.")
                else -> {}
            }
        }

        viewModel.recommendationContent.observe(viewLifecycleOwner) { content ->
            content?.let {
                val adapter = RecommendationAdapter(it.content.recommendations)
                binding.rvRecommendResult.adapter = adapter
                binding.rvRecommendResult.layoutManager = LinearLayoutManager(requireContext())
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