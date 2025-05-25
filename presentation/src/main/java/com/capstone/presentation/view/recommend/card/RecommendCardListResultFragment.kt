package com.capstone.presentation.view.recommend.card

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.navigation.NavigationCommand
import com.capstone.navigation.NavigationRoutes
import com.capstone.presentation.base.BaseFragment
import com.capstone.presentation.databinding.FragmentRecommendCardListResultBinding
import com.capstone.presentation.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecommendCardListResultFragment :
    BaseFragment<FragmentRecommendCardListResultBinding>() {

    private val viewModel: CardRecommendationViewModel by activityViewModels()

    override fun initView() {

        binding.btnItemResultComplete.setOnClickListener {
            viewModel.clearData()

            moveToNext(NavigationRoutes.RecommendFinancailItem)
        }
    }

    override fun setObserver() {
        super.setObserver()

        viewModel.sendPaymentRequestState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    val recommendations = state.data.content.recommendations
                    val adapter = CardRecommendationAdapter(recommendations)
                    binding.rvRecommendCardResult.adapter = adapter
                }
                is UiState.Error -> {
                    showToast("추천 결과를 불러오지 못했습니다: ${state.message}")
                }
                is UiState.Loading -> {
                    // 필요 시 로딩 인디케이터 추가
                }
            }
        }

        viewModel.recommendationContent.observe(viewLifecycleOwner) { content ->
            content?.let {
                val adapter = CardRecommendationAdapter(it.content.recommendations)
                binding.rvRecommendCardResult.adapter = adapter
                binding.rvRecommendCardResult.layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun moveToNext(route: NavigationRoutes) {
        lifecycleScope.launch {
            navigationManager.navigate(NavigationCommand.ToRoute(route))
        }
    }
}
