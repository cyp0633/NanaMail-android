package com.hnu.nanamail.ui.screen

import android.app.Application
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hnu.nanamail.R
import com.hnu.nanamail.viewmodel.InboxViewModel
import com.hnu.nanamail.viewmodel.SetupViewModel

@Composable
fun NavScreen(application: Application) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavItem.Inbox.route) {
        composable(NavItem.Setup.route) {
            val viewModel = ViewModelProvider(it)[SetupViewModel::class.java]
            SetupScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(NavItem.Inbox.route) {
            val viewModel = ViewModelProvider(it)[InboxViewModel::class.java]
            InboxScreen(
                navController = navController,
                viewModel = viewModel
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