package com.hnu.nanamail.ui.screen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hnu.nanamail.R
import com.hnu.nanamail.viewmodel.SetupViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupScreen(
    navController: NavController,
    setupViewModel: SetupViewModel
) {
    val mailAddress = remember { mutableStateOf("")}
    val password = remember { mutableStateOf("")}
    Scaffold(

    ) { PaddingValues->
        Column (
            modifier = Modifier
                .padding(PaddingValues)
                .padding(horizontal = 40.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(id = R.string.finish_basic_settings),
                modifier = Modifier.padding(vertical = 30.dp),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            TextField(
                value = mailAddress.value,
                onValueChange = { mailAddress.value = it },
                label = { Text(text = stringResource(id = R.string.mail_address)) },
                modifier = Modifier.padding(vertical = 10.dp),
                shape = MaterialTheme.shapes.medium
            )
            TextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text(text = stringResource(id = R.string.password)) },
                modifier = Modifier.padding(vertical = 10.dp),
                shape = MaterialTheme.shapes.medium,
                visualTransformation = PasswordVisualTransformation()
            )
        }
    }
}

@Preview
@Composable
fun SetupScreenPreview() {
    SetupScreen(navController = NavController(LocalContext.current), setupViewModel = SetupViewModel())
}