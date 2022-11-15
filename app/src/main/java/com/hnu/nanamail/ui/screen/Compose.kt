package com.hnu.nanamail.ui.screen

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                    navController.popBackStack()
                }
            )
        }
    ) { PaddingValues ->
        Column(
            modifier = Modifier
                .padding(PaddingValues)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            // 发件人
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.sender),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = viewModel.username,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Divider()
            // 收件人 + 右侧展开按钮
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                ) {
                    Text(
                        text = stringResource(id = R.string.recipient_to),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    BasicTextField(
                        value = viewModel.recipient.value,
                        onValueChange = {
                            viewModel.recipient.value = it
                        },
                        textStyle = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                    )
                }
                if (!viewModel.showRecipientCc.value) {
                    IconButton(
                        onClick = {
                            viewModel.showRecipientCc.value = true
                        },
                        modifier = Modifier.size(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp),
                        )
                    }
                }
            }
            Divider()
            if (viewModel.showRecipientCc.value) {
                // 抄送人
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.recipient_cc),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    BasicTextField(
                        value = viewModel.recipientCc.value,
                        onValueChange = {
                            viewModel.recipientCc.value = it
                        },
                        textStyle = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Divider()
                // 密送
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.recipient_bcc),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    BasicTextField(
                        value = viewModel.recipientBcc.value,
                        onValueChange = {
                            viewModel.recipientBcc.value = it
                        },
                        textStyle = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Divider()
            }
            // 主题
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.subject),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
                Spacer(modifier = Modifier.width(10.dp))
                BasicTextField(
                    value = viewModel.subject.value,
                    onValueChange = {
                        viewModel.subject.value = it
                    },
                    textStyle = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Divider()
            // 正文
            Text(
                text = stringResource(id = R.string.content),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 10.dp)
            )
            BasicTextField(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxSize(),
                value = viewModel.content.value,
                onValueChange = {
                    viewModel.content.value = it
                },
                textStyle = MaterialTheme.typography.bodyLarge,
            )
        }
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