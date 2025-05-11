package com.capstone.presentation.view.myPage

import android.app.AlertDialog
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import com.capstone.presentation.base.BaseFragment
import com.capstone.presentation.databinding.FragmentMyPageBinding
import com.capstone.util.LoggerUtil

class MyPageFragment : BaseFragment<FragmentMyPageBinding>() {
    override fun initView() {

        binding.btnEditAsset.setOnClickListener {
            val editAsset = EditText(requireContext()).apply {
                inputType = InputType.TYPE_CLASS_NUMBER
                hint = "새 자산 금액 입력"
            }

            AlertDialog.Builder(requireContext())
                .setTitle("자산 수정")
                .setView(editAsset)
                .setPositiveButton("확인") { _, _ ->
                    val newAsset = editAsset.text.toString()
                    if (newAsset.isNotBlank()) {
                        val assetValue = newAsset.toIntOrNull()
                        if (assetValue != null) {
                            LoggerUtil.d("MyPage", "수정된 자산: $assetValue")

                            // 숫자 포맷팅: 1000 -> 1,000 만원
                            val formatted = String.format("%,d 만원", assetValue)
                            binding.tvCurrentAsset.text = formatted
                        } else {
                            Toast.makeText(requireContext(), "유효한 숫자를 입력해주세요", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .setNegativeButton("취소", null)
                .show()
        }

        binding.btnEditIncome.setOnClickListener {
            val editIncome = EditText(requireContext()).apply {
                inputType = InputType.TYPE_CLASS_NUMBER // 숫자만 입력되도록 설정
                hint = "새 월 소득 금액 입력"
            }

            AlertDialog.Builder(requireContext())
                .setTitle("월 소득 수정")
                .setView(editIncome)
                .setPositiveButton("확인") { _, _ ->
                    val newIncome = editIncome.text.toString()
                    if (newIncome.isNotBlank()) {
                        val incomeValue = newIncome.toIntOrNull()
                        if (incomeValue != null) {
                            LoggerUtil.d("MyPage", "수정된 월 소득: $incomeValue")

                            // 숫자 포맷팅: 1000 -> 1,000
                            val formattedIncome = String.format("%,d 원", incomeValue)
                            binding.tvMonthIncome.text = formattedIncome
                        } else {
                            Toast.makeText(requireContext(), "유효한 숫자를 입력해주세요", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .setNegativeButton("취소", null)
                .show()
        }


    }
}