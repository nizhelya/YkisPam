/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ykis.ykispam.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.outlined.Chat
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.Church
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Church
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.ykis.ykispam.R

data class TopLevelDestination(
    val route: String = "",
    val selectedIcon: ImageVector = Icons.Default.Adjust,
    val unselectedIcon: ImageVector = Icons.Default.Adjust,
    val labelId: Int,
    val alwaysVisible : Boolean,
)

data class TabItem(
    val titleId : Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

val INFO_APARTMENT_TAB_ITEM = listOf(
    TabItem(
        titleId = R.string.bti,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    ),
    TabItem(
        titleId = R.string.list_family,
        selectedIcon = Icons.Filled.Group,
        unselectedIcon = Icons.Outlined.Group,
    )
)


val NAV_BAR_DESTINATIONS = listOf(
    TopLevelDestination(
        route = InfoApartmentScreen.route + "/0",
        selectedIcon = Icons.Filled.Info,
        unselectedIcon = Icons.Outlined.Info,
        labelId = R.string.info,
        alwaysVisible = false,

    ),
    TopLevelDestination(
            route = MeterScreen.route,
        selectedIcon = Icons.Filled.Church ,
        unselectedIcon = Icons.Outlined.Church,
        labelId = R.string.meters,
        alwaysVisible = false
    ),
    TopLevelDestination(
        route = ServiceListScreen.route,
        selectedIcon = Icons.Filled.Payments,
        unselectedIcon = Icons.Outlined.Payments,
        labelId = R.string.accrued,
        alwaysVisible = false

    ),
    TopLevelDestination(
        route = ChatScreen.route,
        selectedIcon = Icons.AutoMirrored.Filled.Chat,
        unselectedIcon = Icons.AutoMirrored.Outlined.Chat,
        labelId = R.string.chat,
        alwaysVisible = false
    )
)
val NAV_RAIL_DESTINATIONS = NAV_BAR_DESTINATIONS + listOf(
    TopLevelDestination(
        route = ProfileScreen.route,
        selectedIcon = Icons.Default.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        labelId = R.string.account,
        alwaysVisible = true
    ),
    TopLevelDestination(
        route = SettingsScreen.route,
        selectedIcon = Icons.Default.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        labelId = R.string.settings,
        alwaysVisible = true
    )
)
