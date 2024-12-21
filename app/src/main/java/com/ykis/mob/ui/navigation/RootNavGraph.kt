package com.ykis.mob.ui.navigation

import android.util.Log
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import com.ykis.mob.domain.UserRole
import com.ykis.mob.ui.rememberAppState
import com.ykis.mob.ui.screens.appartment.ApartmentViewModel
import com.ykis.mob.ui.screens.chat.ChatViewModel
import com.ykis.mob.ui.screens.launch.LaunchScreen
import com.ykis.mob.ui.screens.service.payment.choice.WebView

object Graph {
    const val AUTHENTICATION = "auth_graph"
    const val APARTMENT = "apartment_graph"
}
@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    chatViewModel : ChatViewModel = hiltViewModel(),
    apartmentViewModel : ApartmentViewModel = hiltViewModel(),
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
    val selectedUser by chatViewModel.selectedUser.collectAsStateWithLifecycle()
    val baseUIState by apartmentViewModel.uiState.collectAsStateWithLifecycle()
    val selectedImageUri by chatViewModel.selectedImageUri.collectAsStateWithLifecycle()
    val chatUid = remember(baseUIState, selectedUser.uid) {
        if (baseUIState.userRole == UserRole.StandardUser) {
            baseUIState.uid.toString()
        } else selectedUser.uid
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
                    viewModel = apartmentViewModel
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
                    onMenuClick = { isRailExpanded = !isRailExpanded },
                    navigateToWebView = {
                        uri->
                        Log.d("wv_test" , uri.toString())
                        navController.navigateToWebView(uri)
                    },
                    chatViewModel = chatViewModel,
                    apartmentViewModel = apartmentViewModel,
                    baseUIState = baseUIState
                )
            }

            composable(
                route = WebViewScreen.routeWithArgs,
                arguments = WebViewScreen.arguments
            ){
                    navBackStackEntry->
                val uri =
                    navBackStackEntry.arguments?.getString(WebViewScreen.link)
                WebView(
                    uri = uri.toString()
                )
            }
            composable(ChatScreen.route){
                com.ykis.mob.ui.screens.chat.ChatScreen(
                    userEntity = selectedUser,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    chatViewModel = chatViewModel,
                    baseUIState = baseUIState,
                    navigateToSendImageScreen = {
                        navController.navigate(SendImageScreen.route)
                    },
                    chatUid = chatUid,
                    navigateToCameraScreen = {
                        navController.navigate(CameraScreen.route)
                    },
                    navigateToImageDetailScreen = {
                        chatViewModel.setSelectedMessage(it)
                        navController.navigate(ImageDetailScreen.route)
                    }
                )
            }

            composable(SendImageScreen.route){
                val messageText by chatViewModel.messageText.collectAsStateWithLifecycle()
                val isLoadingAfterSending by chatViewModel.isLoadingAfterSending.collectAsStateWithLifecycle()

                com.ykis.mob.ui.screens.chat.SendImageScreen(
                    imageUri = selectedImageUri,
                    messageText = messageText,
                    onMessageTextChanged = {
                        chatViewModel.onMessageTextChanged(it)
                    },
                    navigateBack = {
                        navController.navigateUp()
                    },
                    onSent = {
                        chatViewModel.uploadPhotoAndSendMessage(
                              chatUid = chatUid,
                              senderUid = baseUIState.uid.toString(),
                              senderDisplayedName = if (baseUIState.displayName.isNullOrEmpty()) baseUIState.email.toString() else baseUIState.displayName.toString(),
                              senderLogoUrl = baseUIState.photoUrl,
                              role = baseUIState.userRole,
                              senderAddress = if(baseUIState.userRole == UserRole.StandardUser) baseUIState.address else "",
                              onComplete = {
                                  navController.navigate(ChatScreen.route){
                                      popUpTo(ChatScreen.route){
                                          inclusive = true
                                      }
                                  }
                              },
                            osbbId = baseUIState.osmdId,
                            // TODO: remove empty list 
                            recipientTokens = emptyList()
                          )
                    },
                    isLoadingAfterSending = isLoadingAfterSending
                )
            }
            composable(CameraScreen.route){
                com.ykis.mob.ui.screens.chat.CameraScreen(
                    navController = navController,
                    setImageUri = {
                        chatViewModel.setSelectedImageUri(it)
                    }
                )
            }
            composable(ImageDetailScreen.route){
                val selectedMessage by chatViewModel.selectedMessage.collectAsStateWithLifecycle()
                com.ykis.mob.ui.screens.chat.ImageDetailScreen(
                    navigateUp = {navController.navigateUp()},
                    messageEntity = selectedMessage
                )
            }
        }
    }
}

private fun NavHostController.navigateToWebView(uri: String) {
    this.navigate("${WebViewScreen.route}/$uri")
}






