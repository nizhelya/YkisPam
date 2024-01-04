package com.ykis.ykispam.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.window.layout.DisplayFeature
import com.ykis.ykispam.SplashScreen
import com.ykis.ykispam.YkisPamAppState
import com.ykis.ykispam.firebase.screens.profile.ProfileScreen
import com.ykis.ykispam.firebase.screens.settings.SettingsScreen
import com.ykis.ykispam.firebase.screens.sign_in.SignInScreen
import com.ykis.ykispam.firebase.screens.sign_up.SignUpScreen
import com.ykis.ykispam.firebase.screens.verify_email.VerifyEmailScreen
import com.ykis.ykispam.pam.screens.appartment.AddApartmentScreen
import com.ykis.ykispam.pam.screens.appartment.ApartmentScreen
import com.ykis.ykispam.pam.screens.appartment.WaterScreen
import com.ykis.ykispam.pam.screens.bti.BtiScreen
import com.ykis.ykispam.pam.screens.family.FamilyScreen
import com.ykis.ykispam.pam.screens.service.ServiceDetailScreen


fun NavGraphBuilder.YkisPamGraph(
    contentType: ContentType,
    navigationType: NavigationType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: NavigationContentPosition,
    appState: YkisPamAppState,

    ) {

    composable(SPLASH_SCREEN) {
        SplashScreen(
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }


        )
    }
    composable(
        route = "$BTI_SCREEN$FLAT_ARG",
        arguments = listOf(
            navArgument(ADDRESS_ID) { defaultValue = ADDRESS_DEFAULT_ID },
            navArgument(ADDRESS) { defaultValue = ADDRESS_DEFAULT })
    )
    {
        BtiScreen(
            popUpScreen = { appState.popUp() },
            openScreen = { route -> appState.navigate(route) },
            addressId = it.arguments?.getString(ADDRESS_ID) ?: ADDRESS_DEFAULT_ID,
            address = it.arguments?.getString(ADDRESS) ?: ADDRESS_DEFAULT,

            )
    }
    composable(
        route = "$FAMILY_SCREEN$FLAT_ARG",
        arguments = listOf(
            navArgument(ADDRESS_ID) { defaultValue = ADDRESS_DEFAULT_ID },
            navArgument(ADDRESS) { defaultValue = ADDRESS_DEFAULT })
    )
    {
        FamilyScreen(
            popUpScreen = { appState.popUp() },
            addressId = it.arguments?.getString(ADDRESS_ID) ?: ADDRESS_DEFAULT_ID,
            address = it.arguments?.getString(ADDRESS) ?: ADDRESS_DEFAULT,

            )
    }
    composable(
        route = "$WATER_SCREEN$ADDRESS_ID_ARG",
        arguments = listOf(navArgument(ADDRESS_ID) { defaultValue = ADDRESS_DEFAULT_ID })
    ) {
        WaterScreen(
            popUpScreen = { appState.popUp() },
            addressId = it.arguments?.getString(ADDRESS_ID) ?: ADDRESS_DEFAULT_ID
        )
    }

    composable(YkisRoute.ACCOUNT) {

        ProfileScreen(
            popUpScreen = { appState.popUp() },
            restartApp = { route -> appState.clearAndNavigate(route) },
        )
    }
    composable(YkisRoute.CHAT) {
        EmptyScreen(
            popUpScreen = { appState.popUp() },
        )
    }
    composable(YkisRoute.MESSAGE) {
        EmptyScreen(
            popUpScreen = { appState.popUp() },
        )    }
    composable(YkisRoute.OSBB) {
        EmptyScreen(
            popUpScreen = { appState.popUp() },
        )    }
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
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) },
            openScreen = { route -> appState.navigate(route) }
        )

    }
    composable(ADD_APARTMENT_SCREEN) {
        AddApartmentScreen(
            popUpScreen = { appState.popUp() },
            restartApp = { route -> appState.clearAndNavigate(route) })

    }

    composable(YkisRoute.SETTINGS) {
        SettingsScreen(popUpScreen = { appState.popUp() })
    }

    composable(
        route = "$APARTMENT_SCREEN$ADDRESS_ID_ARG",
        arguments = listOf(
            navArgument(ADDRESS_ID) { defaultValue = ADDRESS_DEFAULT_ID },
        )
    ) {
        ApartmentScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            addressId = it.arguments?.getString(ADDRESS_ID) ?: ADDRESS_DEFAULT_ID,
//            address = it.arguments?.getString(ADDRESS) ?: ADDRESS_DEFAULT,
            appState = appState,
            contentType = contentType,
            navigationType = navigationType,
            displayFeatures = displayFeatures,
            navigationContentPosition = navigationContentPosition
        )
    }
        composable(
            route = "$SERVICE_DETAIL_SCREEN$FLAT_ARG",
            arguments = listOf(
                navArgument(ADDRESS_ID) { defaultValue = ADDRESS_DEFAULT_ID },
                navArgument(ADDRESS) { defaultValue = ADDRESS_DEFAULT },
//                navArgument(HOUSE_ID) { defaultValue = HOUSE_DEFAULT_ID }),
        )){
            // TODO: добавить аргумент houseId 
            ServiceDetailScreen(
                popUpScreen = { appState.popUp() },
                addressId = it.arguments?.getString(ADDRESS_ID) ?: ADDRESS_DEFAULT_ID,
                houseId ="43",
                address = it.arguments?.getString(ADDRESS) ?: ADDRESS_DEFAULT
            )
        }
    }






