package com.ykis.ykispam.navigation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddHome
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material.icons.twotone.Apartment
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailDefaults
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import com.ykis.ykispam.BaseUIState
import com.ykis.ykispam.R
import com.ykis.ykispam.core.composable.LogoImage

@Preview
@Composable
private fun PreviewRail() {
    val isRailExpanded = rememberSaveable {
        mutableStateOf(false)
    }
    ApartmentNavigationRail(
        selectedDestination = METER_SCREEN,
        isRailExpanded = isRailExpanded.value,
        onMenuClick = { isRailExpanded.value = !isRailExpanded.value }
    )
}

@Preview
@Composable
private fun PreviewExpandedRail() {
    val isRailExpanded = rememberSaveable {
        mutableStateOf(true)
    }
    ApartmentNavigationRail(
        selectedDestination = METER_SCREEN,
        isRailExpanded = isRailExpanded.value,
        onMenuClick = { isRailExpanded.value = !isRailExpanded.value }
    )
}

@Composable
fun CustomNavigationRail(
    currentWidth : Dp,
    isRailExpanded: Boolean,
    modifier: Modifier = Modifier,
    containerColor: Color = NavigationRailDefaults.ContainerColor,
    contentColor: Color = contentColorFor(containerColor),
    header: @Composable (ColumnScope.() -> Unit)? = null,
    windowInsets: WindowInsets = NavigationRailDefaults.windowInsets,
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        color = containerColor,
        contentColor = contentColor,
        modifier = modifier,
    ) {
        Column(
            Modifier
                // TODO: customize animateContentSize
//                .animateContentSize(tween(7000))
                .width(currentWidth)
                .fillMaxSize()
                .windowInsetsPadding(windowInsets)
                .padding(vertical = 4.dp)
                .selectableGroup(),
//            horizontalAlignment = (if(isRailExpanded)Alignment.Start else Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (header != null) {
                header()
                Spacer(Modifier.height(8.dp))
            }
            content()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ApartmentNavigationRail(
//    baseUIState: BaseUIState = BaseUIState(),
    selectedDestination: String,
//    navigationContentPosition: NavigationContentPosition,
//    closeDetailScreen: () -> Unit,
    navigateToDestination: (String) -> Unit = {},
//    setApartment: (Int) -> Unit,
//    onDrawerClicked: () -> Unit,
    isRailExpanded: Boolean,
    onMenuClick: () -> Unit
) {
    val currentWidth by animateDpAsState(
        targetValue = if (isRailExpanded) 260.dp else 80.dp , tween (550), label = ""
    )
    Log.d("anim_test",currentWidth.toString())
    val animatePadding = animateDpAsState(
        targetValue = if(!isRailExpanded) 12.dp else 0.dp,
        animationSpec =  tween( 250 , easing =  FastOutSlowInEasing), label = ""
    )

    CustomNavigationRail(
        isRailExpanded = isRailExpanded,
        modifier = Modifier
            .fillMaxHeight(),
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        header = {
            IconButton(
                onClick = { onMenuClick() },
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu"
                )
            }
            FloatingActionButton(
                shape = FloatingActionButtonDefaults.smallShape,
                onClick = {
                    navigateToDestination(ADD_APARTMENT_SCREEN)
                },
                modifier = Modifier
                    .padding(start = 12.dp ,end = 12.dp)
                    .widthIn(max=180.dp)
                    .fillMaxWidth(),
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AddHome,
                        contentDescription = stringResource(id = R.string.add_appartment),
                    )
                    AnimatedVisibility(
                        visible = isRailExpanded,
                        exit = fadeOut()
                    ) {
                        Text(
                            text = stringResource(id = R.string.add_appartment),
                            modifier = Modifier
                                .padding(start = 8.dp, top = 3.dp),
                            maxLines = 1
                        )
                    }
                }
            }
        },
        currentWidth = currentWidth
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(animatePadding.value)
        ) {
            NAV_RAIL_DESTINATIONS.forEach { replyDestination ->
                Box{
                    NavigationRailItem(
                        modifier = Modifier
//                            .padding(horizontal = 12.dp)
//                            .fillMaxWidth()
                        ,
                        selected = selectedDestination == replyDestination.route,
                        label =
                        if (!isRailExpanded) {
                            {
                                Text(
                                    ""
                                )
                            }
                        } else {
                            null
                        },
                        icon = {
                            Row(
                                modifier = Modifier.width(if (isRailExpanded) 220.dp else 24.dp),
//                                modifier = Modifier.widthIn(min = 24.dp , max =220.dp).fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(
                                    12.dp,
                                    if (currentWidth == 260.dp) Alignment.Start else Alignment.CenterHorizontally
                                ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    modifier = Modifier
//                                        .padding(start = 28.dp)
                                        .size(24.dp),
                                    imageVector = if (selectedDestination == replyDestination.route) {
                                        replyDestination.selectedIcon
                                    } else replyDestination.unselectedIcon,

                                    contentDescription = stringResource(
                                        id = replyDestination.labelId
                                    )
                                )
                                AnimatedVisibility(
                                    visible = isRailExpanded,
                                    exit = fadeOut(
                                        tween(durationMillis = 400)
                                    ),
                                    enter = fadeIn(
                                        tween(durationMillis = 550 , delayMillis = 150)
                                    )

                                ) {
                                    if (isRailExpanded) {
                                        Text(
                                            text = stringResource(id = replyDestination.labelId),
                                        )
                                    }
                                    }
                                }
                        },
                        onClick = { navigateToDestination(replyDestination.route) }
                    )
                    androidx.compose.animation.AnimatedVisibility(
                        visible = !isRailExpanded,
                        modifier = Modifier
                            .align(Alignment.Center),
                        exit = fadeOut(
                            tween(250)
                        ),
                        enter = fadeIn(
                            tween(550)
                        )

                    ) {
                        Text(
                            style = MaterialTheme.typography.labelMedium,
                            color =
                            if (selectedDestination == replyDestination.route) {
                                NavigationRailItemDefaults.colors().selectedTextColor
                            } else NavigationRailItemDefaults.colors().unselectedIconColor,
                            text = stringResource(id = replyDestination.labelId),
                            modifier = Modifier
                                .padding(top = 32.dp)
                        )
                    }

                }

            }
        }

    }
}

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
//                        navigateToDestination(destination.route)
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

