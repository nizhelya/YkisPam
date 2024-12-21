package com.ykis.mob.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ykis.mob.ui.screens.auth.sign_in.SignInScreen
import com.ykis.mob.ui.screens.auth.sign_up.SignUpScreen
import com.ykis.mob.ui.screens.auth.sign_up.SignUpViewModel
import com.ykis.mob.ui.screens.auth.verify_email.VerifyEmailScreen

fun NavGraphBuilder.authNavGraph(
    navController : NavHostController,
) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = SignInScreen.route
    ){
        composable(SignUpScreen.route) {
            entry ->
            val signUpViewModel = entry.sharedViewModel<SignUpViewModel>(navController)
            SignUpScreen(
                openScreen = { route -> navController.navigate(route) },
                viewModel = signUpViewModel
            )
        }
        composable(VerifyEmailScreen.route) {
            entry ->
            val signUpViewModel = entry.sharedViewModel<SignUpViewModel>(navController)
            VerifyEmailScreen(
                restartApp = { route ->  navController.cleanNavigateTo(route) },
                viewModel = signUpViewModel
                )
        }

        composable(SignInScreen.route) {
                entry ->
            val signUpViewModel = entry.sharedViewModel<SignUpViewModel>(navController)
            SignInScreen(
                openScreen = { route -> navController.navigate(route) },
                navigateToDestination = {route-> navController.navigate(route)},
            )

        }
    }
}