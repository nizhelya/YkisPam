package com.ykis.ykispam.navigation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import androidx.window.layout.DisplayFeature
import com.ykis.ykispam.BaseUIState
import com.ykis.ykispam.YkisPamAppState
import com.ykis.ykispam.firebase.screens.profile.ProfileScreen
import com.ykis.ykispam.firebase.screens.settings.SettingsScreen
import com.ykis.ykispam.pam.screens.appartment.AddApartmentScreen
import com.ykis.ykispam.pam.screens.appartment.ApartmentScreen
import com.ykis.ykispam.pam.screens.launch.LaunchScreen
import com.ykis.ykispam.pam.screens.meter.MeterScreen
import com.ykis.ykispam.pam.screens.service.ServiceListScreen
import kotlinx.coroutines.launch

@Composable
fun MainApartmentScreen(
    appState: YkisPamAppState,
    contentType: ContentType,
    navigationType: NavigationType,
    displayFeatures: List<DisplayFeature>,
    baseUIState: BaseUIState,
    getApartments: () -> Unit,
    closeDetailScreen: () -> Unit,
    navigateToDestination: (String) -> Unit,
    setApartment: (Int) -> Unit,
    navigateToDetail: (ContentDetail, ContentType) -> Unit,
    onDrawerClicked: () -> Unit = {},
    navController: NavHostController = rememberNavController(),
//    selectedDestination:String

) {
    val drawerState = DrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = appState.coroutineScope
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination =
        navBackStackEntry?.destination?.route ?: baseUIState.selectedDestination
    Log.d("test_nav2", "$navBackStackEntry\n$selectedDestination")
    ModalNavigationDrawer(
        drawerContent = {
                ModalNavigationDrawerContent(
                    baseUIState = baseUIState,
                    selectedDestination = selectedDestination,
                    navigationContentPosition =NavigationContentPosition.TOP,
                    navigateToDestination = {navController.navigate(it)},
                    closeDetailScreen = closeDetailScreen,
                    setApartment=setApartment,
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
        Log.d("e", it.toString())

        ApartmentNavGraph(
            modifier = Modifier.padding(
                start = if (navigationType != NavigationType.BOTTOM_NAVIGATION) 80.dp else 0.dp,
//                bottom = it.calculateBottomPadding()
            ).background(color = Color.Green),
            appState = appState,
            contentType = contentType,
            navigationType = navigationType,
            displayFeatures = displayFeatures,
            baseUIState = baseUIState,
            getApartments = getApartments,
            closeDetailScreen = closeDetailScreen,
            navigateToDestination = navigateToDestination,
            setApartment = setApartment,
            navigateToDetail = navigateToDetail,
            navController = navController
        )
        if (navigationType != NavigationType.BOTTOM_NAVIGATION) {
            ApartmentNavigationRail(
                baseUIState = baseUIState,
                selectedDestination = selectedDestination,
                navigationContentPosition = NavigationContentPosition.TOP,
                closeDetailScreen = closeDetailScreen,
                navigateToDestination = { navController.navigate(it) },
                setApartment = setApartment
            )
        }
    }
        }
//    }

}

@Composable
fun ApartmentNavGraph(
    modifier: Modifier = Modifier,
    appState: YkisPamAppState,
    contentType: ContentType,
    navigationType: NavigationType,
    displayFeatures: List<DisplayFeature>,
    baseUIState: BaseUIState,
    getApartments: () -> Unit,
    closeDetailScreen: () -> Unit,
    navigateToDestination: (String) -> Unit,
    setApartment: (Int) -> Unit,
    navigateToDetail: (ContentDetail, ContentType) -> Unit,
    onDrawerClicked: () -> Unit = {},
    navController: NavHostController
) {
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

fun NavGraphBuilder.apartmentNavGraph(
//    TODO:remove this arg
    appState: YkisPamAppState,
    contentType: ContentType,
    navigationType: NavigationType,
    displayFeatures: List<DisplayFeature>,
    baseUIState: BaseUIState,
    getApartments: () -> Unit,
    closeDetailScreen: () -> Unit,
    navigateToDestination: (String) -> Unit,
    setApartment: (Int) -> Unit,
    navigateToDetail: (ContentDetail, ContentType) -> Unit,
    onDrawerClicked: () -> Unit = {}
) {
    navigation(
        route = Graph.APARTMENT,
        startDestination = SERVICE_LIST_SCREEN
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