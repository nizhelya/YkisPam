package com.ykis.ykispam.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.window.layout.DisplayFeature
import com.ykis.ykispam.BaseUIState
import com.ykis.ykispam.firebase.screens.profile.ProfileScreen
import com.ykis.ykispam.firebase.screens.settings.SettingsScreen
import com.ykis.ykispam.pam.screens.appartment.AddApartmentScreen
import com.ykis.ykispam.pam.screens.appartment.ApartmentScreen
import com.ykis.ykispam.pam.screens.appartment.ApartmentViewModel
import com.ykis.ykispam.pam.screens.launch.LaunchScreen
import com.ykis.ykispam.pam.screens.meter.MeterScreen
import com.ykis.ykispam.pam.screens.service.ServiceListScreen
import com.ykis.ykispam.rememberAppState
import kotlinx.coroutines.launch

@Composable
fun MainApartmentScreen(
    contentType: ContentType,
    navigationType: NavigationType,
    displayFeatures: List<DisplayFeature>,
    navigateToDestination: (String) -> Unit,
    navController: NavHostController = rememberNavController(),
    viewModel : ApartmentViewModel = hiltViewModel()

) {
    val baseUIState by viewModel.uiState.collectAsState()
    val drawerState = DrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination =
        navBackStackEntry?.destination?.route ?: baseUIState.selectedDestination

    ModalNavigationDrawer(
        drawerContent = {
                ModalNavigationDrawerContent(
                    baseUIState = baseUIState,
                    selectedDestination = selectedDestination,
                    navigationContentPosition =NavigationContentPosition.TOP,
                    navigateToDestination = {navController.navigate(it)},
                    closeDetailScreen = {viewModel.closeDetailScreen()},
                    setApartment= {addressId ->viewModel.setApartment(addressId) },
                    onDrawerClicked = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }
                )
        },
        drawerState = drawerState
    ) {
    Scaffold(
        bottomBar = {
            if (navigationType == NavigationType.BOTTOM_NAVIGATION) BottomNavigationBar(
                navigateToDestination = navigateToDestination,
                selectedDestination = selectedDestination,
                onClick = { navController.navigate(it) })
        }
    ) { it ->
        ApartmentNavGraph(
            modifier = Modifier
                .padding(
                    start = if (navigationType != NavigationType.BOTTOM_NAVIGATION) 80.dp else 0.dp,
                    bottom = it.calculateBottomPadding()
                ),
            contentType = contentType,
            navigationType = navigationType,
            displayFeatures = displayFeatures,
            baseUIState = baseUIState,
            closeDetailScreen = {viewModel.closeDetailScreen()},
            setApartment = {addressId ->viewModel.setApartment(addressId) },
            navigateToDetail = {contentDetail, pane ->  viewModel.setSelectedDetail(contentDetail, pane) },
            getApartments = {viewModel.initialize()},
            navController = navController,
            deleteApartment = {addressId, restartApp ->  viewModel.deleteApartment(addressId, restartApp)},
            onDrawerClicked = {
                coroutineScope.launch {
                    drawerState.open()
                }
            }
        )
        if (navigationType != NavigationType.BOTTOM_NAVIGATION) {
            ApartmentNavigationRail(
                baseUIState = baseUIState,
                selectedDestination = selectedDestination,
                navigationContentPosition = NavigationContentPosition.TOP,
                closeDetailScreen = {viewModel.closeDetailScreen()},
                navigateToDestination = { navController.navigate(it) },
                setApartment = {addressId ->viewModel.setApartment(addressId) }
            )
        }
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
    getApartments: () -> Unit,
    closeDetailScreen: () -> Unit,
    setApartment: (Int) -> Unit,
    navigateToDetail: (ContentDetail, ContentType) -> Unit,
    onDrawerClicked: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    deleteApartment: (addressId: Int, restartApp: (String) -> Unit) ->Unit
) {
    val appState = rememberAppState(navController)
    NavHost(
        modifier = modifier,
        navController = navController,
        route = Graph.APARTMENT,
        startDestination = APARTMENT_SCREEN
    ) {
        composable(YkisRoute.ACCOUNT) {

            ProfileScreen(
                popUpScreen = { appState.popUp() },
                restartApp = { route -> appState.clearAndNavigate(route) },
            )
        }
        composable(YkisRoute.CHAT) {
            EmptyScreen(
                popUpScreen = { appState.popUp() },
            )
        }
        composable(YkisRoute.MESSAGE) {
            EmptyScreen(
                popUpScreen = { appState.popUp() },
            )
        }
        composable(YkisRoute.OSBB) {
            EmptyScreen(
                popUpScreen = { appState.popUp() },
            )
        }
        composable(YkisRoute.EXITAPP) {
            LaunchScreen(
                restartApp = { route -> appState.clearAndNavigate(route) },
                openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) },
            )
        }
        composable(ADD_APARTMENT_SCREEN) {
            AddApartmentScreen(
                popUpScreen = { appState.popUp() },
                restartApp = { route -> appState.clearAndNavigate(route) },
            )

        }

        composable(YkisRoute.SETTINGS) {
            SettingsScreen(popUpScreen = { appState.popUp() })
        }

        composable(
            route = "$APARTMENT_SCREEN$ADDRESS_ID_ARG",
            arguments = listOf(
                navArgument(ADDRESS_ID) { defaultValue = ADDRESS_DEFAULT_ID },
            )
        ) {
            ApartmentScreen(
                openScreen = { route -> appState.navigate(route) },
                restartApp = { route -> appState.clearAndNavigate(route) },
                appState = appState,
                contentType = contentType,
                baseUIState = baseUIState,
                navigationType = navigationType,
                displayFeatures = displayFeatures,
                closeDetailScreen = closeDetailScreen,
                getApartments = getApartments,
                setApartment = setApartment,
                navigateToDetail = navigateToDetail,
                addressId = it.arguments?.getString(ADDRESS_ID) ?: ADDRESS_DEFAULT_ID,
                onDrawerClicked = onDrawerClicked,
                deleteApartment = deleteApartment
            )
        }
        composable(METER_SCREEN) {
            MeterScreen()
        }
        composable(SERVICE_LIST_SCREEN) {
            ServiceListScreen()
        }
    }
}