@Composable
fun PermanentNavigationDrawerContent(
    baseUIState: BaseUIState,
    selectedDestination: String,
    navigationContentPosition: NavigationContentPosition,
    navigateToDestination: (String) -> Unit,
    closeDetailScreen: () -> Unit,
) {
    PermanentDrawerSheet(modifier = Modifier.sizeIn(minWidth = 200.dp, maxWidth = 300.dp)) {
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
                    NAV_RAIL_DESTINATIONS.forEach { destination ->
                        NavigationDrawerItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 2.dp, top = 2.dp, end = 2.dp, bottom = 2.dp),
                            selected = selectedDestination == destination.route,
                            label = {
                                Text(
                                    text = stringResource(id = destination.labelId),
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    color = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = destination.selectedIcon,
                                    contentDescription = stringResource(
                                        id = destination.labelId
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
                    // TODO: remove lazyliststate
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
                    NAV_RAIL_DESTINATIONS.forEach { destination ->
                        NavigationDrawerItem(
                            modifier = Modifier
                                .fillMaxWidth(),
                            selected = selectedDestination == destination.route,
                            label = {
                                Text(
                                    text = stringResource(id = destination.labelId),
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )
                            },
                            icon = {
                                Icon(
                                    imageVector = destination.selectedIcon,
                                    contentDescription = stringResource(
                                        id = destination.labelId
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
