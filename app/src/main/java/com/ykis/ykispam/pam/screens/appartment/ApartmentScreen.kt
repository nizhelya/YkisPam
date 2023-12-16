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
import androidx.compose.material.icons.twotone.FamilyRestroom
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
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
import kotlinx.coroutines.launch
import com.ykis.ykispam.R.string as AppText


private const val LOAD_TIMEOUT = 1000L

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent(
    modifier: Modifier = Modifier,
    drawerState: DrawerState? = null,
    deleteApartment: () -> Unit,
    appState: YkisPamAppState,
    baseUIState: BaseUIState,
    navigationType: NavigationType,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: NavigationContentPosition,
    navController: NavHostController,
    selectedDestination: String,
    navigateToDestination: (String) -> Unit,
    onDrawerClicked: () -> Unit = {},
    ) {
    var openMenu by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.inverseOnSurface,
        topBar = {
                ApartmentTopAppBar(
                    appState,
                    baseUIState,
                    isDriverClicked = true,
                    isButtonAction = baseUIState.apartments.isNotEmpty(),
                    onButtonPressed = { onDrawerClicked() },
                    onButtonAction = { deleteApartment() }
                )
        },

        ) { it ->
        var showDialog by rememberSaveable { mutableStateOf(false) }

        Row(
            modifier = Modifier
                .padding(it)
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
                                .padding(PaddingValues(20.dp))
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (baseUIState.apartments.isEmpty()) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = stringResource(id = AppText.add_appartment_info),
                                        modifier = Modifier.padding(PaddingValues(8.dp)),
                                        textAlign = TextAlign.Center,
                                    )

                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = stringResource(id = AppText.no_data_subtitle),
                                        modifier = Modifier.padding(PaddingValues(top = 20.dp)),
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            } else {
                                MenuItem(
                                    imageVector = ImageVector.vectorResource(R.drawable.twotone_water_damage_24),
                                    serviseName = stringResource(id = R.string.kvartplata_text),
                                    screen = BTI_SCREEN,
                                    baseUIState = baseUIState,
                                    dolg = 65.45,
                                    org = baseUIState.apartments.first().osbb,
                                    navigateToDestination = navigateToDestination,

                                    )
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
                            if (baseUIState.apartments.isEmpty()) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = stringResource(id = AppText.add_appartment_info),
                                        modifier = Modifier.padding(PaddingValues(8.dp)),
                                        textAlign = TextAlign.Center,
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = stringResource(id = AppText.no_data_subtitle),
                                        modifier = Modifier.padding(PaddingValues(top = 20.dp)),
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            } else {
                                MenuItem(
                                    imageVector = ImageVector.vectorResource(R.drawable.twotone_water_damage_24),
                                    serviseName = stringResource(id = R.string.kvartplata_text),
                                    baseUIState = baseUIState,
                                    screen = BTI_SCREEN,
                                    dolg = 65.45,
                                    org = baseUIState.apartments.first().osbb,
                                    navigateToDestination = navigateToDestination,
                                )
                            }
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
//                        .weight(weight = 1f, fill = false)
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.inverseOnSurface),

                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .weight(1f),

                                        )
                                    IconButton(
                                        onClick = { showDialog = true },
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.primaryContainer)
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
                                    org = baseUIState.apartments.first().osbb,
                                    navigateToDestination = navigateToDestination,
                                )
                                MenuItem(
                                    imageVector = Icons.TwoTone.FamilyRestroom,
                                    serviseName = stringResource(id = R.string.list_family),
                                    baseUIState = baseUIState,
                                    screen = FAMILY_SCREEN,
                                    dolg = 65.45,
                                    org = stringResource(id = R.string.vodokanal_colon),
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
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .weight(1f),

                                        )
                                    IconButton(
                                        onClick = { showDialog = true },
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.surface)
                                    ) {
                                        Icon(
                                            imageVector = Icons.TwoTone.Info,
                                            contentDescription = "Info",
                                            tint = MaterialTheme.colorScheme.outline
                                        )
                                    }

                                }
                                MenuItem(
                                    imageVector = ImageVector.vectorResource(R.drawable.twotone_water_damage_24),
                                    serviseName = stringResource(id = R.string.kvartplata_text),
                                    baseUIState = baseUIState,
                                    screen = BTI_SCREEN,
                                    dolg = 65.45,
                                    org = baseUIState.apartments.first().osbb,
                                    navigateToDestination = navigateToDestination,
                                )
                                MenuItem(
                                    imageVector = ImageVector.vectorResource(R.drawable.twotone_water_damage_24),
                                    serviseName = stringResource(id = R.string.water_drainage),
                                    baseUIState = baseUIState,
                                    screen = BTI_SCREEN,
                                    dolg = 65.45,
                                    org = stringResource(id = R.string.vodokanal_colon),
                                    navigateToDestination = navigateToDestination,
                                )
                                MenuItem(
                                    imageVector = ImageVector.vectorResource(R.drawable.twotone_water_damage_24),
                                    serviseName = stringResource(id = R.string.heat_supply),
                                    baseUIState = baseUIState,
                                    screen = BTI_SCREEN,
                                    dolg = 65.45,
                                    org = stringResource(id = R.string.ytke_colon),
                                    navigateToDestination = navigateToDestination,
                                )

                                MenuItem(
                                    imageVector = ImageVector.vectorResource(R.drawable.twotone_water_damage_24),
                                    serviseName = stringResource(id = R.string.payment_list),
                                    baseUIState = baseUIState,
                                    screen = BTI_SCREEN,
                                    dolg = 65.45,
                                    org = stringResource(id = R.string.ytke_colon),
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
    }
}


