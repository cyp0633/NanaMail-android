package com.hnu.nanamail.ui.screen

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.hnu.nanamail.R
import com.hnu.nanamail.data.User
import com.hnu.nanamail.ui.component.DrawerComponent
import com.hnu.nanamail.ui.component.MailItemComponent
import com.hnu.nanamail.ui.component.MainTopBarComponent
import com.hnu.nanamail.viewmodel.SentViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SentScreen(
    navController: NavHostController,
    viewModel: SentViewModel
) {
    val login = remember {
        mutableStateOf(true)
    }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    fun refresh() = refreshScope.launch {
        refreshing = true
        viewModel.getMailList()
        delay(1000)
        refreshing = false
    }

    val refreshState = rememberPullRefreshState(refreshing, ::refresh)

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
                selectedItem = NavItem.Sent
            )
        },
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
    ) {
        Scaffold(
            topBar = {
                MainTopBarComponent(
                    text = stringResource(id = R.string.sent),
                    onClickCompose = {
                        navController.navigate(NavItem.Compose.route)
                    },
                    onClickDrawer = {
                        scope.launch { drawerState.open() }
                    }
                )
            },
        ) { PaddingValues ->
            Box(
                modifier = Modifier
                    .padding(PaddingValues)
                    .pullRefresh(refreshState)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .padding()
                        .padding(horizontal = 20.dp)
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize()
                        .heightIn(min = 300.dp)
                ) {
                    for (mail in viewModel.mailList) {
                        MailItemComponent(mail = mail) {
                            navController.navigate("detail/${mail.uuid}")
                        }
                    }
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
