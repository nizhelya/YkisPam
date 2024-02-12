package com.ykis.ykispam.ui

import android.content.res.Resources
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.ui.navigation.ContentType
import com.ykis.ykispam.ui.navigation.DevicePosture
import com.ykis.ykispam.ui.navigation.NavigationType
import com.ykis.ykispam.ui.navigation.RootNavGraph
import com.ykis.ykispam.ui.navigation.isBookPosture
import com.ykis.ykispam.ui.navigation.isSeparating
import kotlinx.coroutines.CoroutineScope


@Composable
fun YkisPamApp(
    windowSize: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,

) {
    val navigationType: NavigationType
    val contentType: ContentType
    val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()

    val foldingDevicePosture = when {
        isBookPosture(foldingFeature) ->
            DevicePosture.BookPosture(foldingFeature.bounds)

        isSeparating(foldingFeature) ->
            DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

        else -> DevicePosture.NormalPosture
    }

    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.SINGLE_PANE
        }

        WindowWidthSizeClass.Medium -> {
            navigationType = NavigationType.NAVIGATION_RAIL
            contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
                ContentType.DUAL_PANE
            } else {
                ContentType.SINGLE_PANE
            }
        }

        WindowWidthSizeClass.Expanded -> {
            navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
                NavigationType.NAVIGATION_RAIL
            } else {
                NavigationType.PERMANENT_NAVIGATION_DRAWER
            }
            contentType = ContentType.DUAL_PANE

        }

        else -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.SINGLE_PANE
        }
    }
    RootNavGraph(
        modifier = Modifier,
        contentType = contentType,
        displayFeatures = displayFeatures,
        navigationType = navigationType,
    )
}
@Composable
fun rememberAppState(
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) =
    remember(snackbarManager, resources, coroutineScope) {
        YkisPamAppState(
            snackbarHostState,
            snackbarManager,
            resources,
            coroutineScope
        )
    }

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

