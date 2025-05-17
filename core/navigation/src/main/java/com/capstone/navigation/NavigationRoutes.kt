package com.capstone.navigation

sealed class NavigationRoutes(val route: String) {
    data object SignIn : NavigationRoutes("sign_in")
    data object SignUp : NavigationRoutes("sign_up")
    data object SignUpComplete : NavigationRoutes("sign_up_complete")
    data object Question : NavigationRoutes("Question")
    data object RecommendFinancailItem : NavigationRoutes("recommend_financial_item")
    data object ChatBot : NavigationRoutes("chat_bot")
    data object MyPage : NavigationRoutes("my_page")
}