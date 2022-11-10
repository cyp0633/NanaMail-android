package com.hnu.nanamail.ui.screen

import android.app.Application
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hnu.nanamail.ui.component.TopBarComponent
import com.hnu.nanamail.viewmodel.InboxViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InboxScreen(
    viewModel: InboxViewModel,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopBarComponent(
                title = "收件箱"
            )
        },
    ) {
        Column (
            modifier = Modifier.padding(horizontal = 40.dp).padding(it)
                ) {

        }
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

