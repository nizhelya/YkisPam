package com.ykis.ykispam.ui.navigation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddHome
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRailDefaults
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.android.gms.common.util.CollectionUtils
import com.ykis.ykispam.R
import com.ykis.ykispam.ui.navigation.AddApartmentScreen
import com.ykis.ykispam.ui.navigation.MeterScreen
import com.ykis.ykispam.ui.navigation.NAV_RAIL_DESTINATIONS
import com.ykis.ykispam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.ui.BaseUIState
@Preview
@Composable
private fun PreviewRail() {
    val isRailExpanded = rememberSaveable {
        mutableStateOf(false)
    }
    ApartmentNavigationRail(
        selectedDestination = MeterScreen.route,
        isRailExpanded = isRailExpanded.value,
        onMenuClick = { isRailExpanded.value = !isRailExpanded.value },
        baseUIState = BaseUIState(
            apartments = CollectionUtils.listOf(ApartmentEntity(address = "Хіміків 14/33"))
        ),
        railWidth = 80.dp
    )
}
@Preview
@Composable
private fun PreviewExpandedRail() {
    val isRailExpanded = rememberSaveable {
        mutableStateOf(true)
    }
    ApartmentNavigationRail(
        selectedDestination = MeterScreen.route,
        isRailExpanded = isRailExpanded.value,
        onMenuClick = { isRailExpanded.value = !isRailExpanded.value },
        baseUIState = BaseUIState(
            apartments = CollectionUtils.listOf(ApartmentEntity(address = "Хіміків 14/33"))
        ),
        railWidth = 260.dp
    )
}
@Composable
fun CustomNavigationRail(
    currentWidth: Dp,
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
                .width(currentWidth)
                .fillMaxSize()
                .windowInsetsPadding(windowInsets)
                .padding(vertical = 4.dp)
                .selectableGroup(),
        ) {
            if (header != null) {
                header()
                Spacer(Modifier.height(8.dp))
            }
            content()
        }
    }
}

@Composable
fun ApartmentNavigationRail(
    baseUIState: BaseUIState,
    selectedDestination: String,
    navigateToDestination: (String) -> Unit = {},
    isRailExpanded: Boolean,
    onMenuClick: () -> Unit,
    navigateToApartment : (Int) -> Unit = {},
    railWidth : Dp,
    maxApartmentListHeight : Dp = 134.dp
) {
    var showApartmentList by rememberSaveable {
        mutableStateOf(true)
    }
    val transition = updateTransition(targetState = isRailExpanded , label = "Is Rail expanded")
    val alpha by transition.animateFloat(
        label = "alpha",
        transitionSpec ={
            if(isRailExpanded) {
                tween(400 , delayMillis =100 )
            }else tween(350)
        }
    ) {
        if(it) 1f else 0f
    }
    val height by transition.animateDp(
        label = "",
        transitionSpec = {
            tween(450 , delayMillis = 100)
        }
    ) {
        if(it) maxApartmentListHeight else 0.dp
    }
    val animateItemHeight by animateDpAsState(
        targetValue = if (isRailExpanded) 56.dp else 24.dp,
        tween(550), label = ""
    )
    val animatePadding = animateDpAsState(
        targetValue = if (!isRailExpanded) 12.dp else 8.dp,
        animationSpec =
        tween(550, easing = FastOutSlowInEasing), label = ""
    )
    val rotationIcon by animateFloatAsState(
        targetValue = if(showApartmentList) 180f else 0f,
        animationSpec = spring(Spring.DampingRatioLowBouncy, Spring.StiffnessLow),
        label = "Icon rotation"
    )
    CustomNavigationRail(
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
                    navigateToDestination(AddApartmentScreen.route)
                },
                modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp)
                    .widthIn(max = 180.dp)
                    .fillMaxWidth(),
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                Row(
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = Icons.Default.AddHome,
                        contentDescription = stringResource(id = R.string.add_appartment),
                    )
                    AnimatedVisibility(
                        visible = isRailExpanded,
                        exit = shrinkHorizontally()
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
        currentWidth = railWidth
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .alpha(alpha = alpha)
            .heightIn(max = height)
        )
        {
            Row(modifier = Modifier
                .height(42.dp)
                .fillMaxWidth()
                .clickable {
                    showApartmentList = !showApartmentList
                },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 14.dp),
                    text = stringResource(id = R.string.list_apartment),
                    style = MaterialTheme.typography.titleMedium,
                )
                Icon(
                    modifier = Modifier
                        .padding(end = 14.dp)
                        .rotate(rotationIcon),
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = null,
                )
            }
            HorizontalDivider()
            AnimatedVisibility(
                visible = showApartmentList,
                enter =  expandVertically(tween(350 , easing = LinearOutSlowInEasing)),
                exit = shrinkVertically(tween(250 , easing = LinearOutSlowInEasing))
            ) {
                ApartmentList(
                    apartmentList = baseUIState.apartments,
                    onClick = navigateToApartment,
                    currentAddressId = baseUIState.addressId
                )
            }
            HorizontalDivider()
        }
        Column(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxSize()
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(animatePadding.value, Alignment.Top)
        ) {
            NAV_RAIL_DESTINATIONS.forEach { replyDestination ->
                Box {
                    Box {
                        NavigationRailItem(
                            modifier = Modifier.padding(horizontal = 28.dp),
                            selected = selectedDestination == replyDestination.route,
                            label = {
                                Text(
                                    modifier = Modifier.size(if (isRailExpanded) 0.dp else 12.dp),
                                    text = "",
                                )
                            },
                            icon = {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(animateItemHeight),
                                    horizontalArrangement = Arrangement.spacedBy(
                                        12.dp
                                    ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
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
                                            tween(durationMillis = 700)
                                        )
                                                + shrinkHorizontally(),
                                        enter = fadeIn(
                                            tween(durationMillis = 550, delayMillis = 150)
                                        )
                                    ) {
                                        Text(
                                            text = stringResource(id = replyDestination.labelId),
                                        )
                                    }
                                }
                            },
                            onClick = { navigateToDestination(replyDestination.route) }
                        )
                    }
                    androidx.compose.animation.AnimatedVisibility(
                        visible = !isRailExpanded,
                        modifier = Modifier
                            .width(80.dp)
                            .align(Alignment.CenterStart),
                        exit =
                        fadeOut(
                            tween(200)
                        )
                                + shrinkVertically(
                            tween(320)
                        ),
                        enter = fadeIn(
                            tween(300)
                        )
                                + expandVertically(
                            tween(500)
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
                                .padding(top = 32.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}