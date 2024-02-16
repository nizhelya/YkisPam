package com.ykis.ykispam.ui.navigation

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.ykis.ykispam.ui.screens.EmptyScreen
import com.ykis.ykispam.ui.screens.appartment.AddApartmentScreenContent
import com.ykis.ykispam.ui.screens.appartment.ApartmentScreen
import com.ykis.ykispam.ui.screens.appartment.ApartmentViewModel
import com.ykis.ykispam.ui.screens.meter.MeterScreen
import com.ykis.ykispam.ui.screens.profile.ProfileScreen
import com.ykis.ykispam.ui.screens.service.ServiceListScreen
import com.ykis.ykispam.ui.screens.settings.SettingsScreen
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.YkisPamAppState
import com.ykis.ykispam.ui.navigation.components.ApartmentNavigationRail
import com.ykis.ykispam.ui.navigation.components.BottomNavigationBar
import com.ykis.ykispam.ui.navigation.components.ModalNavigationDrawerContent
import com.ykis.ykispam.ui.rememberAppState
import kotlinx.coroutines.launch

@Composable
fun MainApartmentScreen(
    contentType: ContentType,
    navigationType: NavigationType,
    displayFeatures: List<DisplayFeature>,
    navController: NavHostController = rememberNavController(),
    viewModel : ApartmentViewModel = hiltViewModel(),
    rootNavController: NavHostController,
    appState: YkisPamAppState,
    onLaunch : () -> Unit,
    onDispose : () -> Unit
) {
    val baseUIState by viewModel.uiState.collectAsStateWithLifecycle()
    val drawerState = DrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination =
        navBackStackEntry?.destination?.route ?: baseUIState.selectedDestination
    var isRailExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    val railWidth by animateDpAsState(
        targetValue = if (isRailExpanded) 260.dp else 80.dp, tween(550), label = ""
    )
    DisposableEffect(key1 = Unit) {
        onLaunch()
        onDispose {
            onDispose()
        }
    }
    if(baseUIState.apartments.isNotEmpty()) {
        if (navigationType == NavigationType.BOTTOM_NAVIGATION) {
            ModalNavigationDrawer(
                drawerContent = {
                    ModalNavigationDrawerContent(
                        baseUIState = baseUIState,
                        selectedDestination = selectedDestination,
                        navigateToDestination = {
                            coroutineScope.launch {
                                drawerState.close()
                                navController.navigateWithPopUp(it, APARTMENT_SCREEN)
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
                                navController.navigateToApartment(addressId)
                            }

                        }
                    )
                },
                drawerState = drawerState
            ) {
                Scaffold(
                    snackbarHost = { appState.snackbarHostState },
                    bottomBar = {
                        BottomNavigationBar(
                            selectedDestination = selectedDestination,
                            onClick = { navController.navigateWithPopUp(it, APARTMENT_SCREEN) })
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
                        rootNavController = rootNavController

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
                    rootNavController = rootNavController

                )
                ApartmentNavigationRail(
                    selectedDestination = selectedDestination,
                    navigateToDestination = {
                        navController.navigateWithPopUp(
                            it,
                            APARTMENT_SCREEN
                        )
                    },
                    isRailExpanded = isRailExpanded,
                    onMenuClick = { isRailExpanded = !isRailExpanded },
                    baseUIState = baseUIState,
                    navigateToApartment = { addressId ->
                        navController.navigateToApartment(addressId)
                    },
                    railWidth = railWidth,
                )

            }
        }
    }else{
        ApartmentNavGraph(
            modifier = Modifier.fillMaxSize(),
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
            rootNavController = rootNavController

        )
    }
}
@Composable
fun ApartmentNavGraph(
    modifier: Modifier = Modifier,
    contentType: ContentType,
    navigationType: NavigationType,
    displayFeatures: List<DisplayFeature>,
    baseUIState: BaseUIState,
    onDrawerClicked: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    apartmentViewModel: ApartmentViewModel,
    rootNavController: NavHostController
) {
    val appState = rememberAppState()
    LaunchedEffect(key1 = true) {
        apartmentViewModel.initialize()
    }
    NavHost(
        modifier = modifier,
        navController = navController,
        route = Graph.APARTMENT,
        startDestination =
        if(baseUIState.apartments.isEmpty()){
            AddApartmentScreen.route
        }else
            ApartmentScreen.routeWithArgs
    ) {
        composable(ProfileScreen.route) {

            ProfileScreen(
                appState = appState,
                popUpScreen = { navController.popBackStack() },
                cleanNavigateToDestination = {rootNavController.cleanNavigateTo(it)},
            )
        }
        composable(ChatScreen.route) {
            EmptyScreen(
                popUpScreen = { navController.popBackStack()},
            )
        }
        composable(AddApartmentScreen.route) {
            AddApartmentScreenContent(
                viewModel = apartmentViewModel,
                navController = navController,
                canNavigateBack = navController.previousBackStackEntry != null
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
        ) {
                navBackStackEntry->
            val addressIdArg =
                navBackStackEntry.arguments?.getInt(ApartmentScreen.addressIdArg)
            ApartmentScreen(
                openScreen = { route -> navController.navigate(route) },
                restartApp = { route ->  navController.cleanNavigateTo(route) },
                appState = appState,
                contentType = contentType,
                baseUIState = baseUIState,
                navigationType = navigationType,
                displayFeatures = displayFeatures,
                closeDetailScreen = {apartmentViewModel.closeDetailScreen()},
                setApartment = {addressId ->apartmentViewModel.setApartment(addressId) },
                navigateToDetail = {contentDetail, pane ->  apartmentViewModel.setSelectedDetail(contentDetail, pane) },
                getApartment = {apartmentViewModel.getApartment()},
                getApartments = {apartmentViewModel.initialize()},
                deleteApartment = {addressId, restartApp ->  apartmentViewModel.deleteApartment(addressId, restartApp)},
                onDrawerClicked = onDrawerClicked,
                addressId = addressIdArg!!,
                apartmentViewModel = apartmentViewModel
            )
        }
        composable(MeterScreen.route) {
            MeterScreen()
        }
        composable(ServiceListScreen.route) {
            ServiceListScreen()
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