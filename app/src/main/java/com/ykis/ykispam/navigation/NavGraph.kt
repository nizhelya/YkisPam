package com.ykis.ykispam.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import com.ykis.ykispam.pam.screens.launch.LaunchScreen
import com.ykis.ykispam.rememberAppState

object Graph {
    const val AUTHENTICATION = "auth_graph"
    const val APARTMENT = "apartment_graph"
}
@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    navigationType: NavigationType,
) {
    val appState = rememberAppState()
    Scaffold (
        snackbarHost = {
            SnackbarHost(
                hostState = appState.snackbarHostState,
                snackbar = { snackbarData ->
                    Snackbar(
                        snackbarData,
                    )
                }
            )
        },
    ){ paddingValues ->
        NavHost(
            modifier = modifier
                .padding(paddingValues = paddingValues),
            navController = navController,
            startDestination = LAUNCH_SCREEN,
        ) {
            composable(LAUNCH_SCREEN) {
                LaunchScreen(
                    restartApp = { route -> navController.cleanNavigateTo(route) },
                    openAndPopUp = { route, popUp -> navController.navigateWithPopUp(route, popUp) },
                )
            }

            authNavGraph(navController)

            composable(route = Graph.APARTMENT) {
                MainApartmentScreen(
                    contentType = contentType,
                    navigationType = navigationType,
                    displayFeatures = displayFeatures,
                    rootNavController = navController
                )
            }
        }
    }
}







