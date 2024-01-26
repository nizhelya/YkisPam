package com.ykis.ykispam.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import com.ykis.ykispam.pam.screens.launch.LaunchScreen

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
    Scaffold { paddingValues ->
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







