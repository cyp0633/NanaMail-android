package com.hnu.nanamail.ui.screen

import android.app.Application
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hnu.nanamail.R
import com.hnu.nanamail.viewmodel.ComposeViewModel
import com.hnu.nanamail.viewmodel.InboxViewModel
import com.hnu.nanamail.viewmodel.OutboxViewModel
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
        composable(NavItem.Compose.route) {
            val viewModel = ViewModelProvider(it)[ComposeViewModel::class.java]
            ComposeScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(NavItem.Outbox.route) {
            val viewModel = ViewModelProvider(it)[OutboxViewModel::class.java]
            OutboxScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(NavItem.Sent.route) {
            val viewModel = ViewModelProvider(it)[OutboxViewModel::class.java]
            SentScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}

sealed class NavItem(
    var route: String,
    @DrawableRes val iconRes: Int?,
    @StringRes val stringRes: Int
) {
    object Inbox : NavItem("inbox", R.drawable.inbox, R.string.inbox)
    object Outbox : NavItem("outbox", R.drawable.outbox, R.string.outbox)
    object Compose : NavItem("compose", R.drawable.edit, R.string.compose_mail)
    object Setup : NavItem("setup", null, R.string.setup)
    object Sent : NavItem("sent", R.drawable.send, R.string.sent)
}