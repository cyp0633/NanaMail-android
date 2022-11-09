package com.hnu.nanamail.ui.screen

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hnu.nanamail.R
import com.hnu.nanamail.viewmodel.SetupViewModel

@Composable
fun NavScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "setup") {
        composable(NavItem.Setup.route) {
            SetupScreen(
                navController = navController,
                SetupViewModel()
            )
        }
    }
}

sealed class NavItem(var route: String, var icon: ImageVector?, @StringRes val resourceId: Int) {
    object Inbox: NavItem("inbox", Icons.Filled.Email, R.string.inbox)
    object Sent: NavItem("sent", Icons.Filled.Email, R.string.sent)
    object Compose: NavItem("compose", Icons.Filled.Email, R.string.compose_mail)
    object Setup: NavItem("setup", Icons.Filled.Email, R.string.setup)
}