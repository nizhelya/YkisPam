package com.ykis.ykispam.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import com.ykis.ykispam.ui.rememberAppState
import com.ykis.ykispam.ui.screens.launch.LaunchScreen

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
    var isMainScreen by rememberSaveable {
        mutableStateOf(false)
    }
    var isRailExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    val padding = when{
        (navigationType == NavigationType.NAVIGATION_RAIL_COMPACT || navigationType == NavigationType.NAVIGATION_RAIL_EXPANDED )  && !isRailExpanded && isMainScreen-> Modifier.padding(start = 80.dp)
        navigationType == NavigationType.BOTTOM_NAVIGATION && isMainScreen-> Modifier.padding(bottom = 80.dp)
        (navigationType == NavigationType.NAVIGATION_RAIL_COMPACT || navigationType == NavigationType.NAVIGATION_RAIL_EXPANDED)  && isRailExpanded && isMainScreen -> Modifier.padding(start = 260.dp)
        else -> Modifier.padding(0.dp)
    }
    Scaffold (
        snackbarHost = {
            SnackbarHost(
                modifier = padding,
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
            enterTransition = {
                fadeIn()
            },
            exitTransition = {
                fadeOut()
            },
            popEnterTransition = {
                fadeIn()
            },
            popExitTransition = {
                fadeOut()
            }
        ) {
            composable(
                route = LaunchScreen.route,
                enterTransition = {
                    fadeIn()
                },
                exitTransition = {
                    fadeOut()
                },
                popEnterTransition = {
                    fadeIn()
                },
                popExitTransition = {
                    fadeOut()
                }) {
                LaunchScreen(
                    restartApp = { route -> navController.cleanNavigateTo(route) },
                    openAndPopUp = { route, popUp -> navController.navigateWithPopUp(route, popUp) },
                )
            }

            authNavGraph(
                navController
            )

            composable(
                route = Graph.APARTMENT,
                enterTransition = {
                    fadeIn()
                },
                exitTransition = {
                    fadeOut()
                },
                popEnterTransition = {
                    fadeIn()
                },
                popExitTransition = {
                    fadeOut()
                }
            ) {
                MainApartmentScreen(
                    contentType = contentType,
                    navigationType = navigationType,
                    displayFeatures = displayFeatures,
                    rootNavController = navController,
                    appState = appState,
                    onLaunch = {isMainScreen = true},
                    onDispose = {isMainScreen = false},
                    isRailExpanded = isRailExpanded,
                    onMenuClick = { isRailExpanded = !isRailExpanded }
                )
            }
        }
    }
}








