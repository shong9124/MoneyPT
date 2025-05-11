package com.capstone.navigation

sealed class NavigationRoutes(val route: String) {
    data object SignIn : NavigationRoutes("sign_in")
    data object SignUp : NavigationRoutes("sign_up")
    data object SignUpComplete : NavigationRoutes("sign_up_complete")
}