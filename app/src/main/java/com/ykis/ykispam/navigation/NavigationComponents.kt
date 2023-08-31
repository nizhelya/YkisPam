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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddHome
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ykis.ykispam.BaseUIState
import com.ykis.ykispam.R
import com.ykis.ykispam.SplashViewModel
import com.ykis.ykispam.core.composable.LogoImage
import com.ykis.ykispam.pam.screens.appartment.ApartmentViewModel

@Composable
fun ApartmentNavigationRail(
    baseUIState: BaseUIState,
    selectedDestination: String,
    navigationContentPosition: NavigationContentPosition,
    navigateToDestination: (String) -> Unit,
    onDrawerClicked: () -> Unit = {},
//    viewModel: ApartmentViewModel = hiltViewModel()

) {
//    val apartments by viewModel.apartments.observeAsState(emptyList())
//    val uiState by  viewModel.uiState.collectAsStateWithLifecycle()

    NavigationRail(
        modifier = Modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
    ) {
        // TODO remove custom nav rail positioning when NavRail component supports it. ticket : b/232495216
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
                    FloatingActionButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.padding(top = 8.dp, bottom = 32.dp),
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(id = R.string.add_appartment),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(Modifier.height(4.dp)) // NavigationRailVerticalPadding
                }


                    LazyColumn(
                        modifier = Modifier
                            .layoutId(LayoutType.CONTENT),
//                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {

                        items(items = baseUIState.apartments, key =  {it.addressId} ) {
                            NavigationDrawerItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, top = 2.dp, end = 8.dp, bottom = 2.dp),
                                selected = selectedDestination == "$EXIT_SCREEN?$ADDRESS_ID=${it.addressId}",
                                label = {
                                    Text(
                                        text = it.address,
                                        style = MaterialTheme.typography.titleLarge,
                                        modifier = Modifier.padding(horizontal = 4.dp)
                                    )
                                },
//                                icon = {
//                                    Icon(
//                                        imageVector = Icons.TwoTone.Apartment,
//                                        contentDescription = ""
//                                    )
//
//                                },
                                colors = NavigationDrawerItemDefaults.colors(
                                    unselectedContainerColor = Color.Transparent
                                ),
                                onClick = {
                                    navigateToDestination("$EXIT_SCREEN?$ADDRESS_ID=${it.addressId}")
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

@Composable
fun BottomNavigationBar(
    selectedDestination: String,
    navigateToDestination: (String) -> Unit
) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        TOP_LEVEL_DESTINATIONS.forEach { destination ->
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermanentNavigationDrawerContent(
    baseUIState: BaseUIState,
    selectedDestination: String,
    navigationContentPosition: NavigationContentPosition,
    navigateToDestination: (String) -> Unit,
//    viewModel: ApartmentViewModel = hiltViewModel()

) {
//    val apartments by viewModel.apartments.observeAsState(emptyList())

    PermanentDrawerSheet(modifier = Modifier.sizeIn(minWidth = 200.dp, maxWidth = 300.dp)) {
        // TODO remove custom nav drawer content positioning when NavDrawer component supports it. ticket : b/232495216
        Layout(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .padding(16.dp),
            content = {
                Column(
                    modifier = Modifier
                        .layoutId(LayoutType.HEADER),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        text = stringResource(id = R.string.app_name).uppercase(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    ExtendedFloatingActionButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 40.dp),
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(id = R.string.edit),
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.compose),
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .layoutId(LayoutType.CONTENT),
//                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {

                    items(items = baseUIState.apartments, key = { it.addressId }) {
                        NavigationDrawerItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 2.dp, end = 8.dp, bottom = 2.dp),
                            selected = selectedDestination == "$EXIT_SCREEN?$ADDRESS_ID={${it.addressId}}",
                            label = {
                                Text(
                                    text = it.address,
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                            },
//                            icon = {
//                                Icon(
//                                    imageVector = Icons.TwoTone.Apartment,
//                                    contentDescription = ""
//                                )
//
//                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                unselectedContainerColor = Color.Transparent
                            ),
                            onClick = {
                                navigateToDestination("$EXIT_SCREEN?$ADDRESS_ID={${it.addressId}}")
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
    onDrawerClicked: () -> Unit = {},
    viewModel: ApartmentViewModel = hiltViewModel()

) {

    ModalDrawerSheet {

        // TODO remove custom nav drawer content positioning when NavDrawer component supports it. ticket : b/232495216
        Layout(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.inverseOnSurface),
//                .padding(start = 16.dp, top = 4.dp, end = 16.dp, bottom = 8.dp),
            content = {
                Column(
                    modifier = Modifier
                        .layoutId(LayoutType.HEADER),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 4.dp, end = 16.dp, bottom = 8.dp),
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 4.dp, end = 16.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(viewModel.photoUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            error = painterResource(R.drawable.ic_account_circle),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .width(48.dp)
                                .height(48.dp)
                                .clickable(onClick = {
                                    navigateToDestination(PROFILE_SCREEN)
                                    onDrawerClicked()

                                })
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 16.dp,
                                    top = 4.dp,
                                    end = 16.dp,
                                    bottom = 8.dp
                                ),

                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.Start
                        ) {


                            (if (viewModel.email.isNullOrBlank()) {
                                stringResource(id = R.string.email_placeholder)
                            } else {
                                viewModel.email
                            }).let {
                                if (it != null) {
                                    Text(
                                        text = it,
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                        }
                    }

                    ExtendedFloatingActionButton(
                        onClick = {
                            navigateToDestination(ADD_APARTMENT_SCREEN)
                            onDrawerClicked()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddHome,
                            contentDescription = stringResource(id = R.string.add_appartment),
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.add_appartment),
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                val apartments by viewModel.apartments.observeAsState(emptyList())
                val apartmentLazyListState = rememberLazyListState()

                LazyColumn(
                    modifier = Modifier
                        .layoutId(LayoutType.CONTENT),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    state = apartmentLazyListState

                ) {

                    items(items = apartments, key = { it.addressId }) {
                        NavigationDrawerItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, top = 0.dp, end = 8.dp, bottom = 0.dp),
                            selected = selectedDestination == "$EXIT_SCREEN?$ADDRESS_ID={${it.addressId}}",
                            label = {
                                Text(
                                    text = it.address,
                                    style = MaterialTheme.typography.titleLarge,
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                )
                            },
//                            icon = {
//                                Icon(
//                                    imageVector = Icons.TwoTone.Apartment,
//                                    contentDescription = ""
//                                )
//
//                            },
                            colors = NavigationDrawerItemDefaults.colors(
                                unselectedContainerColor = Color.Transparent
                            ),
                            onClick = {
                                navigateToDestination("$EXIT_SCREEN?$ADDRESS_ID=${it.addressId}")
                                onDrawerClicked()

                            }
                        )
                    }
//                    apartments.forEach { it ->
//                        NavigationDrawerItem(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(start = 8.dp, top = 2.dp, end = 8.dp, bottom = 2.dp),
//                            selected = selectedDestination == "$APARTMENT_SCREEN?$ADDRESS_ID={${it.addressId}}",
//                            label = {
//                                Text(
//                                    text = it.address,
//                                    style = MaterialTheme.typography.titleLarge,
//                                    modifier = Modifier.padding(horizontal = 4.dp)
//                                )
//                            },
//                            icon = {
//                                Icon(
//                                    imageVector = Icons.TwoTone.Apartment,
//                                    contentDescription = ""
//                                )
//
//                            },
//                            colors = NavigationDrawerItemDefaults.colors(
//                                unselectedContainerColor = Color.Transparent
//                            ),
//                            onClick = {
//                                navigateToDestination("$APARTMENT_SCREEN?$ADDRESS_ID={${it.addressId}}")
//                            }
//                        )
//                    }
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
