package com.capstone.navigation

import com.capstone.navigation.NavigationCommand
import com.capstone.util.LoggerUtil
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class NavigationManager @Inject constructor() {
    private val _commands = MutableSharedFlow<NavigationCommand>()
    val command = _commands.asSharedFlow()

    suspend fun navigate(command: NavigationCommand) {
        LoggerUtil.d("NavigationManager 확인")
        _commands.emit(command)
    }
}