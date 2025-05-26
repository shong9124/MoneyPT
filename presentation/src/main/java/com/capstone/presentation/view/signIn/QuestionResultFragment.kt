package com.capstone.presentation.view.signIn

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.capstone.navigation.NavigationCommand
import com.capstone.navigation.NavigationRoutes
import com.capstone.presentation.base.BaseFragment
import com.capstone.presentation.databinding.FragmentQuestionResultBinding
import com.capstone.presentation.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuestionResultFragment : BaseFragment<FragmentQuestionResultBinding>() {

    private val viewModel: PropensityViewModel by activityViewModels()

    override fun initView() {
        binding.btnToCheckComplete.setOnClickListener {
            viewModel.clearData()

            val route = NavigationRoutes.RecommendFinancailItem
            moveToNext(route)
        }
    }

    override fun setObserver() {
        super.setObserver()

        viewModel.propensityState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Success -> {
                    val propensityAnalysis = it.data.propensityAnalysis
                    binding.tvUserPropensity.text = propensityAnalysis.type
                    binding.tvPropensityDescription.text = propensityAnalysis.description
                    binding.tvProsAndCons.text = propensityAnalysis.prosAndCons
                    binding.tvPrecaution.text = propensityAnalysis.precaution
                }
                is UiState.Error -> showToast("결과를 불러오지 못했습니다.")
                else -> {}
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