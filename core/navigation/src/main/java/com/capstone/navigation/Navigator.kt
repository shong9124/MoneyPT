package com.capstone.navigation

import com.capstone.navigation.NavigationCommand

interface Navigator {
    fun navigate(route: NavigationCommand)
    fun navigateBack()
    fun popUpTo(route: String, inclusive: Boolean)
}