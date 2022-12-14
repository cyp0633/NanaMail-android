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
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
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
                            color = if (!mail.isRead) {
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
                        text = mail.getShortDate(),
                        style = MaterialTheme.typography.bodySmall,
                        color = if (!mail.isRead) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.secondary
                        }
                    )
                }
                Text(
                    text = mail.subject,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (!mail.isRead) {
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
                    color = if (!mail.isRead) {
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
            thickness = 0.5f.dp,
            modifier = Modifier.padding(horizontal = 1.dp)
        )
    }
}

@Preview(name = "????????????")
@Composable
fun ReadMailItemComponentPreview() {
    Column {
        MailItemComponent(
            mail = Mail(
                uuid = "1",
                account = "test",
                sender = "sender",
                senderAddress = "sender@test",
                recipientTo = "test, test2",
                recipientCc = "test3",
                subject = "????????????????????????",
                content = "????????????",
                preview = "????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????",
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
        MailItemComponent(
            mail = Mail(
                uuid = "1",
                account = "test",
                sender = "sender",
                senderAddress = "sender@test",
                recipientTo = "test, test2",
                recipientCc = "test3",
                subject = "????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????",
                content = "????????????",
                preview = "????????????",
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
}