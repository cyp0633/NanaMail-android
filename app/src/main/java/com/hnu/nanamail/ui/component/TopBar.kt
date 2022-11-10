package com.hnu.nanamail.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hnu.nanamail.ui.theme.NanaMailTheme

// 带返回按钮的顶栏
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTopBarComponent(
    title: String,
    onBackClick: () -> Unit
) {
    TopAppBar (
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.primary
            )
        },
        navigationIcon = {
            IconButton(onClick = {onBackClick()}) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "返回",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    )
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

// 顶栏
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(
    title: String
) {
    TopAppBar (
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.primary
            )
        }
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