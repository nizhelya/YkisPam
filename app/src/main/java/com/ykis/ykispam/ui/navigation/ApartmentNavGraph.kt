package com.ykis.ykispam.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.YkisPamAppState
import com.ykis.ykispam.ui.navigation.components.ApartmentNavigationRail
import com.ykis.ykispam.ui.navigation.components.BottomNavigationBar
import com.ykis.ykispam.ui.navigation.components.ModalNavigationDrawerContent
import com.ykis.ykispam.ui.rememberAppState
import com.ykis.ykispam.ui.screens.EmptyScreen
import com.ykis.ykispam.ui.screens.appartment.AddApartmentScreenContent
import com.ykis.ykispam.ui.screens.appartment.ApartmentScreen
import com.ykis.ykispam.ui.screens.appartment.ApartmentViewModel
import com.ykis.ykispam.ui.screens.appartment.InfoApartmentScreen
import com.ykis.ykispam.ui.screens.meter.MeterScreen
import com.ykis.ykispam.ui.screens.profile.ProfileScreen
import com.ykis.ykispam.ui.screens.service.MainServiceScreen
import com.ykis.ykispam.ui.screens.settings.SettingsScreen
import kotlinx.coroutines.launch

@Composable
fun MainApartmentScreen(
    contentType: ContentType,
    navigationType: NavigationType,
    displayFeatures: List<DisplayFeature>,
    navController: NavHostController = rememberNavController(),
    viewModel: ApartmentViewModel = hiltViewModel(),
    rootNavController: NavHostController,
    appState: YkisPamAppState,
    onLaunch: () -> Unit,
    onDispose: () -> Unit,
    isRailExpanded: Boolean,
    onMenuClick: () -> Unit
    ) {
    val baseUIState by viewModel.uiState.collectAsStateWithLifecycle()
    val drawerState = DrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination =
        navBackStackEntry?.destination?.route ?: InfoApartmentScreen.routeWithArgs
    val railWidth by animateDpAsState(
        targetValue = if (isRailExpanded) 260.dp else 80.dp, tween(550), label = ""
    )
    DisposableEffect(key1 = Unit) {
        onLaunch()
        viewModel.initialize()
        onDispose {
            onDispose()
        }
    }

        if (navigationType == NavigationType.BOTTOM_NAVIGATION) {
            ModalNavigationDrawer(
                drawerContent = {
                    ModalNavigationDrawerContent(
                        baseUIState = baseUIState,
                        selectedDestination = selectedDestination,
                        navigateToDestination = {
                            coroutineScope.launch {
                                drawerState.close()
                                navController.navigateWithPopUp(it, InfoApartmentScreen.route)
                            }
                        },
                        onMenuClick = {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                        },
                        navigateToApartment = { addressId ->
                            coroutineScope.launch {
                                drawerState.close()
                                navController.navigateToInfoApartment(addressId)
                            }

                        },
                        isApartmentsEmpty = baseUIState.apartments.isEmpty()
                    )
                },
                drawerState = drawerState
            ) {
                Scaffold(
                    snackbarHost = { appState.snackbarHostState },
                    bottomBar = {
                        if(baseUIState.apartments.isNotEmpty()) {
                            BottomNavigationBar(
                                selectedDestination = selectedDestination,
                                onClick = { navController.navigateWithPopUp(it , InfoApartmentScreen.route) }
                            )
                        }
                    }
                ) {
                    ApartmentNavGraph(
                        modifier = Modifier
                            .padding(
                                bottom = it.calculateBottomPadding(),
                            ),
                        contentType = contentType,
                        navigationType = navigationType,
                        displayFeatures = displayFeatures,
                        baseUIState = baseUIState,
                        navController = navController,
                        onDrawerClicked = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        },
                        apartmentViewModel = viewModel,
                        rootNavController = rootNavController,
                        firstDestination =  if(baseUIState.apartments.isNotEmpty()) InfoApartmentScreen.routeWithArgs else AddApartmentScreen.route

                    )
                }
            }
        } else {
            Scaffold(
                snackbarHost = { appState.snackbarHostState },
            ) { it ->
                ApartmentNavGraph(
                    modifier = Modifier
                        .padding(
                            start = railWidth,
                            bottom = it.calculateBottomPadding(),
                        ),
                    contentType = contentType,
                    navigationType = navigationType,
                    displayFeatures = displayFeatures,
                    baseUIState = baseUIState,
                    navController = navController,
                    onDrawerClicked = {
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    },
                    apartmentViewModel = viewModel,
                    rootNavController = rootNavController,
                        firstDestination = if(baseUIState.apartments.isNotEmpty()) InfoApartmentScreen.routeWithArgs else AddApartmentScreen.route

                )
                ApartmentNavigationRail(
                    selectedDestination = selectedDestination,
                    navigateToDestination = {
                        navController.navigateWithPopUp(
                            it,
                            InfoApartmentScreen.route
                        )
                    },
                    isRailExpanded = isRailExpanded,
                    onMenuClick = onMenuClick,
                    baseUIState = baseUIState,
                    navigateToApartment = { addressId ->
                        navController.navigateToInfoApartment(addressId)
                    },
                    railWidth = railWidth,
                    isApartmentsEmpty = baseUIState.apartments.isEmpty()
                )
            }
        }
}

