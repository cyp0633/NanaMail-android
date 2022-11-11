package com.hnu.nanamail.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hnu.nanamail.R
import com.hnu.nanamail.data.Mail
import com.hnu.nanamail.data.MailType
import com.hnu.nanamail.data.getTimeStr

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
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = mail.sender,
                            style = MaterialTheme.typography.titleMedium,
                            color = if (mail.isRead) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.secondary
                            },
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        if (mail.hasAttachment) {
                            Icon(
                                painter = painterResource(id = R.drawable.attachment),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(25.dp)
                                    .padding(horizontal = 3.dp),
                                tint = MaterialTheme.colorScheme.secondary
                            )
                        }
                    }
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
                    overflow = TextOverflow.Ellipsis,
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
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        Divider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 1.dp,
        )
    }
}

@Preview(name = "未读邮件")
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
            preview = "测试预览测试预览测试预览测试预览测试预览测试预览测试预览测试预览测试预览测试预览测试预览测试预览测试预览测试预览测试预览测试预览测试预览测试预览",
            isRead = false,
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

@Preview(name = "已读邮件")
@Composable
fun ReadMailItemComponentPreview() {
    MailItemComponent(
        mail = Mail(
            uuid = "1",
            account = "test",
            sender = "sender",
            senderAddress = "sender@test",
            receiveTo = "test, test2",
            receiveCc = "test3",
            title = "测试标题测试标题测试标题测试标题测试标题测试标题测试标题测试标题测试标题测试标题测试标题测试标题测试标题测试标题测试标题",
            content = "测试内容",
            preview = "测试预览",
            isRead = true,
            hasAttachment = false,
            type = MailType.INBOX,
            attachmentDownloaded = false,
            uid = 1,
            time = 1668090507,
        ),
        onClick = {},
        modifier = Modifier.fillMaxWidth()
    )
}