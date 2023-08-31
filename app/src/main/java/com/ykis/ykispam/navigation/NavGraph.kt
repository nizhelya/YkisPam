package com.ykis.ykispam.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.window.layout.DisplayFeature
import com.ykis.ykispam.ExitScreen
import com.ykis.ykispam.BaseUIState
import com.ykis.ykispam.SplashScreen
import com.ykis.ykispam.YkisPamAppState
import com.ykis.ykispam.firebase.screens.profile.ProfileScreen
import com.ykis.ykispam.firebase.screens.settings.SettingsScreen
import com.ykis.ykispam.firebase.screens.sign_in.SignInScreen
import com.ykis.ykispam.firebase.screens.sign_up.SignUpScreen
import com.ykis.ykispam.firebase.screens.verify_email.VerifyEmailScreen
import com.ykis.ykispam.pam.screens.add_apartment.AddApartmentScreen
import com.ykis.ykispam.pam.screens.appartment.ApartmentScreen


fun NavGraphBuilder.YkisPamGraph(
    contentType: ContentType,
    navigationType: NavigationType,
    displayFeatures: List<DisplayFeature>,
    appState:YkisPamAppState,

) {

    composable(SPLASH_SCREEN) {
        SplashScreen(
            openScreen = { route -> appState.clearAndNavigate(route) },
//            navigateToDestination = { route -> appState.navigateTo(route) }
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }


        )
    }

    composable(
        route = "$EXIT_SCREEN$ADDRESS_ID_ARG",
        arguments = listOf(navArgument(ADDRESS_ID) { defaultValue = ADDRESS_DEFAULT_ID })
    ) {
        ExitScreen(
            popUpScreen = { appState.popUp() },
            addressId = it.arguments?.getString(ADDRESS_ID) ?: ADDRESS_DEFAULT_ID
        )
    }


    composable(PROFILE_SCREEN) {
        ProfileScreen(
            restartApp = { route -> appState.clearAndNavigate(route) }
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
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) },
            openScreen = { route -> appState.navigate(route) }
        )

    }
    composable(ADD_APARTMENT_SCREEN) {
        AddApartmentScreen(
            contentType = contentType,
            navigationType = navigationType,
            displayFeatures = displayFeatures,
            popUpScreen = { appState.popUp() })

    }

    composable(SETTINGS_SCREEN) {
        SettingsScreen(popUpScreen = { appState.popUp() })
    }


    composable(
        route = APARTMENT_SCREEN,
    ) {
        ApartmentScreen(
            appState = appState,
            contentType = contentType,
            navigationType = navigationType,
            displayFeatures = displayFeatures,
        )
    }
}






