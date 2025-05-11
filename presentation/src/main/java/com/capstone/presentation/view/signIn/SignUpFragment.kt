package com.capstone.presentation.view.signIn

import androidx.core.widget.addTextChangedListener
import com.capstone.presentation.base.BaseFragment
import com.capstone.presentation.databinding.FragmentSignUpBinding
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.capstone.presentation.R
import com.capstone.presentation.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    override fun initView() {

        binding.etEnterPw.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validatePassword()
            }
        }

        // 텍스트가 바뀔 때마다 실시간 검사도 가능
        binding.etEnterPw.addTextChangedListener {
            validatePassword()
        }

        binding.btnToSignComplete.setOnClickListener {
            viewModel.signUp(binding.etEnterEmail.text.toString(), binding.etEnterPw.text.toString())
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
                    showToast("회원가입에 실패했습니다.")
                }
            }
        }
    }

    private fun validatePassword() {
        val password = binding.etEnterPw.text.toString()
        val pattern = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")

        if (pattern.matches(password)) {
            binding.etEnterPw.setBackgroundResource(R.drawable.shape_edit_text)
            binding.btnToSignComplete.isEnabled = true
        } else {
            binding.etEnterPw.setBackgroundResource(R.drawable.shape_edit_text_error)
            binding.btnToSignComplete.isEnabled = false
            Toast.makeText(requireContext(), "비밀번호는 8자 이상이며 영문자와 숫자를 포함해야 합니다.", Toast.LENGTH_SHORT).show()
        }
    }
}