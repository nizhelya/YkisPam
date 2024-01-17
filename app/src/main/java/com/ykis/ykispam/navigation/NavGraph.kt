package com.ykis.ykispam.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.window.layout.DisplayFeature
import com.ykis.ykispam.BaseUIState
import com.ykis.ykispam.pam.screens.launch.LaunchScreen
import com.ykis.ykispam.pam.screens.meter.water.WaterScreen
import com.ykis.ykispam.rememberAppState

object Graph {
    const val AUTHENTICATION = "auth_graph"
    const val APARTMENT = "apartment_graph"
}
@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
//    appState: YkisPamAppState,
    baseUIState: BaseUIState,
    navController: NavHostController = rememberNavController(),
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    navigationType: NavigationType,
    getApartments: () -> Unit,
    closeDetailScreen: () -> Unit,
//    navigateToDestination: (String) -> Unit,
    setApartment: (Int) -> Unit,
    navigateToDetail: (ContentDetail, ContentType) -> Unit,
    onDrawerClicked: () -> Unit = {},
) {
    val appState = rememberAppState(navController)
    NavHost(
        modifier = modifier.background(MaterialTheme.colorScheme.tertiaryContainer),
        navController = navController,
        startDestination = LAUNCH_SCREEN,
    ) {
        composable(LAUNCH_SCREEN) {
            LaunchScreen(
                restartApp = { route -> appState.clearAndNavigate(route) },
                openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) },
            )
        }

        authNavGraph(appState)

        composable(
            route = "$WATER_SCREEN$ADDRESS_ID_ARG",
            arguments = listOf(navArgument(ADDRESS_ID) { defaultValue = ADDRESS_DEFAULT_ID })
        ) {
            WaterScreen(
                popUpScreen = { appState.popUp() },
                addressId = it.arguments?.getString(ADDRESS_ID) ?: ADDRESS_DEFAULT_ID
            )
        }
        composable(route= Graph.APARTMENT){
            MainApartmentScreen(
                appState = appState,
                contentType =contentType ,
                navigationType = navigationType,
                displayFeatures = displayFeatures,
                baseUIState = baseUIState,
                getApartments = getApartments,
                closeDetailScreen =closeDetailScreen,
                navigateToDestination = appState::navigateTo,
                setApartment = setApartment,
                navigateToDetail = navigateToDetail,
            )
        }
    }
}
//fun NavGraphBuilder.YkisPamGraph(
//    contentType: ContentType,
//    navigationType: NavigationType,
//    displayFeatures: List<DisplayFeature>,
//    appState: YkisPamAppState,
//    baseUIState: BaseUIState,
//    getApartments: () -> Unit,
//    closeDetailScreen: () -> Unit,
//    navigateToDestination: (String) -> Unit,
//    setApartment: (Int) -> Unit,
//    navigateToDetail: (ContentDetail, ContentType) -> Unit,
//    onDrawerClicked: () -> Unit = {},
//    selectedDestination: String
//
//) {
//    composable(LAUNCH_SCREEN) {
//        LaunchScreen(
//            restartApp = { route -> appState.clearAndNavigate(route) },
//            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) },
//        )
//    }
//
//    authNavGraph(appState)
//
//    composable(
//        route = "$WATER_SCREEN$ADDRESS_ID_ARG",
//        arguments = listOf(navArgument(ADDRESS_ID) { defaultValue = ADDRESS_DEFAULT_ID })
//    ) {
//        WaterScreen(
//            popUpScreen = { appState.popUp() },
//            addressId = it.arguments?.getString(ADDRESS_ID) ?: ADDRESS_DEFAULT_ID
//        )
//    }
//    composable(route=Graph.APARTMENT){
//        MainApartmentScreen(
//            appState = appState,
//            contentType =contentType ,
//            navigationType = navigationType,
//            displayFeatures = displayFeatures,
//            baseUIState = baseUIState,
//            getApartments = getApartments,
//            closeDetailScreen =closeDetailScreen,
//            navigateToDestination = navigateToDestination,
//            setApartment = setApartment,
//            navigateToDetail = navigateToDetail,
////            selectedDestination = selectedDestination
//        )
//    }
//
//}






