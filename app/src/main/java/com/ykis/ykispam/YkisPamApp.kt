package com.ykis.ykispam

import android.content.res.Resources
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.navigation.ApartmentNavigationRail
import com.ykis.ykispam.navigation.BottomNavigationBar
import com.ykis.ykispam.navigation.ContentType
import com.ykis.ykispam.navigation.DevicePosture
import com.ykis.ykispam.navigation.ModalNavigationDrawerContent
import com.ykis.ykispam.navigation.NavigationContentPosition
import com.ykis.ykispam.navigation.NavigationType
import com.ykis.ykispam.navigation.PermanentNavigationDrawerContent
import com.ykis.ykispam.navigation.SPLASH_SCREEN
import com.ykis.ykispam.navigation.YkisPamGraph
import com.ykis.ykispam.navigation.isBookPosture
import com.ykis.ykispam.navigation.isSeparating
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun YkisPamApp(
    windowSize: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    baseUIState: BaseUIState
) {

    /**
     *  Это поможет нам выбрать тип навигации и тип содержимого в зависимости от размера окна и
     * navigationType тип навигации (
     * BOTTOM_NAVIGATION- кнопки навигации внизу,
     * NAVIGATION_RAIL - кнопки слева,
     * PERMANENT_NAVIGATION_DRAWER через всплывающюю панель
     * )
     * contentType тип контента зависит от устройства (fold или обычный ) и размера  экрана  (
     * SINGLE_PANE - одиночный экран,
     * DUAL_PANE - расширеный экран
     */
//    val uiState by  viewModel.uiState.collectAsStateWithLifecycle()
    val navigationType: NavigationType
    val contentType: ContentType
    val appState = rememberAppState()

    /**
     * foldingFeature Мы используем функции складывания дисплея, чтобы отобразить положение устройства,
     * в котором находится сгиб.
     * В состоянии складного устройства. Если в BookPosture оно сложено пополам, мы хотим избежать содержимого.
     * на сгибе/петлях
     */
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

    /**
     * navigationContentPosition Содержимое внутри навигационной направляющей/ящика также можно расположить сверху,
     *  или по центру (TOP, CENTER) для эргономичность и доступность в зависимости от высоты устройства.
     */
    val navigationContentPosition = when (windowSize.heightSizeClass) {
        WindowHeightSizeClass.Compact -> {
            NavigationContentPosition.TOP
        }

        WindowHeightSizeClass.Medium,
        WindowHeightSizeClass.Expanded -> {
            NavigationContentPosition.CENTER
        }

        else -> {
            NavigationContentPosition.TOP
        }
    }

    NavigationWrapper(
        navigationType = navigationType, //(BOTTOM_NAVIGATION, NAVIGATION_RAIL, PERMANENT_NAVIGATION_DRAWER)
        contentType = contentType,      //(SINGLE,DUAL)
        displayFeatures = displayFeatures,  //fold device развернуто BookPostureили сложено  SINGLE
        navigationContentPosition = navigationContentPosition, // PERMANENT_NAVIGATION_DRAWER содержимое TOP or CENTER
        baseUIState = baseUIState,
        appState = appState
    )
}

@Composable
fun NavigationWrapper(
    navigationType: NavigationType,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: NavigationContentPosition,
    baseUIState: BaseUIState,
    appState:YkisPamAppState
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = appState.coroutineScope
    val navController = appState.navController
//    val uid: String? = baseUIState.uid
//    val navigationActions = remember(navController) {
//        NavigationActions(navController)
//    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination = navBackStackEntry?.destination?.route ?: baseUIState.selectedDestination
//    val selectedDestination = "$EXIT_SCREEN?$ADDRESS_ID={${ADDRESS_DEFAULT_ID}"

    if (navigationType == NavigationType.PERMANENT_NAVIGATION_DRAWER) {
        // TODO check on custom width of PermanentNavigationDrawer: b/232495216
        PermanentNavigationDrawer(drawerContent = {
            PermanentNavigationDrawerContent(
                baseUIState = baseUIState,
                selectedDestination = selectedDestination,
                navigationContentPosition = navigationContentPosition,
                navigateToDestination = appState::navigateTo,
            )
        })
        {
            AppContent(
                appState = appState,
                baseUIState = baseUIState,
                navigationType = navigationType,
                contentType = contentType,
                displayFeatures = displayFeatures,
                navigationContentPosition = navigationContentPosition,
                navController = navController,
                selectedDestination = selectedDestination,
                navigateToDestination = appState::navigateTo,
            )
        }
    } else {
        ModalNavigationDrawer(
            drawerContent = {
                ModalNavigationDrawerContent(
                    baseUIState = baseUIState,
                    selectedDestination = selectedDestination,
                    navigationContentPosition = navigationContentPosition,
                    navigateToDestination = appState::navigateTo,
                    onDrawerClicked = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }
                )
            },
            drawerState = drawerState
        ) {
            AppContent(
                appState = appState,
                baseUIState = baseUIState,
                navigationType = navigationType,
                contentType = contentType,
                displayFeatures = displayFeatures,
                navigationContentPosition = navigationContentPosition,
                navController = navController,
                selectedDestination = selectedDestination,
                navigateToDestination = appState::navigateTo,
            ) {
                coroutineScope.launch {
                    drawerState.open()
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent(
    modifier: Modifier = Modifier,
    drawerState: DrawerState? = null,
    appState: YkisPamAppState,
    baseUIState: BaseUIState,
    navigationType: NavigationType,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: NavigationContentPosition,
    navController: NavHostController,
    selectedDestination: String,
    navigateToDestination: (String) -> Unit,
    onDrawerClicked: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        baseUIState.uid?.let {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                }, // нет title
                navigationIcon = {
                    IconButton(onClick = {
                        appState.coroutineScope.launch {
                            // открывает ящик
                            onDrawerClicked()
                        }
                    }) {
                        Icon(
                            // внутреннее меню-гамбургер
                            Icons.Rounded.Menu,
                            contentDescription = "MenuButton"
                        )
                    }
                },
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = appState.snackbarHostState,
                modifier = Modifier.padding(8.dp),
                snackbar = { snackbarData ->
                    Snackbar(
                        snackbarData,
                    )
                }
            )
        },
    ) {
        Row(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            AnimatedVisibility(visible = navigationType == NavigationType.NAVIGATION_RAIL) {
                ApartmentNavigationRail(
                    baseUIState = baseUIState,
                    selectedDestination = selectedDestination,
                    navigationContentPosition = navigationContentPosition,
                    navigateToDestination = navigateToDestination,
                    onDrawerClicked = onDrawerClicked,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.inverseOnSurface)

            ) {
                YkisNavHost(
                    appState = appState,
                    baseUIState = baseUIState,
                    navController = navController,
                    contentType = contentType,
                    displayFeatures = displayFeatures,
                    navigationType = navigationType,
                    modifier = Modifier.weight(1f),
                )
                AnimatedVisibility(visible = navigationType == NavigationType.BOTTOM_NAVIGATION) {
                    BottomNavigationBar(
                        selectedDestination = selectedDestination,
                        navigateToDestination = navigateToDestination
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun YkisNavHost(
    appState: YkisPamAppState,
    baseUIState: BaseUIState,
    navController: NavHostController,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    navigationType: NavigationType,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = SPLASH_SCREEN,
    ) {
        YkisPamGraph(contentType, navigationType, displayFeatures,appState)
    }
}


@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),


    ) =
    remember(navController, snackbarManager, resources, coroutineScope) {
        YkisPamAppState(
            navController,
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




