package com.hnu.nanamail.ui.screen

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hnu.nanamail.R
import com.hnu.nanamail.ui.component.DrawerComponent
import com.hnu.nanamail.ui.component.MailItemComponent
import com.hnu.nanamail.ui.component.MainTopBarComponent
import com.hnu.nanamail.viewmodel.TrashViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashScreen(
    viewModel: TrashViewModel,
    navController: NavController
) {
    val login = remember { mutableStateOf(true) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
//    if (!viewModel.checkLogin()) {
//        login.value = false
//    }
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
                selectedItem = NavItem.Trash
            )
        },
        drawerState = drawerState,
        gesturesEnabled = false
    ) {
        Scaffold(
            topBar = {
                MainTopBarComponent(
                    onClickCompose = {
                        navController.navigate(NavItem.Compose.route)
                    },
                    onClickDrawer = {
                        scope.launch { drawerState.open() }
                    },
                    text = stringResource(id = R.string.trash_mail)
                )
            },
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(it)
                    .verticalScroll(rememberScrollState()),
            ) {
                for (mail in viewModel.mailList) {
                    MailItemComponent(mail = mail) {
                        navController.navigate("mail/${mail.uuid}")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TrashScreenPreview() {
    TrashScreen(
        viewModel = TrashViewModel(Application()),
        navController = NavController(LocalContext.current)
    )
}