package com.ykis.ykispam.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ykis.ykispam.YkisPamAppState
import com.ykis.ykispam.firebase.screens.sign_in.SignInScreen
import com.ykis.ykispam.firebase.screens.sign_up.SignUpScreen
import com.ykis.ykispam.firebase.screens.sign_up.SignUpViewModel
import com.ykis.ykispam.firebase.screens.verify_email.VerifyEmailScreen

fun NavGraphBuilder.authNavGraph(
    appState: YkisPamAppState,
    navController : NavHostController
) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = SIGN_IN_SCREEN ){
        composable(SIGN_UP_SCREEN) {
            entry ->
            val signUpViewModel = entry.sharedViewModel<SignUpViewModel>(navController)
            SignUpScreen(
                openScreen = { route -> appState.navigate(route) },
                viewModel = signUpViewModel
            )
        }
        composable(VERIFY_EMAIL_SCREEN) {
            entry ->
            val signUpViewModel = entry.sharedViewModel<SignUpViewModel>(navController)
            VerifyEmailScreen(
                restartApp = { route -> appState.clearAndNavigate(route) },
                viewModel = signUpViewModel
                )
        }

        composable(SIGN_IN_SCREEN) {
                entry ->
            val signUpViewModel = entry.sharedViewModel<SignUpViewModel>(navController)
            SignInScreen(
                openScreen = { route -> appState.navigate(route) },
                navigateToDestination = {route-> appState.navigateTo(route)},
            )

        }
    }
}