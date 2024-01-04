package com.ykis.ykispam.pam.screens.appartment

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddHome
import androidx.compose.material.icons.filled.HotTub
import androidx.compose.material.icons.filled.Water
import androidx.compose.material.icons.twotone.Commute
import androidx.compose.material.icons.twotone.CorporateFare
import androidx.compose.material.icons.twotone.FamilyRestroom
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material.icons.twotone.MonetizationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.ykis.ykispam.BaseUIState
import com.ykis.ykispam.R
import com.ykis.ykispam.YkisPamAppState
import com.ykis.ykispam.core.composable.HelpAlertCard
import com.ykis.ykispam.navigation.ADD_APARTMENT_SCREEN
import com.ykis.ykispam.navigation.ApartmentNavigationRail
import com.ykis.ykispam.navigation.BTI_SCREEN
import com.ykis.ykispam.navigation.ContentType
import com.ykis.ykispam.navigation.FAMILY_SCREEN
import com.ykis.ykispam.navigation.ModalNavigationDrawerContent
import com.ykis.ykispam.navigation.NavigationContentPosition
import com.ykis.ykispam.navigation.NavigationType
import com.ykis.ykispam.navigation.PermanentNavigationDrawerContent
import com.ykis.ykispam.navigation.SERVICE_DETAIL_SCREEN
import com.ykis.ykispam.pam.screens.bti.BtiPanel
import kotlinx.coroutines.launch
import com.ykis.ykispam.R.string as AppText

@Composable
fun ApartmentScreen(
    restartApp: (String) -> Unit,
    addressId: String,
    appState: YkisPamAppState,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    navigationType: NavigationType,
    navigationContentPosition: NavigationContentPosition,
    viewModel: ApartmentViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val drawerState = DrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = appState.coroutineScope
    val navController = appState.navController
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination = navBackStackEntry?.destination?.route ?: uiState.selectedDestination

    viewModel.initialize(addressId.toInt())

    if (navigationType == NavigationType.PERMANENT_NAVIGATION_DRAWER) {
        PermanentNavigationDrawer(drawerContent = {
            PermanentNavigationDrawerContent(
                baseUIState = uiState,
                selectedDestination = selectedDestination,
                navigationContentPosition = navigationContentPosition,
                navigateToDestination = appState::navigateTo,
            )
        })
        {
            AppContent(
                deleteApartment = {
                    viewModel.deleteApartment(
                        uiState.addressId,
                        restartApp
                    )
                },
                appState = appState,
                baseUIState = uiState,
                navigationType = navigationType,
                contentType = contentType,
                displayFeatures = displayFeatures,
                navigationContentPosition = navigationContentPosition,
                navController = navController,
                selectedDestination = selectedDestination,
                navigateToDestination = appState::navigateTo,
            )
        }
    } else {

        ModalNavigationDrawer(
            drawerContent = {
                ModalNavigationDrawerContent(
                    baseUIState = uiState,
                    selectedDestination = selectedDestination,
                    navigationContentPosition = navigationContentPosition,
                    navigateToDestination = appState::navigateTo,
                    onDrawerClicked = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }
                )
            },
            drawerState = drawerState
        ) {
            AppContent(
                deleteApartment = {
                    viewModel.deleteApartment(
                        uiState.addressId,
                        restartApp
                    )
                },
                appState = appState,
                baseUIState = uiState,
                navigationType = navigationType,
                contentType = contentType,
                displayFeatures = displayFeatures,
                navigationContentPosition = navigationContentPosition,
                navController = navController,
                selectedDestination = selectedDestination,
                navigateToDestination = appState::navigateTo,
            ) {
                coroutineScope.launch {
                    drawerState.open()
                }
            }
        }
    }
}

