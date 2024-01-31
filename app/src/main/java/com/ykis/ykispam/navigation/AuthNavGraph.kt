package com.ykis.ykispam.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ykis.ykispam.pam.screens.auth.sign_in.SignInScreen
import com.ykis.ykispam.pam.screens.auth.sign_up.SignUpScreen
import com.ykis.ykispam.pam.screens.auth.sign_up.SignUpViewModel
import com.ykis.ykispam.pam.screens.auth.verify_email.VerifyEmailScreen

fun NavGraphBuilder.authNavGraph(
    navController : NavHostController,
) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = SIGN_IN_SCREEN ){
        composable(SIGN_UP_SCREEN) {
            entry ->
            val signUpViewModel = entry.sharedViewModel<SignUpViewModel>(navController)
            SignUpScreen(
                openScreen = { route -> navController.navigate(route) },
                viewModel = signUpViewModel
            )
        }
        composable(VERIFY_EMAIL_SCREEN) {
            entry ->
            val signUpViewModel = entry.sharedViewModel<SignUpViewModel>(navController)
            VerifyEmailScreen(
                restartApp = { route ->  navController.cleanNavigateTo(route) },
                viewModel = signUpViewModel
                )
        }

        composable(SIGN_IN_SCREEN) {
                entry ->
            val signUpViewModel = entry.sharedViewModel<SignUpViewModel>(navController)
            SignInScreen(
                openScreen = { route -> navController.navigate(route) },
                navigateToDestination = {route-> navController.navigate(route)},
            )

        }
    }
}