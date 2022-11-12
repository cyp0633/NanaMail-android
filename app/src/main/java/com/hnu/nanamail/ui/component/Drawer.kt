package com.hnu.nanamail.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hnu.nanamail.R
import com.hnu.nanamail.ui.screen.NavItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerComponent(
    navController: NavController,
    onClickClose: () -> Unit,
    selectedItem: NavItem
) {
    val drawerItems = listOf(
        NavItem.Inbox,
        NavItem.Outbox,
    )
    ModalDrawerSheet() {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(vertical = 20.dp)
            ) {
                IconButton(onClick = onClickClose) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(30.dp),
                    )
                }
                Text(
                    text = stringResource(id = R.string.app_name),
                    modifier = Modifier.padding(vertical = 30.dp),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            for (items in drawerItems) {
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = items.iconRes!!),
                            contentDescription = stringResource(id = items.stringRes),
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = items.stringRes),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    },
                    onClick = {
                        navController.navigate(items.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        onClickClose()
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        unselectedContainerColor = MaterialTheme.colorScheme.surface,
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                    ),
                    selected = selectedItem == items
                )
            }
        }
    }
}

@Preview
@Composable
fun DrawerComponentPreview() {
    DrawerComponent(
        navController = NavController(LocalContext.current),
        onClickClose = {},
        selectedItem = NavItem.Inbox,
    )
}
