package com.capstone.presentation.view.signIn

import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import com.capstone.domain.model.RegisterInfo
import com.capstone.presentation.R
import com.capstone.presentation.base.BaseFragment
import com.capstone.presentation.databinding.FragmentSignUpCompleteBinding
import com.capstone.presentation.util.UiState
import com.capstone.util.LoggerUtil
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@AndroidEntryPoint
class SignUpCompleteFragment : BaseFragment<FragmentSignUpCompleteBinding>() {

    private val viewModel: LoginViewModel by viewModels()
    private var gender: String? = null

    private val listener by lazy {
        CompoundButton.OnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                when (compoundButton.id) {
                    R.id.cb_male -> {
                        gender = "MALE"
                        binding.cbFemale.isChecked = false
                    }
                    R.id.cb_female -> {
                        gender = "FEMALE"
                        binding.cbMale.isChecked = false
                    }
                }
            }
        }
    }

    override fun initView() {
        binding.cbMale.setOnCheckedChangeListener(listener)
        binding.cbFemale.setOnCheckedChangeListener(listener)

        binding.btnToSignComplete.setOnClickListener {
            val assetText = binding.etEnterAsset.text.toString()
            val name = binding.etEnterName.text.toString()
            val salaryText = binding.etEnterMonthIncome.text.toString()

            val rawBirthDate = binding.etEnterBirth.text.toString()

            if (rawBirthDate.isBlank()) {
                showToast("생년월일을 입력해주세요.")
                return@setOnClickListener
            }

            if (rawBirthDate.length != 8 || !rawBirthDate.all { it.isDigit() }) {
                showToast("생년월일은 8자리 숫자로 입력해주세요. (예: 19990101)")
                return@setOnClickListener
            }

            val formattedDate = "${rawBirthDate.substring(0, 4)}-${rawBirthDate.substring(4, 6)}-${rawBirthDate.substring(6, 8)}"
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            try {
                LocalDate.parse(formattedDate, dateFormatter)  // 유효성 검사
            } catch (e: DateTimeParseException) {
                showToast("유효하지 않은 생년월일입니다.")
                return@setOnClickListener
            }

            val birthDate = formattedDate  // 여기까지 왔으면 유효한 날짜

            when {
                name.isBlank() -> {
                    showToast("이름을 입력해주세요.")
                    return@setOnClickListener
                }
                birthDate.isBlank() -> {
                    showToast("생년월일을 입력해주세요.")
                    return@setOnClickListener
                }
                gender == null -> {
                    showToast("성별을 선택해주세요.")
                    return@setOnClickListener
                }
                assetText.isBlank() -> {
                    showToast("자산을 입력해주세요.")
                    return@setOnClickListener
                }
                salaryText.isBlank() -> {
                    showToast("월급을 입력해주세요.")
                    return@setOnClickListener
                }
            }

            val asset = assetText.toInt()
            val salary = salaryText.toInt()
            val info = RegisterInfo(asset, birthDate, gender!!, name, salary)

            viewModel.signUpComplete(info)
        }
    }

    override fun setObserver() {
        super.setObserver()

        viewModel.signUpCompleteState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    LoggerUtil.d("회원가입 완료 성공")
                    // moveToNext()
                }
                is UiState.Error -> {
                    showToast("회원가입 완료에 실패했습니다.")
                }
            }
        }
    }
}
