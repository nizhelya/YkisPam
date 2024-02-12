package com.ykis.ykispam.ui.navigation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ykis.ykispam.ui.navigation.NAV_BAR_DESTINATIONS

@Composable
fun BottomNavigationBar(
    selectedDestination: String,
    onClick: (String) -> Unit
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) {
        NAV_BAR_DESTINATIONS.forEach { destination ->
            NavigationBarItem(
                selected = selectedDestination.substringBefore("?") == destination.route,
                onClick = {
                    onClick(destination.route)
                },

                icon = {
                    Icon(
                        imageVector = if (selectedDestination.substringBefore("?") == destination.route) {
                            destination.selectedIcon
                        } else destination.unselectedIcon,
                        contentDescription = stringResource(id = destination.labelId)
                    )
                },
                alwaysShowLabel = true,
                label = { Text(text = stringResource(destination.labelId)) }
            )
        }
    }
}