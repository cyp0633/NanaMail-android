package com.hnu.nanamail.ui.screen


import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.hnu.nanamail.R
import com.hnu.nanamail.viewmodel.SetupViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupScreen(
    navController: NavController,
    viewModel: SetupViewModel
) {
    // 输入框内容
    val mailAddress = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val pop3Server = remember { mutableStateOf("") }
    val receiveEncryptMethod = remember { mutableStateOf("") }
    val receivePortNumber = remember { mutableStateOf("") }
    val smtpServer = remember { mutableStateOf("") }
    val sendEncryptMethod = remember { mutableStateOf("") }
    val sendPortNumber = remember { mutableStateOf("") }

    // 提示框内容
    var showDialog = false
    var onDismissRequest = {}
    val text = ""

    if (showDialog) {
        Dialog(
            onDismissRequest = onDismissRequest,
            content = {
                Text(text = text)
            }
        )
    }

    Scaffold { PaddingValues ->
        Column(
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
            Text(
                text = stringResource(id = R.string.basic_account_info),
                modifier = Modifier.padding(vertical = 5.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            TextField(
                value = mailAddress.value,
                onValueChange = { mailAddress.value = it },
                label = { Text(text = stringResource(id = R.string.mail_address)) },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
            )
            TextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text(text = stringResource(id = R.string.password)) },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.receive_server_info),
                modifier = Modifier.padding(vertical = 5.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            TextField(
                value = pop3Server.value,
                onValueChange = { pop3Server.value = it },
                label = { Text(text = stringResource(id = R.string.pop3_server)) },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
            )
            TextField(
                value = receiveEncryptMethod.value,
                onValueChange = { receiveEncryptMethod.value = it },
                label = { Text(text = stringResource(id = R.string.receive_encrypt_method)) },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
            )
            TextField(
                value = receivePortNumber.value,
                onValueChange = { receivePortNumber.value = it },
                label = { Text(text = stringResource(id = R.string.receive_port_number)) },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.send_server_info),
                modifier = Modifier.padding(vertical = 5.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            TextField(
                value = smtpServer.value,
                onValueChange = { smtpServer.value = it },
                label = { Text(text = stringResource(id = R.string.smtp_server)) },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
            )
            TextField(
                value = sendEncryptMethod.value,
                onValueChange = { sendEncryptMethod.value = it },
                label = { Text(text = stringResource(id = R.string.send_encrypt_method)) },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
            )
            TextField(
                value = sendPortNumber.value,
                onValueChange = { sendPortNumber.value = it },
                label = { Text(text = stringResource(id = R.string.send_port_number)) },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
            )
            Button(
                onClick = {
                    val result = viewModel.verify(
                        mailAddress.value,
                        password.value,
                        pop3Server.value,
                        receiveEncryptMethod.value,
                        receivePortNumber.value,
                        smtpServer.value,
                        sendEncryptMethod.value,
                        sendPortNumber.value
                    )
                    if (result == "success") {
                        onDismissRequest = { navController.navigate("inbox") }
                    }
                    showDialog = true
                },
                modifier = Modifier
                    .padding(vertical = 20.dp)
                    .fillMaxWidth(),
                content = {
                    Text(text = stringResource(id = R.string.save_and_verify))
                }
            )
        }
    }
}

@Preview
@Composable
fun SetupScreenPreview() {
    SetupScreen(navController = NavController(LocalContext.current), viewModel = SetupViewModel())
}
