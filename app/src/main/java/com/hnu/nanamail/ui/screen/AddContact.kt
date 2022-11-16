package com.hnu.nanamail.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hnu.nanamail.viewmodel.AddContactViewModel
import com.hnu.nanamail.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactScreen(
    viewModel: AddContactViewModel,
    navController: NavController
) {
    Scaffold(
        topBar = {
            ComposeTopBar(
                onClickSend = { viewModel.submit();navController.popBackStack() },
                onClickBack = { navController.popBackStack() },
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.person),
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(4.dp)
                )
                TextField(
                    value = viewModel.name.value,
                    onValueChange = { viewModel.name.value = it },
                    singleLine = true,
                    placeholder = { Text(text = stringResource(id = R.string.name)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Row(
                modifier = Modifier.padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.mail),
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(4.dp)
                )
                TextField(
                    value = viewModel.email.value,
                    onValueChange = { viewModel.email.value = it },
                    singleLine = true,
                    placeholder = { Text(text = stringResource(id = R.string.email)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Row(
                modifier = Modifier.padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.description),
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(4.dp)
                )
                TextField(
                    value = viewModel.description.value,
                    onValueChange = { viewModel.description.value = it },
                    singleLine = true,
                    placeholder = { Text(text = stringResource(id = R.string.description)) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactTopBarComponent(
    onClickExit: () -> Unit,
    onClickSave: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onClickExit) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "返回",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        title = {
            Text(
                text = stringResource(id = R.string.add_contact),
                color = MaterialTheme.colorScheme.primary
            )
        },
        actions = {
            Button(
                onClick = onClickSave,
            ) {
                Text(
                    text = stringResource(id = R.string.save),
                )
            }
        }

    )
}

@Preview
@Composable
fun AddContactTopBarComponentPreview() {
    AddContactTopBarComponent(
        onClickExit = {},
        onClickSave = {}
    )
}