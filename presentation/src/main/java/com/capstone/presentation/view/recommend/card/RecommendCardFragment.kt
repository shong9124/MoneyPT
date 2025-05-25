package com.capstone.presentation.view.recommend.card

import android.app.ProgressDialog
import android.graphics.Color
import android.text.InputType
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.toDrawable
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
import java.io.File

@AndroidEntryPoint
class RecommendCardFragment : BaseFragment<FragmentRecommendCardBinding>() {

    private val viewModel: CardRecommendationViewModel by activityViewModels()
    private lateinit var customProgressDialog: ProgressDialog

    // 엑셀 파일 선택 SAF 런처
    private val excelPickerLauncher =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
            uri?.let {
                val fileName = "uploaded_excel.xlsx"
                val inputStream = requireContext().contentResolver.openInputStream(it)
                val file = File(requireContext().cacheDir, fileName)

                inputStream?.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }

                showPasswordInputDialog(file) // ← 여기서 다이얼로그 호출
            }
        }

    override fun initView() {

        // ProgressDialog 초기화
        customProgressDialog = ProgressDialog(requireContext())
        customProgressDialog.setCancelable(false)
        customProgressDialog.window?.setBackgroundDrawable(
            Color.TRANSPARENT.toDrawable()
        )
        customProgressDialog.setMessage("로딩 중입니다. 잠시만 기다려주세요...")

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
                    customProgressDialog.dismiss()
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

    private fun showPasswordInputDialog(file: File) {
        val editText = EditText(requireContext()).apply {
            hint = "비밀번호를 입력하세요"
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            setPadding(32, 24, 32, 24)
        }

        AlertDialog.Builder(requireContext())
            .setTitle("비밀번호 입력")
            .setView(editText)
            .setPositiveButton("확인") { _, _ ->
                val password = editText.text.toString()
                if (password.isNotBlank()) {
                    viewModel.uploadEncryptedExcel(file, password)
                    customProgressDialog.show() // 로딩창 표시
                } else {
                    showToast("비밀번호를 입력해주세요.")
                }
            }
            .setNegativeButton("취소", null)
            .show()
    }


    private fun moveToNext(route: NavigationRoutes) {
        lifecycleScope.launch {
            navigationManager.navigate(
                NavigationCommand.ToRoute(route)
            )
        }
    }
}