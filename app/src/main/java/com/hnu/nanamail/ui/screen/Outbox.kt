package com.hnu.nanamail.ui.screen

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.hnu.nanamail.R
import com.hnu.nanamail.data.User
import com.hnu.nanamail.ui.component.DrawerComponent
import com.hnu.nanamail.ui.component.MailItemComponent
import com.hnu.nanamail.ui.component.MainTopBarComponent
import com.hnu.nanamail.viewmodel.OutboxViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutboxScreen(
    navController: NavHostController,
    viewModel: OutboxViewModel
) {
    val login = remember {
        mutableStateOf(true)
    }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    if (User.currentUser == null) {
        login.value = viewModel.checkLogin()
    }
    if (!login.value) {
        AlertDialog(
            onDismissRequest = {
                navController.navigate(NavItem.Setup.route)
            },
            title = {
                Text(text = stringResource(id = R.string.no_login))
            },
            confirmButton = {
                TextButton(onClick = {
                    navController.navigate(NavItem.Setup.route)
//                    login.value = true
                }) {
                    Text(text = stringResource(id = R.string.confirm))
                }
            }
        )
    } else {
        LaunchedEffect(true) {
            viewModel.getMailList()
        }
    }
    ModalNavigationDrawer(
        drawerContent = {
            DrawerComponent(
                navController = navController,
                onClickClose = { scope.launch { drawerState.close() } },
                selectedItem = NavItem.Outbox
            )
        },
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
    ) {
        Scaffold(
            topBar = {
                MainTopBarComponent(
                    text = stringResource(id = R.string.outbox),
                    onClickCompose = {
                        navController.navigate(NavItem.Compose.route)
                    },
                    onClickDrawer = {
                        scope.launch { drawerState.open() }
                    }
                )
            },
        ) { PaddingValues ->
            Column(
                modifier = Modifier
                    .padding(PaddingValues)
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                for (mail in viewModel.mailList) {
                    MailItemComponent(mail = mail) {
                        navController.navigate("detail/${mail.uuid}")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun OutboxScreenPreview() {
    OutboxScreen(
        navController = NavHostController(Application()),
        viewModel = OutboxViewModel(Application())
    )
}
