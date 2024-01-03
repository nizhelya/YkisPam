/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.ykis.ykispam

import android.content.res.Resources
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Stable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.core.snackbar.SnackbarMessage.Companion.toMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch


@Stable
class YkisPamAppState(
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState,
    private val snackbarManager: SnackbarManager,
    private val resources: Resources,
    val coroutineScope: CoroutineScope
) {

    init {
        coroutineScope.launch {
            snackbarManager.snackbarMessages.filterNotNull().collect { snackbarMessage ->
                val text = snackbarMessage.toMessage(resources)
                snackbarHostState.showSnackbar(text)
            }
        }
    }


    fun popUp() {
        navController.popBackStack()
    }

    fun navigate(route: String) {
        navController.navigate(route) { launchSingleTop = true }
    }

       fun navigateAndPopUp(route: String, popUp: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUp) { inclusive = true }
        }
    }

//    fun clearAndNavigate(route: String) {
//        navController.navigate(route) {
//            launchSingleTop = true
//            popUpTo(0) { inclusive = true }
//        }
//    }
fun clearAndNavigate(route: String) {
    navController.navigate(route) {
        // Всплывающее окно к начальному пункту назначения графика
        // избегаем создания большого стека пунктов назначения
        // в заднем стеке, когда пользователи выбирают элементы
//        popUpTo(navController.graph.findStartDestination().id) {
//            saveState = false
//        }
            popUpTo(0) { inclusive = true }

        // Избегайте нескольких копий одного и того же места назначения, когда
        // повторный выбор того же элемента
        launchSingleTop = true
        // Восстанавливаем состояние при повторном выборе ранее выбранного элемента
        restoreState = true
    }
}

    fun navigateTo(route: String) {
        navController.navigate(route) {
            // Всплывающее окно к начальному пункту назначения графика
            // избегаем создания большого стека пунктов назначения
            // в заднем стеке, когда пользователи выбирают элементы
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = false
            }
//            popUpTo(0) { inclusive = true }

            // Избегайте нескольких копий одного и того же места назначения, когда
            // повторный выбор того же элемента
            launchSingleTop = true
            // Восстанавливаем состояние при повторном выборе ранее выбранного элемента
            restoreState = true
        }
    }

}


