package com.capstone.presentation.view.recommend.itemList

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.navigation.NavigationCommand
import com.capstone.presentation.base.BaseFragment
import com.capstone.presentation.databinding.FragmentItemListBinding
import com.capstone.presentation.util.UiState
import com.capstone.presentation.view.recommend.bank.BankProductViewModel
import com.capstone.presentation.view.recommend.bank.RecommendationBankDetailAdapter
import com.capstone.presentation.view.recommend.card.CardRecommendationViewModel
import com.capstone.presentation.view.recommend.card.RecommendationCardDetailAdapter
import com.capstone.util.LoggerUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ItemListFragment : BaseFragment<FragmentItemListBinding>() {
    private val bankViewModel : BankProductViewModel by viewModels()
    private val cardViewModel : CardRecommendationViewModel by viewModels()

    override fun initView() {

        binding.btnBackToMenu.setOnClickListener {
            lifecycleScope.launch {
                navigationManager.navigate(NavigationCommand.Back)
            }
        }

//        // 은행 & 카드 추천 데이터 요청
        bankViewModel.getBankProducts(0, 3)
        cardViewModel.getCardRecommendations(0, 3)
    }

    override fun setObserver() {
        super.setObserver()

        // ✅ 전체 상세 은행 상품 목록
        bankViewModel.allDetailBankProductsState.observe(viewLifecycleOwner) { detailList ->
            LoggerUtil.d("Bank detail list size: ${detailList.size}")
            val adapter = RecommendationBankDetailAdapter(detailList)
            binding.rvRecommendResult.adapter = adapter
            binding.rvRecommendResult.layoutManager = LinearLayoutManager(requireContext())
        }

        // ✅ 카드 추천 목록
        cardViewModel.getDetailCardRecommendationState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    val detailList = uiState.data.content.recommendations
                    val adapter = RecommendationCardDetailAdapter(detailList)
                    LoggerUtil.d("Card recommendations size: ${detailList.size}")
                    binding.rvRecommendCardResult.adapter = adapter
                    binding.rvRecommendCardResult.layoutManager = LinearLayoutManager(requireContext())
                }
                is UiState.Error -> {
                    showToast("카드 결과를 불러오지 못했습니다.")
                }
                else -> {}
            }
        }
    }
}
