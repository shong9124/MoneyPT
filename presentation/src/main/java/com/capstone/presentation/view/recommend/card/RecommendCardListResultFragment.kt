package com.capstone.presentation.view.recommend.card

import androidx.lifecycle.lifecycleScope
import com.capstone.navigation.NavigationCommand
import com.capstone.navigation.NavigationRoutes
import com.capstone.presentation.base.BaseFragment
import com.capstone.presentation.databinding.FragmentRecommendCardListResultBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecommendCardListResultFragment : BaseFragment<FragmentRecommendCardListResultBinding>(){
    override fun initView() {

        binding.btnItemResultComplete.setOnClickListener {
            val route = NavigationRoutes.RecommendFinancailItem
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