@Composable
fun ApartmentNavGraph(
    modifier: Modifier = Modifier,
    contentType: ContentType,
    navigationType: NavigationType,
    displayFeatures: List<DisplayFeature>,
    baseUIState: BaseUIState,
    onDrawerClicked: () -> Unit,
    navController: NavHostController,
    apartmentViewModel: ApartmentViewModel,
    rootNavController: NavHostController,
    firstDestination:String
) {
    val appState = rememberAppState()
    Box(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.Center),
            visible = baseUIState.mainLoading,
            exit = fadeOut(tween(delayMillis = 300)),
            enter = fadeIn(tween(delayMillis = 300))
        ) {
            CircularProgressIndicator()
        }
        AnimatedVisibility(
            visible = !baseUIState.mainLoading,
            exit = fadeOut(tween(delayMillis = 300)),
            enter = fadeIn(tween(delayMillis = 300))
        ) {
            NavHost(
                modifier = Modifier.fillMaxSize(),
                navController = navController,
                route = Graph.APARTMENT,
                startDestination = firstDestination
            ) {
                composable(ProfileScreen.route) {

                    ProfileScreen(
                        appState = appState,
                        popUpScreen = { navController.popBackStack() },
                        cleanNavigateToDestination = { rootNavController.cleanNavigateTo(it) },
                    )
                }
                composable(ChatScreen.route) {
                    EmptyScreen(
                        popUpScreen = { navController.popBackStack() },
                    )
                }
                composable(AddApartmentScreen.route) {
                    AddApartmentScreenContent(
                        viewModel = apartmentViewModel,
                        navController = navController,
                        canNavigateBack = navController.previousBackStackEntry != null,
                        onDrawerClicked = onDrawerClicked,
                        navigationType = navigationType
                    )
                    //            AddApartmentScreen(
                    //                popUpScreen = { appState.popUp() },
                    //                restartApp = { route -> appState.clearAndNavigate(route) },
                    //            )
                }

                composable(SettingsScreen.route) {
                    SettingsScreen(popUpScreen = { navController.popBackStack() })
                }

                composable(
                    route = ApartmentScreen.routeWithArgs,
                    arguments = ApartmentScreen.arguments
                ) { navBackStackEntry ->
                    val addressIdArg =
                        navBackStackEntry.arguments?.getInt(ApartmentScreen.addressIdArg)
                    ApartmentScreen(
                        appState = appState,
                        contentType = contentType,
                        baseUIState = baseUIState,
                        navigationType = navigationType,
                        displayFeatures = displayFeatures,
                        closeDetailScreen = { apartmentViewModel.closeDetailScreen() },
                        navigateToDetail = { contentDetail ->
                            apartmentViewModel.setSelectedDetail(
                                contentDetail
                            )
                        },
                        deleteApartment = {
                            apartmentViewModel.deleteApartment(
                                navController.navigateToInfoApartment(
                                    0
                                )
                            )
                        },
                        onDrawerClicked = onDrawerClicked,
                        addressId = addressIdArg!!,
                        apartmentViewModel = apartmentViewModel,
                    )
                }
                composable(MeterScreen.route) {
                    MeterScreen(
                        baseUIState = baseUIState,
                        navigationType = navigationType,
                        onDrawerClick = onDrawerClicked
                    )
                }
                composable(ServiceListScreen.route) {
                    MainServiceScreen(
                        baseUIState = baseUIState,
                        navigationType = navigationType,
                        onDrawerClick = onDrawerClicked,
                        displayFeature = displayFeatures,
                        contentType = contentType,
                    )
                }
                composable(
                    route = InfoApartmentScreen.routeWithArgs,
                    arguments = InfoApartmentScreen.arguments
                ){
                    navBackStackEntry->
                    val addressIdArg =
                    navBackStackEntry.arguments?.getInt(InfoApartmentScreen.addressIdArg)
                    InfoApartmentScreen(
                        baseUIState = baseUIState,
                        apartmentViewModel = apartmentViewModel,
                        deleteApartment = {
                            apartmentViewModel.deleteApartment(
                                navController.navigateToInfoApartment(
                                    0
                                )
                            )
                        },
                        onDrawerClicked = onDrawerClicked,
                        appState = appState,
                        addressId = addressIdArg!!,
                        navigationType = navigationType
                    )
                }
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}