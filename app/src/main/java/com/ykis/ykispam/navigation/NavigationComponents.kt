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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material.icons.twotone.Apartment
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import com.ykis.ykispam.BaseUIState
import com.ykis.ykispam.R
import com.ykis.ykispam.core.composable.LogoImage
import com.ykis.ykispam.core.composable.LogoImageShort

@Composable
fun ApartmentNavigationRail(
    baseUIState: BaseUIState,
    selectedDestination: String,
    navigationContentPosition: NavigationContentPosition,
    closeDetailScreen: () -> Unit,
    navigateToDestination: (String) -> Unit,
    setApartment: (Int) -> Unit,
    onDrawerClicked: () -> Unit = {},
) {
    NavigationRail(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
    ) {
        Layout(
            modifier = Modifier.widthIn(0.dp, 80.dp),
            content = {
                Column(
                    modifier = Modifier
                        .layoutId(LayoutType.HEADER),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    NavigationRailItem(
                        selected = false,
                        onClick = onDrawerClicked,
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = stringResource(id = R.string.navigation_drawer)
                            )
                        }
                    )
                    LogoImageShort()

                    val apartmentLazyListState = rememberLazyListState()
                    LazyColumn(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,
                        state = apartmentLazyListState

                    ) {

                        items(items = baseUIState.apartments, key = { it.addressId }) {
                            NavigationDrawerItem(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                selected = baseUIState.selectedDestination == "$APARTMENT_SCREEN?$ADDRESS_ID={${it.addressId}}",
                                label = {
                                    Text(
                                        text = it.addressId.toString(),
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.padding(start = 4.dp)
                                    )
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.TwoTone.Apartment,
                                        contentDescription = ""
                                    )

                                },
                                colors = NavigationDrawerItemDefaults.colors(
                                    unselectedContainerColor = Color.Transparent
                                ),
                                onClick = {
                                    setApartment(it.addressId)
                                    closeDetailScreen()
                                    navigateToDestination("$APARTMENT_SCREEN?$ADDRESS_ID={${it.addressId}}")
                                    onDrawerClicked()

                                }
                            )
                        }
                    }

                }
                Column(
                    modifier = Modifier
                        .layoutId(LayoutType.CONTENT)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TOP_LEVEL_DESTINATIONS.forEach { replyDestination ->

                        NavigationDrawerItem(
                            modifier = Modifier
                                .fillMaxWidth(),
                            selected = selectedDestination == replyDestination.route,
                            label = {
                                Text(
                                    text = stringResource(id = replyDestination.iconTextId),
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = replyDestination.selectedIcon,
                                    contentDescription = stringResource(
                                        id = replyDestination.iconTextId
                                    )
                                )
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                unselectedContainerColor = Color.Transparent
                            ),
                            onClick = { navigateToDestination(replyDestination.route) }
                        )
                    }
                }
            },
            measurePolicy = { measurables, constraints ->
                lateinit var headerMeasurable: Measurable
                lateinit var contentMeasurable: Measurable
                measurables.forEach {
                    when (it.layoutId) {
                        LayoutType.HEADER -> headerMeasurable = it
                        LayoutType.CONTENT -> contentMeasurable = it
                        else -> error("Unknown layoutId encountered!")
                    }
                }

                val headerPlaceable = headerMeasurable.measure(constraints)
                val contentPlaceable = contentMeasurable.measure(
                    constraints.offset(vertical = -headerPlaceable.height)
                )
                layout(constraints.maxWidth, constraints.maxHeight) {
                    // Place the header, this goes at the top
                    headerPlaceable.placeRelative(0, 0)

                    // Determine how much space is not taken up by the content
                    val nonContentVerticalSpace = constraints.maxHeight - contentPlaceable.height

                    val contentPlaceableY = when (navigationContentPosition) {
                        // Figure out the place we want to place the content, with respect to the
                        // parent (ignoring the header for now)
                        NavigationContentPosition.TOP -> 0
                        NavigationContentPosition.CENTER -> nonContentVerticalSpace / 2
                    }
                        // And finally, make sure we don't overlap with the header.
                        .coerceAtLeast(headerPlaceable.height)

                    contentPlaceable.placeRelative(0, contentPlaceableY)
                }
            }
        )
    }
}

