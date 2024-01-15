package com.ykis.ykispam.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ykis.ykispam.YkisPamAppState
import com.ykis.ykispam.firebase.screens.sign_in.SignInScreen
import com.ykis.ykispam.firebase.screens.sign_up.SignUpScreen
import com.ykis.ykispam.firebase.screens.verify_email.VerifyEmailScreen

fun NavGraphBuilder.authNavGraph(
//    navController: NavHostController,
    appState: YkisPamAppState
) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = SIGN_IN_SCREEN ){
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
    }
}