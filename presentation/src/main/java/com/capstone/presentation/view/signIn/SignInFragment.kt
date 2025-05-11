package com.capstone.presentation.view.signIn

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.capstone.navigation.NavigationCommand
import com.capstone.navigation.NavigationRoutes
import com.capstone.presentation.base.BaseFragment
import com.capstone.presentation.databinding.FragmentSignInBinding
import com.capstone.presentation.util.UiState
import com.capstone.util.LoggerUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    override fun initView() {

        binding.btnLogin.setOnClickListener {
            viewModel.login(binding.etId.text.toString(), binding.etPw.text.toString())
        }

        binding.tvAddAccount.setOnClickListener {
            LoggerUtil.d("회원가입 버튼이 눌렸습니다")
            val route = NavigationRoutes.SignUp
            moveToNext(route)
        }
    }

    override fun setObserver() {
        super.setObserver()

        viewModel.loginState.observe(viewLifecycleOwner) {
            when(it) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    // 넘어갈 화면 입력
//                    moveToNext()
                }
                is UiState.Error -> {
                    showToast("로그인에 실패했습니다.")
                }
            }
        }
    }

    private fun moveToNext(route: NavigationRoutes){
        lifecycleScope.launch {
            navigationManager.navigate(
                NavigationCommand.ToRouteAndClear(route)
            )
        }
    }
}