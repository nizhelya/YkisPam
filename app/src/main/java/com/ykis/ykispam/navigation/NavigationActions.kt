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

package com.ykis.ykispam.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddHome
import androidx.compose.material.icons.filled.Adjust
import androidx.compose.material.icons.filled.HomeWork
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Transcribe
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.twotone.Transcribe
import androidx.compose.ui.graphics.vector.ImageVector
import com.ykis.ykispam.R

object YkisRoute {
    const val ACCOUNT = "ProfileScreen"
    const val OSBB = "OsbbScreen"
    const val CHAT = "ChatScreen"
    const val MESSAGE = "MessageScreen"
    const val SETTINGS = "SettingsScreen"
    const val ADDAPARTMENT = "AddApartmentScreen"
}

data class TopLevelDestination(
    val route: String = "",
    val selectedIcon: ImageVector = Icons.Default.Adjust,
    val unselectedIcon: ImageVector = Icons.Default.Adjust,
    val iconTextId: Int = -1,
    val vkl:Boolean = true
)


val TOP_LEVEL_DESTINATIONS = listOf(
    TopLevelDestination(
        route = YkisRoute.ADDAPARTMENT,
        selectedIcon = Icons.Default.AddHome,
        unselectedIcon = Icons.Default.AddHome,
        iconTextId = R.string.add_appartment,
        vkl = true,
    ),
    TopLevelDestination(
        route = YkisRoute.ACCOUNT,
        selectedIcon = Icons.Default.AccountCircle,
        unselectedIcon = Icons.Default.AccountCircle,
        iconTextId = R.string.tab_account,
        vkl = true,

        ),

    TopLevelDestination(
        route = YkisRoute.OSBB,
        selectedIcon = Icons.Default.HomeWork,
        unselectedIcon = Icons.Default.HomeWork,
        iconTextId = R.string.tab_osbb,
        vkl = false,
    ),
    TopLevelDestination(
        route = YkisRoute.CHAT,
        selectedIcon = Icons.Outlined.Chat,
        unselectedIcon = Icons.Outlined.ChatBubbleOutline,
        iconTextId = R.string.tab_chat,
        vkl = true,

        ),
    TopLevelDestination(
        route = YkisRoute.MESSAGE,
        selectedIcon = Icons.TwoTone.Transcribe,
        unselectedIcon = Icons.Default.Transcribe,
        iconTextId = R.string.tab_message,
        vkl = true,
        ),
    TopLevelDestination(
        route = YkisRoute.SETTINGS,
        selectedIcon = Icons.Default.Settings,
        unselectedIcon = Icons.Default.Settings,
        iconTextId = R.string.tab_settings,
        vkl = false,

        ),

)
