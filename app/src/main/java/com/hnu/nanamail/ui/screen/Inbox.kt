package com.hnu.nanamail.ui.screen

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hnu.nanamail.ui.component.TopBarComponent
import com.hnu.nanamail.viewmodel.InboxViewModel
import com.hnu.nanamail.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InboxScreen(
    viewModel: InboxViewModel,
    navController: NavController
) {
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