@Composable
fun BottomNavigationBar(
    navigateToDestination: (String) -> Unit,
    selectedDestination: String,
) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        TOP_LEVEL_DESTINATIONS.forEach { destination ->
            if (destination.vkl) {
                NavigationBarItem(
                    selected = selectedDestination == destination.route,
                    onClick = { navigateToDestination(destination.route) },

                    icon = {
                        Icon(
                            imageVector = destination.selectedIcon,
                            contentDescription = stringResource(id = destination.iconTextId)
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun PermanentNavigationDrawerContent(
    baseUIState: BaseUIState,
    selectedDestination: String,
    navigationContentPosition: NavigationContentPosition,
    navigateToDestination: (String) -> Unit,
    closeDetailScreen: () -> Unit,
) {
    PermanentDrawerSheet(modifier = Modifier.sizeIn(minWidth = 200.dp, maxWidth = 300.dp)) {
        // TODO remove custom nav drawer content positioning when NavDrawer component supports it. ticket : b/232495216
        Layout(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(16.dp),
            content = {
                Column(
                    modifier = Modifier.layoutId(LayoutType.HEADER),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LogoImage()
                    }
                    val apartmentLazyListState = rememberLazyListState()
                    LazyColumn(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,
                        state = apartmentLazyListState

                    ) {

                        items(items = baseUIState.apartments, key = { it.addressId }) {
                            NavigationDrawerItem(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                selected = baseUIState.selectedDestination == "$APARTMENT_SCREEN?$ADDRESS_ID={${it.addressId}}",
                                label = {
                                    Text(
                                        text = it.address,
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.padding(start = 4.dp),
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.TwoTone.Apartment,
                                        contentDescription = "",
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )

                                },
                                colors = NavigationDrawerItemDefaults.colors(
                                    unselectedContainerColor = Color.Transparent,
                                    selectedContainerColor = MaterialTheme.colorScheme.primary
                                ),
                                onClick = {
                                    closeDetailScreen()
                                    navigateToDestination("$APARTMENT_SCREEN?$ADDRESS_ID=${it.addressId}")

                                }
                            )
                        }
                    }

                }
                Column(
                    modifier = Modifier
                        .layoutId(LayoutType.CONTENT)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TOP_LEVEL_DESTINATIONS.forEach { destination ->
                        NavigationDrawerItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 2.dp, top = 2.dp, end = 2.dp, bottom = 2.dp),
                            selected = selectedDestination == destination.route,
                            label = {
                                Text(
                                    text = stringResource(id = destination.iconTextId),
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    color = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = destination.selectedIcon,
                                    contentDescription = stringResource(
                                        id = destination.iconTextId
                                    ),
                                    tint = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                unselectedContainerColor = Color.Transparent,
                                selectedContainerColor = MaterialTheme.colorScheme.tertiaryContainer
                            ),
                            onClick = { navigateToDestination(destination.route) }
                        )
                    }
                }
            },
            measurePolicy = { measurables, constraints ->
                lateinit var headerMeasurable: Measurable
                lateinit var contentMeasurable: Measurable
                measurables.forEach {
                    when (it.layoutId) {
                        LayoutType.HEADER -> headerMeasurable = it
                        LayoutType.CONTENT -> contentMeasurable = it
                        else -> error("Unknown layoutId encountered!")
                    }
                }

                val headerPlaceable = headerMeasurable.measure(constraints)
                val contentPlaceable = contentMeasurable.measure(
                    constraints.offset(vertical = -headerPlaceable.height)
                )
                layout(constraints.maxWidth, constraints.maxHeight) {
                    // Place the header, this goes at the top
                    headerPlaceable.placeRelative(0, 0)

                    // Determine how much space is not taken up by the content
                    val nonContentVerticalSpace = constraints.maxHeight - contentPlaceable.height

                    val contentPlaceableY = when (navigationContentPosition) {
                        // Figure out the place we want to place the content, with respect to the
                        // parent (ignoring the header for now)
                        NavigationContentPosition.TOP -> 0
                        NavigationContentPosition.CENTER -> nonContentVerticalSpace / 4
                    }
                        // And finally, make sure we don't overlap with the header.
                        .coerceAtLeast(headerPlaceable.height)

                    contentPlaceable.placeRelative(0, contentPlaceableY)
                }
            }
        )
    }
}

@Composable
fun ModalNavigationDrawerContent(
    baseUIState: BaseUIState,
    selectedDestination: String,
    navigationContentPosition: NavigationContentPosition,
    navigateToDestination: (String) -> Unit,
    closeDetailScreen: () -> Unit = {},
    setApartment: (Int) -> Unit,
    onDrawerClicked: () -> Unit = {},

    ) {

    ModalDrawerSheet {
        Layout(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.inverseOnSurface),
//                .padding(start = 16.dp, top = 4.dp, end = 16.dp, bottom = 8.dp),
            content = {
                Column(
                    modifier = Modifier.layoutId(LayoutType.HEADER),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LogoImage()
                        IconButton(onClick = onDrawerClicked) {
                            Icon(
                                imageVector = Icons.Default.MenuOpen,
                                contentDescription = stringResource(id = R.string.navigation_drawer)
                            )
                        }
                    }
                    val apartmentLazyListState = rememberLazyListState()
                    LazyColumn(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,
                        state = apartmentLazyListState

                    ) {

                        items(items = baseUIState.apartments, key = { it.addressId }) {
                            NavigationDrawerItem(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                selected = baseUIState.selectedDestination == "$APARTMENT_SCREEN?$ADDRESS_ID={${it.addressId}}",
                                label = {
                                    Text(
                                        text = it.address,
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier.padding(start = 4.dp)
                                    )
                                },
                                icon = {
                                    Icon(
                                        imageVector = Icons.TwoTone.Apartment,
                                        contentDescription = ""
                                    )

                                },
                                colors = NavigationDrawerItemDefaults.colors(
                                    unselectedContainerColor = Color.Transparent
                                ),
                                onClick = {
                                    setApartment(it.addressId)
                                    closeDetailScreen()
                                    navigateToDestination("$APARTMENT_SCREEN?$ADDRESS_ID=${it.addressId}")
                                    onDrawerClicked()

                                }
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .layoutId(LayoutType.CONTENT)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TOP_LEVEL_DESTINATIONS.forEach { destination ->
                        NavigationDrawerItem(
                            modifier = Modifier
                                .fillMaxWidth(),
                            selected = selectedDestination == destination.route,
                            label = {
                                Text(
                                    text = stringResource(id = destination.iconTextId),
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = destination.selectedIcon,
                                    contentDescription = stringResource(
                                        id = destination.iconTextId
                                    )
                                )
                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                unselectedContainerColor = Color.Transparent
                            ),
                            onClick = {
                                navigateToDestination(destination.route)
                            }
                        )
                    }
                }
            },
            measurePolicy = { measurables, constraints ->
                lateinit var headerMeasurable: Measurable
                lateinit var contentMeasurable: Measurable
                measurables.forEach {
                    when (it.layoutId) {
                        LayoutType.HEADER -> headerMeasurable = it
                        LayoutType.CONTENT -> contentMeasurable = it
                        else -> error("Unknown layoutId encountered!")
                    }
                }

                val headerPlaceable = headerMeasurable.measure(constraints)
                val contentPlaceable = contentMeasurable.measure(
                    constraints.offset(vertical = -headerPlaceable.height)
                )
                layout(constraints.maxWidth, constraints.maxHeight) {
                    // Place the header, this goes at the top
                    headerPlaceable.placeRelative(0, 0)

                    // Determine how much space is not taken up by the content
                    val nonContentVerticalSpace = constraints.maxHeight - contentPlaceable.height

                    val contentPlaceableY = when (navigationContentPosition) {
                        // Figure out the place we want to place the content, with respect to the
                        // parent (ignoring the header for now)
                        NavigationContentPosition.TOP -> 0
                        NavigationContentPosition.CENTER -> nonContentVerticalSpace / 2
                    }
                        // And finally, make sure we don't overlap with the header.
                        .coerceAtLeast(headerPlaceable.height)

                    contentPlaceable.placeRelative(0, contentPlaceableY)
                }
            }
        )
    }
}


enum class LayoutType {
    HEADER, CONTENT
}
