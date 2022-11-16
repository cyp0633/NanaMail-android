package com.hnu.nanamail.ui.screen

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hnu.nanamail.ui.component.DrawerComponent
import com.hnu.nanamail.ui.component.MainTopBarComponent
import com.hnu.nanamail.viewmodel.ContactsViewModel
import kotlinx.coroutines.delay
import com.hnu.nanamail.R
import com.hnu.nanamail.ui.component.ContactListComponent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ContactsScreen(
    viewModel: ContactsViewModel,
    navController: NavController,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }
    val contactList by viewModel.contactList.observeAsState(listOf())
    fun refresh() = scope.launch {
        refreshing = true
        viewModel.getContactList()
        delay(1000)
        refreshing = false
    }

    val refreshState = rememberPullRefreshState(refreshing, ::refresh)
    LaunchedEffect(Unit) {
        viewModel.getContactList()
    }
    ModalNavigationDrawer(
        drawerContent = {
            DrawerComponent(
                navController = navController,
                onClickClose = { scope.launch { drawerState.close() } },
                selectedItem = NavItem.Contacts
            )
        },
        modifier = Modifier.fillMaxSize(),
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
    ) {
        Scaffold(
            topBar = {
                MainTopBarComponent(
                    text = stringResource(id = R.string.contacts),
                    onClickDrawer = { scope.launch { drawerState.open() } },
                    onClickCompose = { navController.navigate(NavItem.AddContact.route) }
                )
            },
            modifier = Modifier
                .fillMaxSize()
                .heightIn(min = 300.dp),
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .pullRefresh(refreshState)
                    .fillMaxSize()
                    .heightIn(min = 300.dp)
            ) {
                ContactListComponent(
                    contacts = contactList,
                    onClickItem = { uuid: String -> navController.navigate(NavItem.ContactDetail.route + "/${uuid}") },
                    modifier = Modifier
                        .fillMaxSize()
                        .heightIn(min = 300.dp)
                        .padding(horizontal = 20.dp)
                )
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
fun ContactsScreenPreview() {
    ContactsScreen(
        viewModel = ContactsViewModel(Application()),
        navController = NavController(Application())
    )
}