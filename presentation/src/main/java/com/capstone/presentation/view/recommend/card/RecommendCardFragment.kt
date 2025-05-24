package com.capstone.presentation.view.recommend.card

import androidx.lifecycle.lifecycleScope
import com.capstone.navigation.NavigationCommand
import com.capstone.navigation.NavigationRoutes
import com.capstone.presentation.base.BaseFragment
import com.capstone.presentation.databinding.FragmentRecommendCardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.handleCoroutineException
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecommendCardFragment : BaseFragment<FragmentRecommendCardBinding>() {
    override fun initView() {

        binding.btnBackToMenu.setOnClickListener {
            lifecycleScope.launch {
                navigationManager.navigate(NavigationCommand.Back)
            }
        }

        binding.btnToUpload.setOnClickListener {
            val route = NavigationRoutes.RecommendCardResult
            moveToNext(route)
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