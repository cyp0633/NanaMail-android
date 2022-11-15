package com.hnu.nanamail.ui.screen


import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
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
    viewModel: SetupViewModel
) {
    val scope = rememberCoroutineScope()
    if (viewModel.showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                if (viewModel.proceed.value) {
                    navController.popBackStack()
                }
                viewModel.showDialog.value = false
            },
            title = {
                Text(text = viewModel.dialogText)
            },
            confirmButton = {
                TextButton(onClick = {
                    if (viewModel.proceed.value) {
                        navController.popBackStack()
                    }
                    viewModel.showDialog.value = false
                }) {
                    Text(text = stringResource(id = R.string.confirm))
                }
            }
        )
    }

    Scaffold(
        modifier = Modifier.imePadding()
    ) { PaddingValues ->
        Column(
            modifier = Modifier
                .padding(PaddingValues)
                .padding(horizontal = 40.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // 标题
            Text(
                text = stringResource(id = R.string.finish_basic_settings),
                modifier = Modifier.padding(vertical = 30.dp),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            // 基本账户设置
            Text(
                text = stringResource(id = R.string.basic_account_info),
                modifier = Modifier.padding(vertical = 5.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            // 邮箱地址
            TextField(
                value = viewModel.mailAddress.value,
                onValueChange = {
                    viewModel.mailAddress.value = it
                    if (it.contains("@")) {
                        val server = it.substring(it.indexOf("@") + 1)
                        if (!viewModel.modifiedPop3Server.value) {
                            viewModel.pop3Server.value = "pop3.$server"
                        }
                        if (!viewModel.modifiedSmtpServer.value) {
                            viewModel.smtpServer.value = "smtp.$server"
                        }
                    }
                },
                label = { Text(text = stringResource(id = R.string.mail_address)) },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
            )
            // 密码
            TextField(
                value = viewModel.password.value,
                onValueChange = { viewModel.password.value = it },
                label = { Text(text = stringResource(id = R.string.password)) },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(20.dp))
            // 接收设置
            Text(
                text = stringResource(id = R.string.receive_server_info),
                modifier = Modifier.padding(vertical = 5.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            // POP3服务器
            TextField(
                value = viewModel.pop3Server.value,
                onValueChange = {
                    viewModel.modifiedPop3Server.value = true
                    viewModel.pop3Server.value = it
                },
                label = { Text(text = stringResource(id = R.string.pop3_server)) },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
            )
            // 加密方式复选框
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = viewModel.receiveStartTlsChecked.value,
                    onCheckedChange = { viewModel.changeReceiveEncryptMethod() },
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = stringResource(id = R.string.enable_starttls),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            // 端口号
            TextField(
                value = viewModel.receivePortNumber.value,
                onValueChange = { viewModel.receivePortNumber.value = it },
                label = { Text(text = stringResource(id = R.string.receive_port_number)) },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(20.dp))
            // 发送设置
            Text(
                text = stringResource(id = R.string.send_server_info),
                modifier = Modifier.padding(vertical = 5.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            // SMTP服务器
            TextField(
                value = viewModel.smtpServer.value,
                onValueChange = {
                    viewModel.modifiedSmtpServer.value = true
                    viewModel.smtpServer.value = it
                },
                label = { Text(text = stringResource(id = R.string.smtp_server)) },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
            )
            // 加密方式复选框
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = viewModel.sendStartTlsChecked.value,
                    onCheckedChange = { viewModel.changeSendEncryptMethod() },
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = stringResource(id = R.string.enable_starttls),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            // 端口号
            TextField(
                value = viewModel.sendPortNumber.value,
                onValueChange = { viewModel.sendPortNumber.value = it },
                label = { Text(text = stringResource(id = R.string.send_port_number)) },
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(),
            )
            // 完成
            Button(
                onClick = {
                    viewModel.verify()
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
    SetupScreen(
        navController = NavController(LocalContext.current),
        viewModel = SetupViewModel(Application())
    )
}
