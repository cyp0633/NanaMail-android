package com.hnu.nanamail.ui.screen

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hnu.nanamail.BuildConfig
import com.hnu.nanamail.R
import com.hnu.nanamail.ui.component.DrawerComponent
import com.hnu.nanamail.ui.component.DrawerTopBarComponent
import com.hnu.nanamail.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val drawerScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            DrawerComponent(
                navController = navController,
                onClickClose = {
                    drawerScope.launch {
                        drawerState.close()
                    }
                },
                selectedItem = NavItem.Settings
            )
        },
        modifier = Modifier.fillMaxSize(),
        drawerState = drawerState,
        gesturesEnabled = false,
    ) {
        Scaffold(
            topBar = {
                DrawerTopBarComponent(
                    onClickDrawer = {
                        drawerScope.launch {
                            drawerState.open()
                        }
                    },
                    text = stringResource(id = R.string.settings)
                )
            },
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.displayMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 20.dp)
                )
                Text(
                    text = BuildConfig.VERSION_NAME + " (" + BuildConfig.VERSION_CODE + ")",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                Text(
                    text = stringResource(id = R.string.credits),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary,
                )
                Text(
                    text = stringResource(id = R.string.course_project),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary,
                )
                Text(
                    text = stringResource(id = R.string.open_source),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(vertical = 20.dp)
                )
                Text(
                    text = """
                        JavaMail (JakartaMail), EPLv2
                        Google Accompanist, Apache 2.0
                        Jetpack libraries, Apache 2.0
                        Koin, Apache 2.0
                    """.trimIndent(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = {
                        viewModel.clearData()
                        navController.navigate(NavItem.Setup.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    ),
                    modifier = Modifier.padding(vertical = 20.dp)
                ) {
                    Text(text = stringResource(id = R.string.clear_and_reset))
                }
            }
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(
        navController = NavController(LocalContext.current),
        viewModel = SettingsViewModel(Application())
    )
}