package com.hnu.nanamail.ui.screen

import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hnu.nanamail.R
import com.hnu.nanamail.data.Mail
import com.hnu.nanamail.ui.theme.NanaMailTheme
import com.hnu.nanamail.viewmodel.DetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewModel,
    uuid: String
) {
    // 在启动时拉取邮件信息
    LaunchedEffect(Unit) {
        viewModel.fetch(uuid)
    }
    if (!viewModel.exist.value) {
        AlertDialog(
            onDismissRequest = { navController.popBackStack() },
            title = { Text(stringResource(id = R.string.mail_not_found)) },
            confirmButton = { Text(stringResource(id = R.string.confirm)) }
        )
    } else {
        Scaffold(
            topBar = {
                DetailTopBar(
                    onClickBack = { navController.popBackStack() },
                    onClickUnread = {
                        viewModel.setUnread()
                        navController.popBackStack()
                    },
                    onClickDelete = {
                        viewModel.delete()
                        navController.popBackStack()
                    },
                )
            }
        ) { paddingValues ->
            DetailComponent(
                viewModel = viewModel,
                mail = viewModel.mail.value,
                paddingValues = paddingValues
            )
        }
    }
}

@Composable
fun DetailComponent(
    viewModel: DetailViewModel,
    mail: Mail,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        // 主题
        Text(
            text = mail.subject,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(vertical = 10.dp),
            color = MaterialTheme.colorScheme.primary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Row(
            modifier = Modifier.padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 发件人
            Text(
                text = mail.sender,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(10.dp))
            // 发件时间
            Text(
                text = mail.getShortDate(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
        }
        // 收件人和按钮
        Row(
            modifier = Modifier.padding(vertical = 10.dp),
        ) {
            Text(
                text = stringResource(id = R.string.recipient_to) + ": " + mail.recipientTo,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
            Spacer(modifier = Modifier.width(10.dp))
            IconButton(
                onClick = {
                    viewModel.expandMsgDetail.value = !viewModel.expandMsgDetail.value
                },
                modifier = Modifier.size(20.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.expand_more),
                    contentDescription = stringResource(id = R.string.expand_detail),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
        if (viewModel.expandMsgDetail.value) {
            CommunicationDetailComponent(mail = mail)
        }
        Divider()
        // 正文
        Text(
            text = mail.content,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(vertical = 10.dp),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(
    onClickBack: () -> Unit,
    onClickUnread: () -> Unit,
    onClickDelete: () -> Unit,
) {
    TopAppBar(
        modifier = Modifier.statusBarsPadding(),
        navigationIcon = {
            IconButton(onClick = onClickBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = onClickUnread) {
                Icon(
                    painter = painterResource(R.drawable.mark_email_unread),
                    contentDescription = "Mark as unread",
                    modifier = Modifier.size(24.dp)
                )
            }
            IconButton(onClick = onClickDelete) {
                Icon(
                    painter = painterResource(R.drawable.delete),
                    contentDescription = "Delete",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {}

    )
}

@Composable
fun CommunicationDetailComponent(
    mail: Mail,
) {
    Card(
        modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 5.dp),
            ) {
                // 发件人
                Text(
                    text = stringResource(id = R.string.sender),
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                // 收件人
                Text(
                    text = stringResource(id = R.string.recipient_to),
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                // 抄送
                if (mail.recipientCc != "") {
                    Text(
                        text = stringResource(id = R.string.recipient_cc),
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                }
                // 日期
                Text(
                    text = stringResource(id = R.string.date),
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
                // 加密
                Text(
                    text = stringResource(id = R.string.encryption),
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 5.dp),
            ) {
                // 发件人
                Row {
                    Text(
                        text = mail.sender,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(vertical = 5.dp),
                    )
                    Text(
                        text = " · " + mail.senderAddress,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(vertical = 5.dp),
                    )
                }
                // 收件人
                Text(
                    text = mail.recipientTo,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(vertical = 5.dp),
                )
                // 抄送
                if (mail.recipientCc != "") {
                    Text(
                        text = mail.recipientCc,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(vertical = 5.dp),
                    )
                }
                // 日期
                Text(
                    text = mail.getTimeStr(),
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(vertical = 5.dp),
                )
                // 加密
                Text(
                    text = stringResource(id = R.string.no_encryption),
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(vertical = 5.dp),
                )
            }
        }
    }

}

@Preview
@Composable
fun DetailTopBarPreview() {
    NanaMailTheme {
        DetailTopBar(
            onClickBack = {},
            onClickUnread = {},
            onClickDelete = {},
        )
    }
}

@Preview
@Composable
fun CommunicationDetailComponentPreview() {
    NanaMailTheme {
        CommunicationDetailComponent(
            mail = Mail(
                subject = "测试标题",
                content = "测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容",
                sender = "测试发件人",
                senderAddress = "test@testmail",
                recipientTo = "test2@testmail",
                recipientCc = "test3@testmail",
            )
        )
    }
}

@Preview
@Composable
fun DetailComponentPreview() {
    NanaMailTheme {
        DetailComponent(
            viewModel = DetailViewModel(Application()),
            mail = Mail(
                subject = "测试标题",
                content = "测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容",
                sender = "测试发件人",
                senderAddress = "test@testmail",
                recipientTo = "test2@testmail",
                recipientCc = "test3@testmail",
            )
        )
    }
}