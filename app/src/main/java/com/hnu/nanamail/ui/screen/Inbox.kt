package com.hnu.nanamail.ui.screen

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hnu.nanamail.R
import com.hnu.nanamail.ui.component.DrawerComponent
import com.hnu.nanamail.ui.component.MailItemComponent
import com.hnu.nanamail.ui.component.MainTopBarComponent
import com.hnu.nanamail.viewmodel.InboxViewModel
import kotlinx.coroutines.launch
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun InboxScreen(
    viewModel: InboxViewModel,
    navController: NavController
) {
    val login = remember { mutableStateOf(true) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    fun refresh() = refreshScope.launch {
        refreshing = true
//        viewModel.fetchMails()
        delay(5000)
        refreshing = false
    }

    val refreshState = rememberPullRefreshState(refreshing, ::refresh)
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
                selectedItem = NavItem.Inbox
            )
        },
        modifier = Modifier.fillMaxSize(),
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
                    text = stringResource(id = R.string.inbox)
                )
            },
            modifier = Modifier.fillMaxSize()
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
                        .heightIn(min = 300.dp)
                ) {
                    TrashEntryComponent(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp)
                    ) {
                        navController.navigate("trash")
                    }
                    for (mail in viewModel.mailList) {
                        MailItemComponent(mail = mail) {
                            navController.navigate("mail/${mail.uuid}")
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

// 垃圾邮件箱按钮
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashEntryComponent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.secondary,
        ),
        modifier = modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        onClick = { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(id = R.string.cant_find_mail_see_trash),
                modifier = Modifier,
                style = MaterialTheme.typography.titleMedium,
            )
            Icon(
                painter = painterResource(id = R.drawable.unsubscribe),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}


@Preview
@Composable
fun InboxScreenPreview() {
    InboxScreen(
        viewModel = InboxViewModel(Application()),
        navController = NavController(Application())
    )
}

@Preview
@Composable
fun TrashEntryComponentPreview() {
    TrashEntryComponent(
        onClick = {}
    )
}

