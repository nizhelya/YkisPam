package com.ykis.ykispam.ui.screens.appartment

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.YkisPamAppState
import com.ykis.ykispam.ui.navigation.ContentDetail
import com.ykis.ykispam.ui.navigation.ContentType
import com.ykis.ykispam.ui.navigation.NavigationType
import com.ykis.ykispam.ui.screens.appartment.content.DetailContent
import com.ykis.ykispam.ui.screens.appartment.content.ListContent
import com.ykis.ykispam.ui.screens.bti.BtiPanelContent
import com.ykis.ykispam.ui.screens.family.FamilyContent
import com.ykis.ykispam.ui.screens.service.detail.ServicesContent


@Composable
fun ApartmentScreen(
    modifier: Modifier = Modifier,
    appState: YkisPamAppState,
    contentType: ContentType,
    baseUIState: BaseUIState,
    navigationType: NavigationType,
    displayFeatures: List<DisplayFeature>,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (ContentDetail) -> Unit,
    onDrawerClicked: () -> Unit = {},
    deleteApartment: () -> Unit,
    addressId: Int,
    apartmentViewModel: ApartmentViewModel,
) {

    LaunchedEffect(key1 = addressId, key2 = baseUIState.apartments) {
        if(baseUIState.apartments.isNotEmpty()) {
            if (addressId == 0) {
                apartmentViewModel.getApartment(baseUIState.apartments.first().addressId)
            } else {
                apartmentViewModel.getApartment(addressId)
            }
        }
        if (contentType == ContentType.SINGLE_PANE && !baseUIState.isDetailOnlyOpen) {
            closeDetailScreen()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.Center),
            visible = baseUIState.apartmentLoading,
            exit = fadeOut(tween(delayMillis = 300)),
            enter = fadeIn(tween(delayMillis = 300))
        ) {
            CircularProgressIndicator()
        }
    }
    AnimatedVisibility(
        visible = !baseUIState.apartmentLoading,
        exit = fadeOut(tween(delayMillis = 300)),
        enter = fadeIn(tween(delayMillis = 300))
    ) {
        if (contentType == ContentType.DUAL_PANE) {
            DualPanelContent(
                appState = appState,
                baseUIState = baseUIState,
                deleteApartment = deleteApartment,
                navigateToDetail = navigateToDetail,
                displayFeatures = displayFeatures,
                closeDetailScreen = closeDetailScreen,
                apartmentViewModel = apartmentViewModel
            )
        } else {
            Box(modifier = modifier.fillMaxSize()) {
                SinglePanelContent(
                    modifier = Modifier.fillMaxSize(),
                    contentType = contentType,
                    appState = appState,
                    baseUIState = baseUIState,
                    closeDetailScreen = closeDetailScreen,
                    deleteApartment = { deleteApartment() },
                    navigateToDetail = navigateToDetail,
                    onDrawerClicked = onDrawerClicked,
                    navigationType = navigationType,
                    apartmentViewModel = apartmentViewModel
                )
            }
        }
    }

}

@Composable
fun DualPanelContent(
    appState: YkisPamAppState,
    baseUIState: BaseUIState,
    deleteApartment: () -> Unit,
    navigateToDetail: (ContentDetail) -> Unit,
    displayFeatures: List<DisplayFeature>,
    apartmentViewModel: ApartmentViewModel,
    closeDetailScreen: () -> Unit
    ) {
    val contentDetail = baseUIState.selectedContentDetail
    TwoPane(
        modifier = Modifier.fillMaxSize(),
        first = {
            ListContent(
                appState = appState,
                baseUIState = baseUIState,
                deleteApartment = { deleteApartment() },
                navigateToDetail = navigateToDetail,
                navigationType = NavigationType.PERMANENT_NAVIGATION_DRAWER
            )
        },
        second = {
            DetailContent(
                modifier = Modifier.padding(horizontal = 24.dp),
                baseUIState = baseUIState,
                contentType = ContentType.DUAL_PANE,
                onBackPressed = {apartmentViewModel.closeDetailScreen()},
                contentDetail = baseUIState.selectedContentDetail,
                showDetail = baseUIState.showDetail
            ) {
                when (contentDetail) {
                    ContentDetail.BTI -> BtiPanelContent(
                        baseUIState = baseUIState,
                        viewModel = apartmentViewModel
                    )

                    ContentDetail.FAMILY -> FamilyContent(
                        baseUIState = baseUIState,
                    )

                    ContentDetail.OSBB -> ServicesContent(
                        contentDetail = contentDetail,
                        baseUIState = baseUIState,
                    )

                    ContentDetail.WATER_SERVICE -> ServicesContent(
                        contentDetail = contentDetail,
                        baseUIState = baseUIState,
                    )

                    ContentDetail.WARM_SERVICE -> ServicesContent(
                        contentDetail = contentDetail,
                        baseUIState = baseUIState,
                    )

                    ContentDetail.GARBAGE_SERVICE -> ServicesContent(
                        contentDetail = contentDetail,
                        baseUIState = baseUIState,
                    )

                    ContentDetail.PAYMENTS -> ServicesContent(
                        contentDetail = contentDetail,
                        baseUIState = baseUIState,
                    )
                }
            }

        },
        strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f),
        displayFeatures = displayFeatures
    )
}

@Composable
fun SinglePanelContent(
    modifier: Modifier = Modifier,
    contentType: ContentType,
    appState: YkisPamAppState,
    baseUIState: BaseUIState,
    closeDetailScreen: () -> Unit,
    deleteApartment: () -> Unit,
    navigateToDetail: (ContentDetail) -> Unit,
    onDrawerClicked: () -> Unit,
    navigationType: NavigationType,
    apartmentViewModel: ApartmentViewModel
    ) {

    val contentDetail = baseUIState.selectedContentDetail
    if (baseUIState.showDetail) {
        BackHandler {
            closeDetailScreen()
        }
        DetailContent(
            baseUIState = baseUIState,
            contentType = contentType,
            contentDetail = baseUIState.selectedContentDetail,
            onBackPressed = closeDetailScreen,
            showDetail = baseUIState.showDetail
        )
        {
            when (contentDetail) {
                ContentDetail.BTI -> BtiPanelContent(
                    baseUIState = baseUIState,
                    viewModel = apartmentViewModel
                )

                ContentDetail.FAMILY -> FamilyContent(
                    baseUIState = baseUIState,
                )

                ContentDetail.OSBB -> ServicesContent(
                    contentDetail = contentDetail,
                    baseUIState = baseUIState,
                )

                ContentDetail.WATER_SERVICE -> ServicesContent(
                    contentDetail = contentDetail,
                    baseUIState = baseUIState,
                )

                ContentDetail.WARM_SERVICE -> ServicesContent(
                    contentDetail = contentDetail,
                    baseUIState = baseUIState,
                )

                ContentDetail.GARBAGE_SERVICE -> ServicesContent(
                    contentDetail = contentDetail,
                    baseUIState = baseUIState,
                )

                ContentDetail.PAYMENTS -> ServicesContent(
                    contentDetail = contentDetail,
                    baseUIState = baseUIState,
                )
            }
        }
    } else {
        ListContent(
            modifier = modifier,
            appState = appState,
            baseUIState = baseUIState,
            deleteApartment = deleteApartment,
            navigateToDetail = navigateToDetail,
            onDrawerClicked = onDrawerClicked,
            navigationType = navigationType
        )

    }
}



