package com.hnu.nanamail.ui.screen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hnu.nanamail.R
import com.hnu.nanamail.viewmodel.*

@Composable
fun NavScreen() {
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
            val viewModel = ViewModelProvider(it)[SentViewModel::class.java]
            SentScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(
            NavItem.Detail.route + "/{uuid}",
            arguments = listOf(navArgument("uuid") { type = NavType.StringType })
        ) {
            val viewModel = ViewModelProvider(it)[DetailViewModel::class.java]
            DetailScreen(
                navController = navController,
                viewModel = viewModel,
                uuid = it.arguments?.getString("uuid") ?: ""
            )
        }
        composable(NavItem.Trash.route) {
            val viewModel = ViewModelProvider(it)[TrashViewModel::class.java]
            TrashScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(NavItem.Settings.route) {
            val viewModel = ViewModelProvider(it)[SettingsViewModel::class.java]
            SettingsScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(NavItem.Contacts.route) {
            val viewModel = ViewModelProvider(it)[ContactsViewModel::class.java]
            ContactsScreen(
                navController = navController,
                viewModel = viewModel
            )
        }
        composable(
            NavItem.ContactDetail.route + "/{uuid}",
            arguments = listOf(navArgument("uuid") { type = NavType.StringType })
        ) {
            val viewModel = ViewModelProvider(it)[ContactDetailViewModel::class.java]
            ContactDetailScreen(
                navController = navController,
                viewModel = viewModel,
                uuid = it.arguments?.getString("uuid") ?: ""
            )
        }
        composable(NavItem.AddContact.route) {
            val viewModel = ViewModelProvider(it)[AddContactViewModel::class.java]
            AddContactScreen(
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
    object Detail : NavItem("detail", null, R.string.mail_detail)
    object Trash : NavItem("trash", R.drawable.unsubscribe, R.string.trash_mail)
    object Settings : NavItem("settings", R.drawable.settings, R.string.settings)
    object Contacts : NavItem("contacts", R.drawable.contacts, R.string.contacts)
    object AddContact : NavItem("add_contact", null, R.string.add_contact)
    object ContactDetail : NavItem("contact_detail", null, R.string.contact_detail)
}