package com.ykis.ykispam.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.window.layout.DisplayFeature
import com.ykis.ykispam.BaseUIState
import com.ykis.ykispam.pam.screens.appartment.AddApartmentScreenContent
import com.ykis.ykispam.pam.screens.appartment.ApartmentScreen
import com.ykis.ykispam.pam.screens.appartment.ApartmentViewModel
import com.ykis.ykispam.pam.screens.launch.LaunchScreen
import com.ykis.ykispam.pam.screens.meter.MeterScreen
import com.ykis.ykispam.pam.screens.profile.ProfileScreen
import com.ykis.ykispam.pam.screens.service.ServiceListScreen
import com.ykis.ykispam.pam.screens.settings.SettingsScreen
import com.ykis.ykispam.rememberAppState
import kotlinx.coroutines.launch

@Composable
fun MainApartmentScreen(
    contentType: ContentType,
    navigationType: NavigationType,
    displayFeatures: List<DisplayFeature>,
    navController: NavHostController = rememberNavController(),
    viewModel : ApartmentViewModel = hiltViewModel(),
    rootNavController: NavHostController

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
                    navigateToDestination = {navController.navigateWithPopUp(it , APARTMENT_SCREEN)},
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
                selectedDestination = selectedDestination,
                onClick = { navController.navigateWithPopUp(it, APARTMENT_SCREEN) })
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
            navController = navController,
            onDrawerClicked = {
                coroutineScope.launch {
                    drawerState.open()
                }
            },
            apartmentViewModel = viewModel,
            rootNavController = rootNavController

        )
        if (navigationType != NavigationType.BOTTOM_NAVIGATION) {
            ApartmentNavigationRail(
                baseUIState = baseUIState,
                selectedDestination = selectedDestination,
                navigationContentPosition = NavigationContentPosition.TOP,
                closeDetailScreen = {viewModel.closeDetailScreen()},
                navigateToDestination = { navController.navigateWithPopUp(it, APARTMENT_SCREEN) },
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
    onDrawerClicked: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    apartmentViewModel: ApartmentViewModel,
    rootNavController: NavHostController
) {
    val appState = rememberAppState()

    NavHost(
        modifier = modifier,
        navController = navController,
        route = Graph.APARTMENT,
        startDestination = APARTMENT_SCREEN
    ) {
        composable(YkisRoute.ACCOUNT) {

            ProfileScreen(
                appState = appState,
                popUpScreen = { navController.popBackStack() },
                cleanNavigateToDestination = {rootNavController.cleanNavigateTo(it)},
            )
        }
        composable(YkisRoute.CHAT) {
            EmptyScreen(
                popUpScreen = { navController.popBackStack()},
            )
        }
        composable(YkisRoute.MESSAGE) {
            EmptyScreen(
                popUpScreen = {navController.popBackStack() },
            )
        }
        composable(YkisRoute.OSBB) {
            EmptyScreen(
                popUpScreen = { navController.popBackStack() },
            )
        }
        composable(YkisRoute.EXITAPP) {
            LaunchScreen(
                restartApp = { route -> navController.cleanNavigateTo(route) },
                openAndPopUp = { route, popUp -> navController.navigateWithPopUp(route, popUp) },
            )
        }
        composable(ADD_APARTMENT_SCREEN) {
            AddApartmentScreenContent(
                viewModel = apartmentViewModel,
                navController = navController
            )
//            AddApartmentScreen(
//                popUpScreen = { appState.popUp() },
//                restartApp = { route -> appState.clearAndNavigate(route) },
//            )
        }

        composable(YkisRoute.SETTINGS) {
            SettingsScreen(popUpScreen = { navController.popBackStack() })
        }

        composable(
            route = "$APARTMENT_SCREEN$ADDRESS_ID_ARG",
            arguments = listOf(
                navArgument(ADDRESS_ID) { defaultValue = ADDRESS_DEFAULT_ID },
            )
        ) {
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
                getApartments = {apartmentViewModel.initialize()},
                deleteApartment = {addressId, restartApp ->  apartmentViewModel.deleteApartment(addressId, restartApp)},
                onDrawerClicked = onDrawerClicked,
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