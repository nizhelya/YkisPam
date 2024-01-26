package com.ykis.ykispam.navigation

import androidx.navigation.NavHostController

fun NavHostController.cleanNavigateTo(route: String){
    this.navigate(route) {
        popUpTo(0)
    }
}
fun NavHostController.navigateWithPopUp(route: String, popUp: String) {
    this.navigate(route) {
        restoreState = true
        launchSingleTop = true
        popUpTo(popUp) {
            inclusive = false
            saveState=true

        }
    }
}