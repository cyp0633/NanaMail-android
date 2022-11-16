package com.hnu.nanamail.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hnu.nanamail.R
import com.hnu.nanamail.ui.theme.NanaMailTheme

// 带返回按钮的顶栏
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTopBarComponent(
    title: String,
    onBackClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.primary
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "返回",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
}

// 顶栏
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(
    title: String
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.primary
            )
        }
    )
}

// 在侧边栏下的顶栏，如收件箱、已发送
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainTopBarComponent(
    onClickDrawer: () -> Unit,
    onClickCompose: () -> Unit,
    text: String
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.statusBarsPadding(),
        title = {
            Text(
                text = text,
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
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
        ),
        navigationIcon = {
            IconButton(onClick = { onClickDrawer() }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DrawerTopBarComponent(
    onClickDrawer: () -> Unit,
    text: String
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.statusBarsPadding(),
        title = {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge,
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
        ),
        navigationIcon = {
            IconButton(onClick = { onClickDrawer() }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        },
    )
}

@Preview
@Composable
fun TopBarPreview() {
    NanaMailTheme {
        TopBarComponent(
            title = "顶栏"
        )
    }
}

@Preview
@Composable
fun BackTopBarPreview() {
    NanaMailTheme {
        BackTopBarComponent(
            title = "带有返回的顶栏",
            onBackClick = {}
        )
    }
}

@Preview
@Composable
fun MainTopBarComponentPreview() {
    MainTopBarComponent(
        onClickCompose = {},
        onClickDrawer = {},
        text = "收件箱"
    )
}

@Preview
@Composable
fun DrawerTopBarComponentPreview() {
    DrawerTopBarComponent(
        onClickDrawer = {},
        text = "带侧边栏按钮的顶栏"
    )
}
