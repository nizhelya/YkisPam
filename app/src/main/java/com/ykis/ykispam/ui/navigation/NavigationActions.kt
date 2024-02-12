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
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Church
import androidx.compose.material.icons.outlined.Payments
import androidx.compose.ui.graphics.vector.ImageVector
import com.ykis.ykispam.R

data class TopLevelDestination(
    val route: String = "",
    val selectedIcon: ImageVector = Icons.Default.Adjust,
    val unselectedIcon: ImageVector = Icons.Default.Adjust,
    val labelId: Int,
)


val NAV_BAR_DESTINATIONS = listOf(
//    TopLevelDestination(
//        route = APARTMENT_SCREEN,
//        selectedIcon = Icons.Filled.Home,
//        unselectedIcon = Icons.Outlined.Home,
//        labelId = R.string.house
//    ),
    TopLevelDestination(
            route = MeterScreen.route,
        selectedIcon = Icons.Filled.Church ,
        unselectedIcon = Icons.Outlined.Church,
        labelId = R.string.meters
    ),
    TopLevelDestination(
        route = ServiceListScreen.route,
        selectedIcon = Icons.Filled.Payments,
        unselectedIcon = Icons.Outlined.Payments,
        labelId = R.string.accrued
    ),
    TopLevelDestination(
        route = ChatScreen.route,
        selectedIcon = Icons.AutoMirrored.Filled.Chat,
        unselectedIcon = Icons.AutoMirrored.Outlined.Chat,
        labelId = R.string.chat,
    )
)
val NAV_RAIL_DESTINATIONS = NAV_BAR_DESTINATIONS + listOf(
    TopLevelDestination(
        route = ProfileScreen.route,
        selectedIcon = Icons.Default.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        labelId = R.string.account
    ),
//    TopLevelDestination(
//        route = YkisRoute.SETTINGS,
//        selectedIcon = Icons.Default.Settings,
//        unselectedIcon = Icons.Outlined.Settings,
//        labelId = R.string.settings
//    )
)
