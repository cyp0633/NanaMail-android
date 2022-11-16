package com.hnu.nanamail.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hnu.nanamail.data.Contact

/**
 * 联系人项
 * @property contact 联系人
 * @property modifier Modifier
 * @property onClick 点击事件，如添加联系人、进入详情等
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactItemComponent(
    contact: Contact,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        onClick = { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        shape = RoundedCornerShape(0.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = contact.name,
                modifier = Modifier.padding(vertical = 4.dp),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = contact.address,
                modifier = Modifier.padding(vertical = 4.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

/**
 * 联系人列表
 * @property contacts 联系人列表，LiveData
 * @property modifier Modifier
 * @property onClickItem 点击事件，如添加联系人、进入详情等
 */
@Composable
fun ContactListComponent(
    contacts: MutableLiveData<List<Contact>>,
    onClickItem: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        for (contact in contacts.value!!) {
            ContactItemComponent(
                contact = contact,
                onClick = onClickItem,
            )
            Divider()
        }
    }
}

@Preview
@Composable
fun ContactItemComponentPreview() {
    ContactItemComponent(
        modifier = Modifier.fillMaxWidth(),
        contact = Contact(
            name = "Nahida",
            address = "nahida@genshin",
        ),
        onClick = { },
    )
}

