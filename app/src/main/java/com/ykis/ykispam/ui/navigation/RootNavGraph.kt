package com.ykis.ykispam.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import com.ykis.ykispam.ui.screens.launch.LaunchScreen
import com.ykis.ykispam.ui.rememberAppState

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
    var isNavBar by rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold (
        snackbarHost = {
            SnackbarHost(
                modifier = modifier.padding(bottom = if(isNavBar) 80.dp else 0.dp),
                hostState = appState.snackbarHostState,
                snackbar = { snackbarData ->
                    Snackbar(
                        snackbarData
                    )
                }
            )
        },
    ){ paddingValues ->
        NavHost(
            modifier = modifier
                .padding(paddingValues = paddingValues),
            navController = navController,
            startDestination = LaunchScreen.route,
        ) {
            composable(LaunchScreen.route) {
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
                    rootNavController = navController,
                    appState = appState,
                    onLaunch = {isNavBar = true},
                    onDispose = {isNavBar = false}
                )
            }
        }
    }
}








