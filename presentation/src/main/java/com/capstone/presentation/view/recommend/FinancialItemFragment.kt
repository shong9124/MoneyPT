package com.capstone.presentation.view.recommend

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.capstone.domain.model.recommend.PostRecommendation
import com.capstone.navigation.NavigationCommand
import com.capstone.navigation.NavigationRoutes
import com.capstone.presentation.R
import com.capstone.presentation.base.BaseFragment
import com.capstone.presentation.databinding.FragmentFinancialItemBinding
import com.capstone.presentation.util.UiState
import com.capstone.util.LoggerUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FinancialItemFragment : BaseFragment<FragmentFinancialItemBinding>() {

    // viewModel의 결과를 공유하기 위함
    private val viewModel: BankProductViewModel by activityViewModels()

    override fun initView() {
        val spinner = binding.spinnerSelectPropensity
        val items = listOf("보수형", "소비형", "투자형", "융합형", "균형형")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerSelectPropensity.adapter = adapter
        binding.spinnerSelectPropensity.setSelection(0)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selected = parent.getItemAtPosition(position).toString()
                LoggerUtil.d("Selected: $selected")
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


        binding.btnBackToMenu.setOnClickListener {
            lifecycleScope.launch {
                navigationManager.navigate(NavigationCommand.Back)
            }
        }

        binding.btnToGetBankProduct.setOnClickListener {
            val selectedText = binding.spinnerSelectPropensity.selectedItem as String
            val mappedPropensity = mapSelectedPropensity(selectedText)

            viewModel.sendBankProductRequest(
                PostRecommendation(
                    amount = binding.etEnterAmount.text.toString().toLong() * 10000,
                    propensity = mappedPropensity,
                    term = binding.etEnterTerm.text.toString().toInt()
                )
            )
        }
    }

    override fun setObserver() {
        super.setObserver()

        viewModel.sendBankProductRequestState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {
                    showToast("로딩중입니다.\n잠시 기다려주세요.")
                }
                is UiState.Success -> {
                    val route = NavigationRoutes.FinancialItemResult
                    moveToNext(route)
                }
                is UiState.Error -> {
                    showToast("금융 상품 추천에 실패했습니다.")
                }
            }
        }
    }

    private fun mapSelectedPropensity(selected: String): String {
        return when (selected) {
            "균형형" -> "BALANCED"
            "투자형" -> "INVESTOR"
            "보수형" -> "CONSERVATIVE"
            "소비형" -> "CONSUMER"
            "융합형" -> "FLEXIBLE"
            else -> "UNKNOWN"
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