@Composable
fun AppContent(
    modifier: Modifier = Modifier,
    deleteApartment: () -> Unit,
    appState: YkisPamAppState,
    baseUIState: BaseUIState,
    navigationType: NavigationType,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: NavigationContentPosition,
    navController: NavController,
    selectedDestination: String,
    navigateToDestination: (String) -> Unit,
    onDrawerClicked: () -> Unit = {},
) {

//    Scaffold(
//        containerColor = MaterialTheme.colorScheme.inverseOnSurface,
//        topBar = {
//                ApartmentTopAppBar(
//                    appState,
//                    baseUIState,
//                    isDriverClicked = true,
//                    isButtonAction = baseUIState.apartments.isNotEmpty(),
//                    onButtonPressed = { onDrawerClicked() },
//                    onButtonAction = { deleteApartment() }
//                )
//        },
//
//        ) { it ->
    var showDialog by rememberSaveable { mutableStateOf(false) }
    val yes:Byte = 1
    Row(
        modifier = Modifier
//                .padding(it)
            .fillMaxSize()
    ) {
        AnimatedVisibility(visible = navigationType == NavigationType.NAVIGATION_RAIL) {
            ApartmentNavigationRail(
                baseUIState = baseUIState,
                selectedDestination = selectedDestination,
                navigationContentPosition = navigationContentPosition,
                navigateToDestination = navigateToDestination,
                onDrawerClicked = onDrawerClicked,
            )
        }

        if (contentType == ContentType.DUAL_PANE) {
            TwoPane(
                first = {
                    Column(
                        modifier = Modifier
                            .padding(PaddingValues(4.dp))
                            .verticalScroll(rememberScrollState())
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(MaterialTheme.colorScheme.inverseOnSurface),

                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ApartmentTopAppBar(
                            appState,
                            baseUIState,
                            isDriverClicked = true,
                            isButtonAction = baseUIState.apartments.isNotEmpty(),
                            onButtonPressed = { onDrawerClicked() },
                            onButtonAction = { deleteApartment() }
                        )

                        if (baseUIState.apartments.isEmpty()) {
                            Box(modifier = modifier.fillMaxSize()) {
                                // When we have bottom navigation we show FAB at the bottom end.
                                if (navigationType == NavigationType.BOTTOM_NAVIGATION) {
                                    LargeFloatingActionButton(
                                        onClick = {
                                            navigateToDestination(ADD_APARTMENT_SCREEN)
                                        },
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .align(Alignment.BottomEnd),
                                        containerColor = MaterialTheme.colorScheme.inverseOnSurface,
                                        contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AddHome,
                                            contentDescription = stringResource(id = R.string.add_appartment),
                                            modifier = Modifier.size(28.dp)
                                        )

                                    }

                                }
                            }
                        } else {
                            Card(
                                modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)

                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = stringResource(id = AppText.xp),
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.secondary,
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .weight(1f),

                                            )
                                        IconButton(
                                            onClick = { showDialog = true },
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .clip(CircleShape)
                                                .background(MaterialTheme.colorScheme.inverseOnSurface)
                                        ) {
                                            Icon(
                                                imageVector = Icons.TwoTone.Info,
                                                contentDescription = "Info",
                                                tint = MaterialTheme.colorScheme.outline
                                            )
                                        }

                                    }
                                    MenuItem(
                                        imageVector = Icons.TwoTone.Home,
                                        serviseName = stringResource(id = R.string.bti),
                                        baseUIState = baseUIState,
                                            screen = BTI_SCREEN,
                                        dolg = 65.45,
                                        serviceName = baseUIState.apartments.first().osbb,
                                        navigateToDestination = navigateToDestination,
                                    )
                                    MenuItem(
                                        imageVector = Icons.TwoTone.FamilyRestroom,
                                        serviseName = stringResource(id = R.string.list_family),
                                        baseUIState = baseUIState,
                                        screen = FAMILY_SCREEN,
                                        dolg = 65.45,
                                        serviceName = stringResource(id = R.string.vodokanal_colon),
                                        navigateToDestination = navigateToDestination,
                                    )

                                    if (showDialog) {
                                        HelpAlertCard(
                                            title = stringResource(id = R.string.consumed_services),
                                            text = stringResource(id = R.string.consumed_services),
                                            org = "",
                                            showDialog = true,
                                            onShowDialogChange = { showDialog = it }
                                        )
                                    }
                                }
                            }
                            Card(
                                modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = stringResource(id = AppText.consumed_services),
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.secondary,
                                            modifier = Modifier
                                                .padding(16.dp)
                                                .weight(1f),

                                            )
                                        IconButton(
                                            onClick = { showDialog = true },
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .clip(CircleShape)
                                                .background(MaterialTheme.colorScheme.inverseOnSurface)
                                        ) {
                                            Icon(
                                                imageVector = Icons.TwoTone.Info,
                                                contentDescription = "Info",
                                                tint = MaterialTheme.colorScheme.outline
                                            )
                                        }

                                    }
                                    if(baseUIState.apartment.kvartplata == yes ) {
                                        MenuItem(
                                            imageVector = Icons.TwoTone.CorporateFare,
                                            serviseName = baseUIState.apartment.osbb,

                                            baseUIState = baseUIState,
                                            screen = SERVICE_DETAIL_SCREEN,
                                            dolg = 65.45,
                                            service = 1,
                                            serviceName = baseUIState.apartment.osbb,
                                            navigateToDestination = navigateToDestination,
                                        )
                                    }
                                    if(baseUIState.apartment.voda == yes || baseUIState.apartment.stoki == yes) {

                                        MenuItem(
                                            imageVector = Icons.Default.Water,
                                            serviseName = stringResource(id = R.string.vodokanal),
                                            baseUIState = baseUIState,
                                            screen = SERVICE_DETAIL_SCREEN,
                                            dolg = 65.45,
                                            service = 2,
                                            serviceName = stringResource(id = R.string.vodokanal),
                                            navigateToDestination = navigateToDestination,
                                        )
                                    }
                                    if(baseUIState.apartment.otoplenie == yes ) {

                                        MenuItem(
                                            imageVector = Icons.Default.HotTub,
                                            serviseName = stringResource(id = R.string.ytke),
                                            baseUIState = baseUIState,
                                            screen = SERVICE_DETAIL_SCREEN,
                                            dolg = 65.45,
                                            service = 3,
                                            serviceName = stringResource(id = R.string.ytke),
                                            navigateToDestination = navigateToDestination,
                                        )
                                    }
                                    if(baseUIState.apartment.tbo == yes ) {

                                        MenuItem(
                                            imageVector = Icons.TwoTone.Commute,
                                            serviseName = stringResource(id = R.string.yzhtrans),
                                            baseUIState = baseUIState,
                                            screen = SERVICE_DETAIL_SCREEN,
                                            service = 4,
                                            serviceName = stringResource(id = R.string.yzhtrans),
                                            navigateToDestination = navigateToDestination,
                                        )
                                    }
                                    MenuItem(
                                        imageVector = Icons.TwoTone.MonetizationOn,
                                        serviseName = stringResource(id = R.string.payment_list),
                                        baseUIState = baseUIState,
                                        screen = SERVICE_DETAIL_SCREEN,
                                        dolg = 65.45,
                                        serviceName = stringResource(id = R.string.payment_list),
                                        navigateToDestination = navigateToDestination,
                                    )
                                    if (showDialog) {
                                        HelpAlertCard(
                                            title = stringResource(id = R.string.consumed_services),
                                            text = stringResource(id = R.string.consumed_services),
                                            org = "",
                                            showDialog = true,
                                            onShowDialogChange = { showDialog = it }
                                        )
                                    }
                                }
                            }
                        }
                    }
                },
                second = {
                    Column(
                        modifier = Modifier
                            .padding(PaddingValues(20.dp))
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BtiPanel( baseUIState = baseUIState)

                    }
                },
                strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f, gapWidth = 16.dp),
                displayFeatures = displayFeatures
            )
        } else {
            Column(
                modifier = Modifier
                    .padding(PaddingValues(4.dp))
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.inverseOnSurface),

                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ApartmentTopAppBar(
                    appState,
                    baseUIState,
                    isDriverClicked = true,
                    isButtonAction = baseUIState.apartments.isNotEmpty(),
                    onButtonPressed = { onDrawerClicked() },
                    onButtonAction = { deleteApartment() }
                )
                if (baseUIState.apartments.isEmpty()) {
                    Box(modifier = modifier.fillMaxSize()) {
                        // When we have bottom navigation we show FAB at the bottom end.
                        if (navigationType == NavigationType.BOTTOM_NAVIGATION) {
                            LargeFloatingActionButton(
                                onClick = {
                                    navigateToDestination(ADD_APARTMENT_SCREEN)
                                },
                                modifier = Modifier
                                    .padding(16.dp)
                                    .align(Alignment.BottomEnd),
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,

                            ) {
                                Icon(
                                    imageVector = Icons.Default.AddHome,
                                    contentDescription = stringResource(id = R.string.add_appartment),
                                    modifier = Modifier.size(28.dp)
                                )

                            }

                        }
                    }
                } else {
                    Card(
                        modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)

                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(id = AppText.xp),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .weight(1f),

                                    )
                                IconButton(
                                    onClick = { showDialog = true },
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.inverseOnSurface)
                                ) {
                                    Icon(
                                        imageVector = Icons.TwoTone.Info,
                                        contentDescription = "Info",
                                        tint = MaterialTheme.colorScheme.outline
                                    )
                                }

                            }
                            MenuItem(
                                imageVector = Icons.TwoTone.Home,
                                serviseName = stringResource(id = R.string.bti),
                                baseUIState = baseUIState,
                                screen = BTI_SCREEN,
                                dolg = 65.45,
                                serviceName = baseUIState.apartments.first().osbb,
                                navigateToDestination = navigateToDestination,
                            )
                            MenuItem(
                                imageVector = Icons.TwoTone.FamilyRestroom,
                                serviseName = stringResource(id = R.string.list_family),
                                baseUIState = baseUIState,
                                screen = FAMILY_SCREEN,
                                dolg = 65.45,
                                serviceName = stringResource(id = R.string.vodokanal_colon),
                                navigateToDestination = navigateToDestination,
                            )

                            if (showDialog) {
                                HelpAlertCard(
                                    title = stringResource(id = R.string.consumed_services),
                                    text = stringResource(id = R.string.consumed_services),
                                    org = "",
                                    showDialog = true,
                                    onShowDialogChange = { showDialog = it }
                                )
                            }
                        }
                    }
                    Card(
                        modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(id = AppText.consumed_services),
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .weight(1f),

                                    )
                                IconButton(
                                    onClick = { showDialog = true },
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.inverseOnSurface)
                                ) {
                                    Icon(
                                        imageVector = Icons.TwoTone.Info,
                                        contentDescription = "Info",
                                        tint = MaterialTheme.colorScheme.outline
                                    )
                                }

                            }
                            if(baseUIState.apartment.kvartplata == yes ) {
                                MenuItem(
                                    imageVector = Icons.TwoTone.CorporateFare,
                                    serviseName = baseUIState.apartment.osbb,

                                    baseUIState = baseUIState,
                                    screen = SERVICE_DETAIL_SCREEN,
                                    service = 1,
                                    serviceName = baseUIState.apartment.osbb,
                                    navigateToDestination = navigateToDestination,
                                )
                            }
                            if(baseUIState.apartment.voda == yes || baseUIState.apartment.stoki == yes) {

                                MenuItem(
                                    imageVector = Icons.Default.Water,
                                    serviseName = stringResource(id = R.string.vodokanal),
                                    baseUIState = baseUIState,
                                    screen = SERVICE_DETAIL_SCREEN,
                                    service = 2,
                                    dolg = 65.45,
                                    serviceName = stringResource(id = R.string.vodokanal),
                                    navigateToDestination = navigateToDestination,
                                )
                            }
                            if(baseUIState.apartment.otoplenie == yes ) {

                                MenuItem(
                                    imageVector = Icons.Default.HotTub,
                                    serviseName = stringResource(id = R.string.ytke),
                                    baseUIState = baseUIState,
                                    screen = SERVICE_DETAIL_SCREEN,
                                    service = 3,
                                    dolg = 65.45,
                                    serviceName = stringResource(id = R.string.ytke),
                                    navigateToDestination = navigateToDestination,
                                )
                            }
                            if(baseUIState.apartment.tbo == yes ) {

                                MenuItem(
                                    imageVector = Icons.TwoTone.Commute,
                                    serviseName = stringResource(id = R.string.yzhtrans),
                                    baseUIState = baseUIState,
                                    screen = SERVICE_DETAIL_SCREEN,
                                    service = 4,
                                    dolg = 65.45,
                                    serviceName = stringResource(id = R.string.yzhtrans),
                                    navigateToDestination = navigateToDestination,
                                )
                            }
                            MenuItem(
                                imageVector = Icons.TwoTone.MonetizationOn,
                                serviseName = stringResource(id = R.string.payment_list),
                                baseUIState = baseUIState,
                                screen = BTI_SCREEN,
                                dolg = 65.45,
                                serviceName = stringResource(id = R.string.payment_list),
                                navigateToDestination = navigateToDestination,
                            )
                            if (showDialog) {
                                HelpAlertCard(
                                    title = stringResource(id = R.string.consumed_services),
                                    text = stringResource(id = R.string.consumed_services),
                                    org = "",
                                    showDialog = true,
                                    onShowDialogChange = { showDialog = it }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
//    }
}