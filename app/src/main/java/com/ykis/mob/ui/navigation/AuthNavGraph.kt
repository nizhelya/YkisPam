package com.ykis.mob.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
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
    signUpViewModel : SignUpViewModel
) {

    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = SignInScreen.route
    ){
        composable(SignUpScreen.route) {
            entry ->
            SignUpScreen(
                viewModel = signUpViewModel,
                navController = navController
            )
        }
        composable(VerifyEmailScreen.route) {
            entry ->
            VerifyEmailScreen(
                restartApp = { route ->  navController.cleanNavigateTo(route) },
                viewModel = signUpViewModel,
                navController = navController
                )
        }

        composable(SignInScreen.route) {
                entry ->
            SignInScreen(
                openScreen = { route -> navController.navigate(route) },
                navController = navController
            )
        }
    }
}