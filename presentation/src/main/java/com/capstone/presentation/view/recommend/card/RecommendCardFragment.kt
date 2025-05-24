package com.capstone.presentation.view.recommend.card

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.capstone.domain.model.recommend.card.PostPaymentInfo
import com.capstone.navigation.NavigationCommand
import com.capstone.navigation.NavigationRoutes
import com.capstone.presentation.base.BaseFragment
import com.capstone.presentation.databinding.FragmentRecommendCardBinding
import com.capstone.presentation.util.UiState
import com.capstone.util.ExcelUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecommendCardFragment : BaseFragment<FragmentRecommendCardBinding>() {

    private val viewModel: CardRecommendationViewModel by activityViewModels()

    // SAF를 위한 Activity Result Launcher
    private val excelPickerLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        uri?.let {
            requireContext().contentResolver.openInputStream(it)?.use { inputStream ->
                val excelString = ExcelUtils.readExcelAsString(inputStream)
                val postInfo = PostPaymentInfo(file = excelString)
                viewModel.sendPaymentRequest(postInfo)
            }
        }
    }

    override fun initView() {

        binding.btnBackToMenu.setOnClickListener {
            lifecycleScope.launch {
                navigationManager.navigate(NavigationCommand.Back)
            }
        }

        binding.btnToUpload.setOnClickListener {
            // 엑셀 파일 선택 UI 띄우기
            excelPickerLauncher.launch(
                arrayOf(
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xlsx
                    "application/vnd.ms-excel"                                           // .xls
                )
            )

        }
    }

    override fun setObserver() {
        super.setObserver()

        viewModel.sendPaymentRequestState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    // 업로드 성공 시 다음 화면으로 이동
                    moveToNext(NavigationRoutes.RecommendCardResult)
                }
                is UiState.Error -> {
                    showToast("업로드 실패: ${state.message}")
                }
                is UiState.Loading -> {
                    // 필요 시 로딩 처리
                }
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