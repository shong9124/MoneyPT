package com.capstone.presentation.view.myPage

import android.annotation.SuppressLint
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.capstone.domain.model.FinancialType
import com.capstone.navigation.NavigationCommand
import com.capstone.navigation.NavigationRoutes
import com.capstone.presentation.R
import com.capstone.presentation.base.BaseFragment
import com.capstone.presentation.databinding.FragmentMyPageBinding
import com.capstone.presentation.util.UiState
import com.capstone.presentation.view.signIn.PropensityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MyPageFragment : BaseFragment<FragmentMyPageBinding>() {

    private var timeJob: Job? = null
    private val propensityViewModel: PropensityViewModel by viewModels()
    private val userInfoViewModel: UserInfoViewModel by viewModels()
    private var propensityId: String = ""

    override fun initView() {

        setBottomNav()

        userInfoViewModel.getUserInfo()
        propensityViewModel.getPropensityList(0, 10)

    }

    @SuppressLint("SetTextI18n")
    override fun setObserver() {
        super.setObserver()

        propensityViewModel.getPropensityListState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    it.data.content.map { content ->

                        val label = when (content.propensity) {
                            "BALANCED" -> "균형형"
                            "INVESTMENT" -> "공격형"
                            "CONSERVATIVE" -> "저축형"
                            "CONSUMPTIVE" -> "소비형"
                            "FUSION" -> "융합형"
                            else -> "금융 성향 분석 결과가 없습니다."
                        }

                        propensityId = content.id
                        binding.tvFinancialMbti.text = label
                    }
                }

                is UiState.Error -> {
                    showToast("회원 정보 불러오기에 실패했습니다.")
                }
            }
        }

        userInfoViewModel.userInfoState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is  UiState.Success -> {
                    binding.tvUserName.text = it.data.nickname
                    binding.tvMonthIncome.text = it.data.salary.toString() + " 만원"
                    binding.tvCurrentAsset.text = it.data.asset.toString() + " 만원"
                }

                is  UiState.Error -> {
                    showToast("회원 정보 불러오기에 실패했습니다.")
                }
            }
        }
    }

    private fun setBottomNav() {
        binding.bottomNav.ivMyPage.setImageResource(R.drawable.ic_my_page_able)
        binding.bottomNav.tvMyPage.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.primary
            )
        )

        binding.bottomNav.menuRecommend.setOnClickListener {
            timeJob?.cancel()
            lifecycleScope.launch {
                navigationManager.navigate(
                    NavigationCommand.ToRoute(NavigationRoutes.RecommendFinancailItem)
                )
            }
        }

        binding.bottomNav.menuChat.setOnClickListener {
            timeJob?.cancel()
            lifecycleScope.launch {
                navigationManager.navigate(
                    NavigationCommand.ToRoute(NavigationRoutes.ChatBot)
                )
            }
        }
    }

}