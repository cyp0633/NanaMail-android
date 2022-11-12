package com.hnu.nanamail.ui.screen

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hnu.nanamail.R
import com.hnu.nanamail.viewmodel.ComposeViewModel

// 写邮件
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeScreen(
    viewModel: ComposeViewModel,
    navController: NavController,
) {
    Scaffold(
        topBar = {
            ComposeTopBar(
                onClickBack = {
                    navController.popBackStack()
                },
                onClickSend = {
                    viewModel.sendMail()
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeTopBar(
    onClickSend: () -> Unit,
    onClickBack: () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.statusBarsPadding(),
        title = {
            Text(
                text = stringResource(id = R.string.compose_mail),
                style = MaterialTheme.typography.titleLarge
            )
        },
        actions = {
            IconButton(onClick = onClickSend) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "发送",
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { onClickBack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        },

        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
        ),
    )
}

@Preview
@Composable
fun ComposeScreenPreview() {
    ComposeScreen(
        viewModel = ComposeViewModel(Application()),
        navController = NavController(Application())
    )
}

@Preview
@Composable
fun ComposeTopBarPreview() {
    ComposeTopBar(
        onClickSend = {},
        onClickBack = {}
    )
}