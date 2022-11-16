package com.hnu.nanamail.ui.screen

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hnu.nanamail.R
import com.hnu.nanamail.ui.component.DrawerComponent
import com.hnu.nanamail.ui.component.MainTopBarComponent
import com.hnu.nanamail.viewmodel.TrashViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun TrashScreen(
    viewModel: TrashViewModel,
    navController: NavController
) {
    val login = remember { mutableStateOf(true) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    fun refresh() = refreshScope.launch {
        refreshing = true
        viewModel.fetchMail()
        delay(1000)
        refreshing = false
    }

    val refreshState = rememberPullRefreshState(refreshing, ::refresh)


//    if (User.currentUser == null) {
//        login.value = viewModel.checkLogin()
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
        gesturesEnabled = drawerState.isOpen,
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
            Box(
                modifier = Modifier
                    .padding(it)
                    .pullRefresh(refreshState)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize()
                        .heightIn(min = 300.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
//                    for (mail in viewModel.mailList) {
//                        MailItemComponent(mail = mail) {
//                            navController.navigate("mail/${mail.uuid}")
//                        }
//                    }
                    // POP3 并不支持垃圾邮件文件夹，所以显示不支持
                    Icon(
                        painter = painterResource(id = R.drawable.error),
                        contentDescription = "error",
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .size(100.dp),
                        tint = MaterialTheme.colorScheme.error
                    )
                    Text(
                        text = stringResource(id = R.string.not_supported_by_protocol),
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(vertical = 10.dp),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                PullRefreshIndicator(
                    refreshing = refreshing,
                    state = refreshState,
                    modifier = Modifier.align(Alignment.TopCenter),
                    contentColor = MaterialTheme.colorScheme.primary
                )
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