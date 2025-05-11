package com.capstone.presentation.view.signIn

import androidx.core.widget.addTextChangedListener
import com.capstone.presentation.base.BaseFragment
import com.capstone.presentation.databinding.FragmentSignUpBinding
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.capstone.navigation.NavigationCommand
import com.capstone.navigation.NavigationRoutes
import com.capstone.presentation.R
import com.capstone.presentation.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    override fun initView() {

        // 포커스 이동 시 유효성 검사는 제거 (원한다면 유지 가능)
        binding.etEnterPw.setOnFocusChangeListener(null)

        binding.btnToSignComplete.setOnClickListener {
            if (validateInput()) {
                viewModel.signUp(
                    binding.etEnterEmail.text.toString(),
                    binding.etEnterPw.text.toString()
                )
            }
        }
    }

    override fun setObserver() {
        super.setObserver()

        viewModel.signUpState.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    val route = NavigationRoutes.SignUpComplete
                    moveToNext(route)
                }
                is UiState.Error -> {
                    showToast("회원가입에 실패했습니다.")
                }
            }
        }
    }

    private fun validateInput(): Boolean {
        val email = binding.etEnterEmail.text.toString()
        val password = binding.etEnterPw.text.toString()

        val passwordPattern = Regex("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")

        var isValid = true

        if (!email.contains("@")) {
            binding.etEnterEmail.setBackgroundResource(R.drawable.shape_edit_text_error)
            showToast("올바른 이메일 형식이 아닙니다. '@'를 포함해야 합니다.")
            isValid = false
        } else {
            binding.etEnterEmail.setBackgroundResource(R.drawable.shape_edit_text)
        }

        if (!passwordPattern.matches(password)) {
            binding.etEnterPw.setBackgroundResource(R.drawable.shape_edit_text_error)
            showToast("비밀번호는 8자 이상이며 영문자와 숫자를 포함해야 합니다.")
            isValid = false
        } else {
            binding.etEnterPw.setBackgroundResource(R.drawable.shape_edit_text)
        }

        return isValid
    }

    private fun moveToNext(route: NavigationRoutes) {
        lifecycleScope.launch {
            navigationManager.navigate(
                NavigationCommand.ToRoute(route)
            )
        }
    }
}
