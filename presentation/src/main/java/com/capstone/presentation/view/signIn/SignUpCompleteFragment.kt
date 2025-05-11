package com.capstone.presentation.view.signIn

import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import com.capstone.domain.model.RegisterInfo
import com.capstone.presentation.R
import com.capstone.presentation.base.BaseFragment
import com.capstone.presentation.databinding.FragmentSignUpCompleteBinding
import com.capstone.presentation.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpCompleteFragment : BaseFragment<FragmentSignUpCompleteBinding>() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var gender: String

    val listener by lazy {
        CompoundButton.OnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                when(compoundButton.id) {
                    R.id.cb_male -> gender = "남"
                    R.id.cb_female -> gender = "여"
                }
            }
            else {
                when(compoundButton.id) {
                    R.id.cb_male -> gender = ""
                    R.id.cb_female -> gender = ""
                }
            }
        }
    }

    override fun initView() {
        val asset = binding.etEnterAsset.text.toString().toInt()
        val birthDate = binding.etEnterBirth.text.toString()
        val name = binding.etEnterName.text.toString()
        val salary = binding.etEnterMonthIncome.text.toString().toInt()

        binding.cbMale.setOnCheckedChangeListener(listener)
        binding.cbFemale.setOnCheckedChangeListener(listener)

        val info = RegisterInfo(asset, birthDate, gender, name, salary)

        binding.btnToSignComplete.setOnClickListener {
            viewModel.signUpComplete(info)
        }
    }

    override fun setObserver() {
        super.setObserver()

        viewModel.signUpState.observe(viewLifecycleOwner) {
            when(it) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    // 넘어갈 화면 입력
//                    moveToNext()
                }
                is UiState.Error -> {
                    showToast("회원가입 완료에 실패했습니다.")
                }
            }
        }
    }
}