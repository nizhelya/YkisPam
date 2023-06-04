package com.ykis.ykispam.navigation

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.ykis.ykispam.YkisPamAppState
import com.ykis.ykispam.firebase.screens.profile.ProfileScreen
import com.ykis.ykispam.firebase.screens.sign_in.SignInScreen
import com.ykis.ykispam.firebase.screens.sign_up.SignUpScreen
import com.ykis.ykispam.firebase.screens.splash.SplashScreen
import com.ykis.ykispam.firebase.screens.verify_email.VerifyEmailScreen
import com.ykis.ykispam.pam.screens.add_appartment.AddAppartmentScreen
import com.ykis.ykispam.pam.screens.appartment.AppartmentScreen


@OptIn(ExperimentalComposeUiApi::class)
fun NavGraphBuilder.YkisPamGraph(appState: YkisPamAppState) {

    composable(SPLASH_SCREEN) {
        SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }


    composable(PROFILE_SCREEN) {
        ProfileScreen(restartApp = { route -> appState.clearAndNavigate(route) })
    }
    composable(SIGN_UP_SCREEN) {
        SignUpScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }
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
    composable(ADD_APPARTMENT_SCREEN) {
        AddAppartmentScreen(openScreen = { route -> appState.navigate(route) })
    }
    composable(APPARTMENT_SCREEN) {
        AppartmentScreen(openScreen = { route -> appState.navigate(route) })
    }
}

