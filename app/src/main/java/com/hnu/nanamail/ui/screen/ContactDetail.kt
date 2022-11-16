package com.hnu.nanamail.ui.screen

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hnu.nanamail.R
import com.hnu.nanamail.data.Contact
import com.hnu.nanamail.ui.component.BackTopBarComponent
import com.hnu.nanamail.ui.component.TopBarComponent
import com.hnu.nanamail.viewmodel.ContactDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactDetailScreen(
    navController: NavController,
    viewModel: ContactDetailViewModel,
    uuid: String
) {
    LaunchedEffect(Unit) {
        viewModel.fetch(uuid)
    }
    if (!viewModel.exist.value) {
        AlertDialog(
            onDismissRequest = { navController.popBackStack() },
            title = { Text(stringResource(id = R.string.contact_not_found)) },
            confirmButton = { Text(stringResource(id = R.string.confirm)) }
        )
    } else {
        Scaffold(
            topBar = {
                BackTopBarComponent(
                    title = viewModel.contact.value.name,
                    onBackClick = { navController.popBackStack() })
            }
        ) { paddingValues ->
            ContactDetailComponent(
                viewModel = viewModel,
                contact = viewModel.contact.value,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
fun ContactDetailComponent(
    viewModel: ContactDetailViewModel,
    contact: Contact,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        // 联系人名
        Text(
            text = contact.name,
            modifier = Modifier.padding(vertical = 20.dp),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary
        )
        // 联系人地址
        Text(
            text = contact.address,
            modifier = Modifier.padding(vertical = 10.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Preview
@Composable
fun ContactDetailComponentPreview() {
    ContactDetailComponent(
        viewModel = ContactDetailViewModel(Application()),
        contact = Contact(
            name = "cyp0633",
            address = "cyp0633@cyp0633.icu",
            description = "author"
        )
    )
}