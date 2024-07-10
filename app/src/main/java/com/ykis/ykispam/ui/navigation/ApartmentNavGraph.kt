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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
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
import com.ykis.ykispam.ui.screens.appartment.AddApartmentScreenContent
import com.ykis.ykispam.ui.screens.appartment.ApartmentViewModel
import com.ykis.ykispam.ui.screens.appartment.InfoApartmentScreen
import com.ykis.ykispam.ui.screens.chat.ChatViewModel
import com.ykis.ykispam.ui.screens.chat.UserListScreen
import com.ykis.ykispam.ui.screens.meter.MainMeterScreen
import com.ykis.ykispam.ui.screens.meter.MeterViewModel
import com.ykis.ykispam.ui.screens.profile.ProfileScreen
import com.ykis.ykispam.ui.screens.service.MainServiceScreen
import com.ykis.ykispam.ui.screens.service.ServiceViewModel
import com.ykis.ykispam.ui.screens.settings.SettingsScreenStateful
import kotlinx.coroutines.launch

@Composable
fun MainApartmentScreen(
    contentType: ContentType,
    navigationType: NavigationType,
    displayFeatures: List<DisplayFeature>,
    navController: NavHostController = rememberNavController(),
    apartmentViewModel: ApartmentViewModel = hiltViewModel(),
    meterViewModel: MeterViewModel = hiltViewModel(),
    serviceViewModel: ServiceViewModel = hiltViewModel(),
    chatViewModel: ChatViewModel = hiltViewModel(),
    rootNavController: NavHostController,
    appState: YkisPamAppState,
    onLaunch: () -> Unit,
    onDispose: () -> Unit,
    isRailExpanded: Boolean,
    onMenuClick: () -> Unit,
    navigateToWebView: (String) -> Unit
) {
    val baseUIState by apartmentViewModel.uiState.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination =
        navBackStackEntry?.destination?.route ?: InfoApartmentScreen.route
    val railWidth by animateDpAsState(
        targetValue = if (isRailExpanded) 260.dp else 80.dp, tween(550), label = ""
    )

    DisposableEffect(key1 = Unit) {
        onLaunch()
        apartmentViewModel.initialize()
        onDispose {
            onDispose()
        }
    }
    val movableApartmentNavGraph = remember {
        movableContentOf {
            ApartmentNavGraph(
                modifier =
                Modifier
                    .padding(
                        bottom = if (navigationType == NavigationType.BOTTOM_NAVIGATION) 80.dp else 0.dp,
                        start = if (navigationType != NavigationType.BOTTOM_NAVIGATION) railWidth else 0.dp
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
                apartmentViewModel = apartmentViewModel,
                rootNavController = rootNavController,
                firstDestination = if (baseUIState.apartments.isNotEmpty()) InfoApartmentScreen.route else AddApartmentScreen.route,
                meterViewModel = meterViewModel,
                serviceViewModel = serviceViewModel,
                closeContentDetail = {
                    meterViewModel.closeContentDetail()
                    serviceViewModel.closeContentDetail()
                },
                navigateToWebView = navigateToWebView,
                chatViewModel = chatViewModel
            )
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
                        meterViewModel.closeContentDetail()
                        serviceViewModel.closeContentDetail()
                    },
                    onMenuClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    },
                    navigateToApartment = { addressId ->
                        coroutineScope.launch {
                            drawerState.close()
                            meterViewModel.closeContentDetail()
                            serviceViewModel.closeContentDetail()
                            apartmentViewModel.setAddressId(addressId)
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
                    if (baseUIState.apartments.isNotEmpty()) {
                        BottomNavigationBar(
                            selectedDestination = selectedDestination,
                            onClick = {
                                navController.navigateWithPopUp(
                                    it,
                                    InfoApartmentScreen.route
                                )
                                meterViewModel.closeContentDetail()
                                serviceViewModel.closeContentDetail()
                            }
                        )
                    }
                }
            ) {
                paddingValues ->
                print(paddingValues.toString())
                movableApartmentNavGraph()
            }
        }
    } else {
        Scaffold(
            snackbarHost = { appState.snackbarHostState },
        ) {
            paddingValues ->
            print(paddingValues.toString())
            movableApartmentNavGraph()
            ApartmentNavigationRail(
                selectedDestination = selectedDestination,
                navigateToDestination = {
                    navController.navigateWithPopUp(
                        it,
                        InfoApartmentScreen.route
                    )
                    meterViewModel.closeContentDetail()
                    serviceViewModel.closeContentDetail()
                },
                isRailExpanded = isRailExpanded,
                onMenuClick = onMenuClick,
                baseUIState = baseUIState,
                navigateToApartment = { addressId ->
                    meterViewModel.closeContentDetail()
                    serviceViewModel.closeContentDetail()
                    apartmentViewModel.setAddressId(addressId)
                },
                railWidth = railWidth,
                isApartmentsEmpty = baseUIState.apartments.isEmpty(),
                maxApartmentListHeight = if (navigationType == NavigationType.NAVIGATION_RAIL_COMPACT) 132.dp else 194.dp
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
    firstDestination: String,
    meterViewModel: MeterViewModel,
    serviceViewModel: ServiceViewModel,
    chatViewModel: ChatViewModel,
    closeContentDetail :() ->Unit,
    navigateToWebView: (String) -> Unit
) {
    val appState = rememberAppState()
    val userList by chatViewModel.userList.collectAsStateWithLifecycle()
    val selectedUser by chatViewModel.selectedUser.collectAsStateWithLifecycle()
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
                        navigationType = navigationType,
                        onDrawerClicked = onDrawerClicked,
                        navigateToSettings = {
                            navController.navigate(SettingsScreen.route)
                        }
                    )
                }
                composable(UserListScreen.route) {
                    LaunchedEffect(key1 = true) {
                        chatViewModel.getUsers()
                    }
                    UserListScreen(
                        userList = userList,
                        onUserClicked = {
                            chatViewModel.setSelectedUser(it)
                            navController.navigate(ChatScreen.route)
                        },
                        onDrawerClicked = onDrawerClicked,
                        navigationType = navigationType,
                        baseUIState = baseUIState
                    )
                }

                composable(ChatScreen.route){
                    com.ykis.ykispam.ui.screens.chat.ChatScreen(
                        userEntity = selectedUser,
                        navigateBack = {
                            navController.navigateUp()
                        },
                        chatViewModel = chatViewModel,
                        baseUIState = baseUIState
                    )
                }
                composable(AddApartmentScreen.route) {
                    AddApartmentScreenContent(
                        viewModel = apartmentViewModel,
                        navController = navController,
                        canNavigateBack = navController.previousBackStackEntry != null,
                        onDrawerClicked = onDrawerClicked,
                        navigationType = navigationType,
                        closeContentDetail = {
                            closeContentDetail()
                        }
                    )
                }

                composable(SettingsScreen.route) {
                   SettingsScreenStateful(
                        navigationType = navigationType,
                        navigateToAuthGraph = {
                            rootNavController.cleanNavigateTo(LaunchScreen.route)
                        },
                       onDrawerClick = onDrawerClicked
                    )
                }
                composable(MeterScreen.route) {
                    MainMeterScreen(
                        baseUIState = baseUIState,
                        navigationType = navigationType,
                        onDrawerClick = onDrawerClicked,
                        contentType = contentType,
                        displayFeature = displayFeatures,
                        viewModel = meterViewModel
                    )
                }
                composable(ServiceListScreen.route) {
                    MainServiceScreen(
                        baseUIState = baseUIState,
                        navigationType = navigationType,
                        onDrawerClick = onDrawerClicked,
                        displayFeature = displayFeatures,
                        contentType = contentType,
                        viewModel = serviceViewModel,
                        navigateToWebView = navigateToWebView
                    )
                }
                composable(
                    route = InfoApartmentScreen.route,
                ) {
                    InfoApartmentScreen(
                        contentType = contentType,
                        displayFeatures = displayFeatures,
                        baseUIState = baseUIState,
                        apartmentViewModel = apartmentViewModel,
                        deleteApartment = {
                            apartmentViewModel.deleteApartment()
                        },
                        onDrawerClicked = onDrawerClicked,
                        appState = appState,
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