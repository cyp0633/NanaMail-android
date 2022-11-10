package com.hnu.nanamail.ui.screen

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hnu.nanamail.viewmodel.InboxViewModel
import com.hnu.nanamail.R
import com.hnu.nanamail.data.Mail
import com.hnu.nanamail.data.MailType
import com.hnu.nanamail.data.getTimeStr


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InboxScreen(
    viewModel: InboxViewModel,
    navController: NavController
) {
    val login = remember { mutableStateOf(true) }
    if (!viewModel.checkLogin()) {
        login.value = false
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
                }) {
                    Text(text = stringResource(id = R.string.confirm))
                }
            }
        )
    }
    Scaffold(
        topBar = {
            InboxTopBarComponent {
                navController.navigate(NavItem.Compose.route)
            }
        },
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(it)
        ) {
            TrashEntryComponent(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                navController.navigate("trash")
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
        modifier = Modifier
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
                text = stringResource(id = R.string.trash_mail),
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

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun InboxTopBarComponent(
    onClickCompose: () -> Unit,
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.statusBarsPadding(),
        title = {
            Text(
                text = stringResource(id = R.string.inbox),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        actions = {
            IconButton(onClick = { onClickCompose() }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary,
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MailItemComponent(
    mail: Mail,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Card(
            modifier = modifier,
            onClick = { onClick() },
            shape = RoundedCornerShape(0.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = mail.sender,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (mail.isRead) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.secondary
                        }
                    )
                    Text(
                        text = mail.getTimeStr(),
                        style = MaterialTheme.typography.bodySmall,
                        color = if (mail.isRead) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.secondary
                        }
                    )
                }
                Text(
                    text = mail.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (mail.isRead) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.secondary
                    },
                    maxLines = 1,
                )
                Text(
                    text = mail.preview,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (mail.isRead) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.secondary
                    },
                    maxLines = 1,
                )
            }
        }
        Divider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 1.dp,
        )
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

@Preview
@Composable
fun InboxTopBarComponentPreview() {
    InboxTopBarComponent(
        onClickCompose = {}
    )
}

@Preview
@Composable
fun MailItemComponentPreview() {
    MailItemComponent(
        mail = Mail(
            uuid = "1",
            account = "test",
            sender = "sender",
            senderAddress = "sender@test",
            receiveTo = "test, test2",
            receiveCc = "test3",
            title = "测试标题",
            content = "测试内容",
            preview = "测试预览",
            isRead = true,
            hasAttachment = true,
            type = MailType.INBOX,
            attachmentDownloaded = false,
            uid = 1,
            time = 1668090507,
        ),
        onClick = {},
        modifier = Modifier.fillMaxWidth()
    )
}