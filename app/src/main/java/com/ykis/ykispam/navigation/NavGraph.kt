package com.ykis.ykispam.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.window.layout.DisplayFeature
import com.ykis.ykispam.BaseUIState
import com.ykis.ykispam.YkisPamAppState
import com.ykis.ykispam.firebase.screens.profile.ProfileScreen
import com.ykis.ykispam.firebase.screens.settings.SettingsScreen
import com.ykis.ykispam.firebase.screens.sign_in.SignInScreen
import com.ykis.ykispam.firebase.screens.sign_up.SignUpScreen
import com.ykis.ykispam.firebase.screens.verify_email.VerifyEmailScreen
import com.ykis.ykispam.pam.screens.appartment.AddApartmentScreen
import com.ykis.ykispam.pam.screens.appartment.ApartmentScreen
import com.ykis.ykispam.pam.screens.launch.LaunchScreen
import com.ykis.ykispam.pam.screens.meter.water.WaterScreen


fun NavGraphBuilder.YkisPamGraph(
    contentType: ContentType,
    navigationType: NavigationType,
    displayFeatures: List<DisplayFeature>,
    appState: YkisPamAppState,
    baseUIState: BaseUIState,
    getApartments: () -> Unit,
    closeDetailScreen: () -> Unit,
    navigateToDestination: (String) -> Unit,
    setApartment: (Int) -> Unit ,
    navigateToDetail: (ContentDetail, ContentType) -> Unit,
    onDrawerClicked: () -> Unit = {}

) {

    composable(LAUNCH_SCREEN) {
        LaunchScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) },
        )
    }


    composable(
        route = "$WATER_SCREEN$ADDRESS_ID_ARG",
        arguments = listOf(navArgument(ADDRESS_ID) { defaultValue = ADDRESS_DEFAULT_ID })
    ) {
        WaterScreen(
            popUpScreen = { appState.popUp() },
            addressId = it.arguments?.getString(ADDRESS_ID) ?: ADDRESS_DEFAULT_ID
        )
    }

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
    composable(SIGN_UP_SCREEN) {
        SignUpScreen(
            openScreen = { route -> appState.navigate(route) },
        )
    }
    composable(VERIFY_EMAIL_SCREEN) {
        VerifyEmailScreen(restartApp = { route -> appState.clearAndNavigate(route) })
    }

    composable(SIGN_IN_SCREEN) {
        SignInScreen(
            openScreen = { route -> appState.navigate(route) }
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

}






