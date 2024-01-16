package com.ykis.ykispam

import android.content.res.Resources
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.navigation.ContentDetail
import com.ykis.ykispam.navigation.ContentType
import com.ykis.ykispam.navigation.DevicePosture
import com.ykis.ykispam.navigation.LAUNCH_SCREEN
import com.ykis.ykispam.navigation.NavigationContentPosition
import com.ykis.ykispam.navigation.NavigationType
import com.ykis.ykispam.navigation.YkisPamGraph
import com.ykis.ykispam.navigation.isBookPosture
import com.ykis.ykispam.navigation.isSeparating
import kotlinx.coroutines.CoroutineScope


@Composable
fun YkisPamApp(
//    viewModel : ApartmentViewModel = hiltViewModel(),
    windowSize: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
    baseUIState: BaseUIState,
    isUserSignedOut: Boolean,
    getApartments: () -> Unit,
    closeDetailScreen: () -> Unit = {},
    setApartment: (Int) -> Unit ,
    navigateToDetail: (ContentDetail, ContentType) -> Unit = { _, _ -> },
) {
    val navigationType: NavigationType
    val contentType: ContentType
    val appState = rememberAppState()
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
    val navigationContentPosition = when (windowSize.heightSizeClass) {
        WindowHeightSizeClass.Compact -> {
            NavigationContentPosition.TOP
        }

        WindowHeightSizeClass.Medium,
        WindowHeightSizeClass.Expanded -> {
            NavigationContentPosition.TOP
        }

        else -> {
            NavigationContentPosition.TOP
        }
    }

    NavigationWrapper(
        navigationType = navigationType, //(BOTTOM_NAVIGATION, NAVIGATION_RAIL, PERMANENT_NAVIGATION_DRAWER)
        contentType = contentType,      //(SINGLE,DUAL)
        displayFeatures = displayFeatures,  //fold device развернуто BookPosture или сложено  SINGLE
        navigationContentPosition = navigationContentPosition, // PERMANENT_NAVIGATION_DRAWER содержимое TOP or CENTER
        appState = appState,
        baseUIState = baseUIState,
        getApartments = getApartments,
        isUserSignedOut = isUserSignedOut,
        closeDetailScreen = closeDetailScreen,
        setApartment = setApartment,
        navigateToDetail = navigateToDetail,
    )
}

@Composable
fun NavigationWrapper(
    navigationType: NavigationType,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: NavigationContentPosition,
    appState: YkisPamAppState,
    baseUIState: BaseUIState,
    isUserSignedOut: Boolean,
    getApartments: () -> Unit,
    closeDetailScreen: () -> Unit,
    setApartment: (Int) -> Unit ,
    navigateToDetail: (ContentDetail, ContentType) -> Unit,
) {
    val drawerState = DrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = appState.coroutineScope
    val navController = appState.navController
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination = navBackStackEntry?.destination?.route ?: baseUIState.selectedDestination
//    Log.d("test_nav", "${selectedDestination}\n$navBackStackEntry")
//
//    if (isUserSignedOut) {
//        AppContent(
//            appState = appState,
//            baseUIState = baseUIState,
//            isUserSignedOut = isUserSignedOut,
//            navigationType = navigationType,
//            contentType = contentType,
//            displayFeatures = displayFeatures,
//            navigationContentPosition = navigationContentPosition,
//            navController = navController,
//            selectedDestination = selectedDestination,
//            navigateToDestination = appState::navigateTo,
//            getApartments = getApartments,
//            closeDetailScreen = closeDetailScreen,
//            setApartment = setApartment,
//            navigateToDetail = navigateToDetail,
//        )
////        navController.navigate("Auth")
//    } else {
//        if (navigationType == NavigationType.PERMANENT_NAVIGATION_DRAWER) {
//            PermanentNavigationDrawer(drawerContent = {
//                PermanentNavigationDrawerContent(
//                    baseUIState = baseUIState,
//                    selectedDestination = selectedDestination,
//                    navigationContentPosition = navigationContentPosition,
//                    navigateToDestination = appState::navigateTo,
//                    closeDetailScreen = closeDetailScreen,
//                )
//            }) {
//                AppContent(
//                    appState = appState,
//                    baseUIState = baseUIState,
//                    isUserSignedOut = isUserSignedOut,
//                    navigationType = navigationType,
//                    contentType = contentType,
//                    displayFeatures = displayFeatures,
//                    navigationContentPosition = navigationContentPosition,
//                    navController = navController,
//                    selectedDestination = selectedDestination,
//                    navigateToDestination = appState::navigateTo,
//                    getApartments = getApartments,
//                    closeDetailScreen = closeDetailScreen,
//                    setApartment = setApartment,
//                    navigateToDetail = navigateToDetail,
//                )
//            }
//        } else {
//            ModalNavigationDrawer(
//                drawerContent = {
//                    ModalNavigationDrawerContent(
//                        baseUIState = baseUIState,
//                        selectedDestination = selectedDestination,
//                        navigationContentPosition = navigationContentPosition,
//                        navigateToDestination = appState::navigateTo,
//                        closeDetailScreen = closeDetailScreen,
//                        setApartment=setApartment,
//                        onDrawerClicked = {
//                            coroutineScope.launch {
//                                drawerState.close()
//                            }
//                        }
//                    )
//                },
//                drawerState = drawerState
//            ) {
                AppContent(
                    appState = appState,
                    baseUIState = baseUIState,
                    isUserSignedOut = isUserSignedOut,
                    navigationType = navigationType,
                    contentType = contentType,
                    displayFeatures = displayFeatures,
                    navigationContentPosition = navigationContentPosition,
                    navController = navController,
                    selectedDestination = selectedDestination,
                    navigateToDestination = appState::navigateTo,
                    getApartments = getApartments,
                    closeDetailScreen = closeDetailScreen,
                    setApartment = setApartment,
                    navigateToDetail = navigateToDetail,
                )
//                {
//                    coroutineScope.launch {
//                        drawerState.open()
//                    }
//                }
//            }
//        }
//    }
}

