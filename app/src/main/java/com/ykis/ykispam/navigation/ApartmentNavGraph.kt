package com.ykis.ykispam.navigation

import android.util.Log
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
import com.ykis.ykispam.pam.screens.appartment.AddApartmentScreenContent
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
    Log.d("viewmodel_test","MainApartmentScreen:$viewModel")
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
            navController = navController,
            onDrawerClicked = {
                coroutineScope.launch {
                    drawerState.open()
                }
            },
            apartmentViewModel = viewModel
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
    onDrawerClicked: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
    apartmentViewModel: ApartmentViewModel
) {
    Log.d("viewModel_test" , "ApartmentNavGraph:$apartmentViewModel")
    val appState = rememberAppState(navController)
    val state = apartmentViewModel.uiState.collectAsState()
    Log.d("vm_test","apartmentNavGraph:${state}")
    NavHost(
        modifier = modifier,
        navController = navController,
        route = Graph.APARTMENT,
        startDestination = APARTMENT_SCREEN
    ) {
        composable(YkisRoute.ACCOUNT) {

            ProfileScreen(
                appState = appState,
                popUpScreen = { appState.popUp() },
                navigateToDestination = {appState.navigateTo(it)},
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
            AddApartmentScreenContent(
                appState = appState,
                viewModel = apartmentViewModel
            )
//            AddApartmentScreen(
//                popUpScreen = { appState.popUp() },
//                restartApp = { route -> appState.clearAndNavigate(route) },
//            )
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