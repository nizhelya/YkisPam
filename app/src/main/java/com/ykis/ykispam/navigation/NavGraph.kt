package com.ykis.ykispam.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.window.layout.DisplayFeature
import com.ykis.ykispam.BaseUIState
import com.ykis.ykispam.YkisPamAppState
import com.ykis.ykispam.pam.screens.launch.LaunchScreen
import com.ykis.ykispam.pam.screens.meter.water.WaterScreen

object Graph {
    const val AUTHENTICATION = "auth_graph"
    const val APARTMENT = "apartment_graph"
}

fun NavGraphBuilder.YkisPamGraph(
    contentType: ContentType,
    navigationType: NavigationType,
    displayFeatures: List<DisplayFeature>,
    appState: YkisPamAppState,
    baseUIState: BaseUIState,
    getApartments: () -> Unit,
    closeDetailScreen: () -> Unit,
    navigateToDestination: (String) -> Unit,
    setApartment: (Int) -> Unit,
    navigateToDetail: (ContentDetail, ContentType) -> Unit,
    onDrawerClicked: () -> Unit = {},
    selectedDestination: String

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
    composable(route=Graph.APARTMENT){
        MainApartmentScreen(
            appState = appState,
            contentType =contentType ,
            navigationType = navigationType,
            displayFeatures = displayFeatures,
            baseUIState = baseUIState,
            getApartments = getApartments,
            closeDetailScreen =closeDetailScreen,
            navigateToDestination = navigateToDestination,
            setApartment = setApartment,
            navigateToDetail = navigateToDetail,
//            selectedDestination = selectedDestination
        )
    }

}