@Composable
fun AppContent(
    modifier: Modifier = Modifier,
    appState: YkisPamAppState,
    baseUIState: BaseUIState,
    isUserSignedOut: Boolean,
    navigationType: NavigationType,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: NavigationContentPosition,
    navController: NavHostController,
    selectedDestination: String,
    navigateToDestination: (String) -> Unit,
    getApartments: () -> Unit,
    closeDetailScreen: () -> Unit,
    setApartment: (Int) -> Unit,
    navigateToDetail: (ContentDetail, ContentType) -> Unit,
    onDrawerClicked: () -> Unit = {}
) {
//    Scaffold(
//        modifier = modifier.fillMaxSize(),
//        snackbarHost = {
//            SnackbarHost(
//                hostState = appState.snackbarHostState,
//                modifier = Modifier.padding(8.dp),
//                snackbar = { snackbarData ->
//                    Snackbar(
//                        snackbarData,
//                    )
//                }
//            )
//        },
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(it)
//                .fillMaxSize()
//        ) {
////            if(navigationType == NavigationType.NAVIGATION_RAIL && !isUserSignedOut)
////            {
////                ApartmentNavigationRail(
////                    baseUIState = baseUIState,
////                    selectedDestination = selectedDestination,
////                    navigationContentPosition = navigationContentPosition,
////                    closeDetailScreen = closeDetailScreen,
////                    navigateToDestination = appState::navigateTo,
////                    setApartment=setApartment,
////                    onDrawerClicked = onDrawerClicked,
////                )
////            }
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    // TODO: remove color from this
//                    .background(MaterialTheme.colorScheme.tertiaryContainer)
//
//            ) {
                YkisNavHost(
                    modifier = Modifier.fillMaxSize(),
                    appState = appState,
                    baseUIState = baseUIState,
                    navController = navController,
                    contentType = contentType,
                    displayFeatures = displayFeatures,
                    navigationType = navigationType,
                    getApartments = getApartments,
                    closeDetailScreen = closeDetailScreen,
                    navigateToDestination = navigateToDestination,
                    setApartment = setApartment,
                    navigateToDetail = navigateToDetail,
                    onDrawerClicked = onDrawerClicked,
                    selectedDestination = selectedDestination
                )
//                if(navigationType == NavigationType.BOTTOM_NAVIGATION) {
//                    BottomNavigationBar(
//                        navigateToDestination = navigateToDestination,
//                        selectedDestination = selectedDestination,
//                    )
//            }
//        }
//    }
}

@Composable
private fun YkisNavHost(
    modifier: Modifier = Modifier,
    appState: YkisPamAppState,
    baseUIState: BaseUIState,
    navController: NavHostController,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    navigationType: NavigationType,
    getApartments: () -> Unit,
    closeDetailScreen: () -> Unit,
    navigateToDestination: (String) -> Unit,
    setApartment: (Int) -> Unit ,
    navigateToDetail: (ContentDetail, ContentType) -> Unit,
    onDrawerClicked: () -> Unit = {},
    selectedDestination: String


) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = LAUNCH_SCREEN,
    ) {
        YkisPamGraph(
            contentType,
            navigationType,
            displayFeatures,
            appState,
            baseUIState,
            getApartments,
            closeDetailScreen,
            navigateToDestination,
            setApartment,
            navigateToDetail,
            onDrawerClicked,
            selectedDestination
        )
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

