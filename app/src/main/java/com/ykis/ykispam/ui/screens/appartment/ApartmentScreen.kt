package com.ykis.ykispam.ui.screens.appartment

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.ykis.ykispam.ui.navigation.ContentDetail
import com.ykis.ykispam.ui.navigation.ContentType
import com.ykis.ykispam.ui.navigation.NavigationType
import com.ykis.ykispam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.ui.screens.appartment.content.DetailContent
import com.ykis.ykispam.ui.screens.appartment.content.ListContent
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.YkisPamAppState


@Composable
fun ApartmentScreen(
    modifier: Modifier = Modifier,
    openScreen: (String) -> Unit,
    restartApp: (String) -> Unit,
    appState: YkisPamAppState,
    contentType: ContentType,
    baseUIState: BaseUIState,
    navigationType: NavigationType,
    displayFeatures: List<DisplayFeature>,
    closeDetailScreen: () -> Unit,
    getApartments: () -> Unit,
    setApartment: (Int) -> Unit,
    navigateToDetail: (ContentDetail, ContentType) -> Unit,
    onDrawerClicked: () -> Unit = {},
    deleteApartment: (addressId: Int, restartApp: (String) -> Unit) -> Unit,
    addressId: Int
) {
//    baseUiState called 5 times
    LaunchedEffect(key1 = baseUIState.apartments) {
        getApartments()
        if (contentType == ContentType.SINGLE_PANE && !baseUIState.isDetailOnlyOpen) {
            closeDetailScreen()
        }
    }
    LaunchedEffect(key1 = addressId, key2 = baseUIState.apartments) {
        if (baseUIState.apartment == ApartmentEntity() && baseUIState.apartments.isNotEmpty()) {
            setApartment(baseUIState.apartments.first().addressId)
        } else {
            setApartment(addressId)
        }

        if (contentType == ContentType.SINGLE_PANE && !baseUIState.isDetailOnlyOpen) {
            closeDetailScreen()
        }
    }
    if (contentType == ContentType.DUAL_PANE) {
        DualPanelContent(
            appState = appState,
            baseUIState = baseUIState,
            deleteApartment = deleteApartment,
            restartApp = restartApp,
            navigateToDetail = navigateToDetail,
            navigationType = navigationType,
            contentType = contentType,
            displayFeatures = displayFeatures,
            closeDetailScreen = closeDetailScreen
        )
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            SinglePanelContent(
                modifier = Modifier.fillMaxSize(),
                contentType = contentType,
                appState = appState,
                baseUIState = baseUIState,
                closeDetailScreen = closeDetailScreen,
                deleteApartment = { deleteApartment(baseUIState.addressId, restartApp) },
                navigateToDetail = navigateToDetail,
                onDrawerClicked = onDrawerClicked,
                navigationType = navigationType
            )
        }
    }
}

@Composable
fun DualPanelContent(
    appState: YkisPamAppState,
    baseUIState: BaseUIState,
    deleteApartment: (addressId: Int, restartApp: (String) -> Unit) -> Unit,
    restartApp: (String) -> Unit,
    navigateToDetail: (ContentDetail, ContentType) -> Unit,
    //todo always Dual_pane
    navigationType: NavigationType,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    closeDetailScreen: () -> Unit,

    ) {
    TwoPane(
        modifier = Modifier.fillMaxSize(),
        first = {
            ListContent(
                appState = appState,
                baseUIState = baseUIState,
                deleteApartment = { deleteApartment(baseUIState.addressId, restartApp) },
                navigateToDetail = navigateToDetail,
                navigationType = navigationType
            )
        },
        second = {
            DetailContent(
                modifier = Modifier.padding(horizontal = 24.dp),
                baseUIState = baseUIState,
                contentType = contentType,
                contentDetail = baseUIState.selectedContentDetail ?: ContentDetail.EMPTY,
            ) {
                closeDetailScreen()
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
    navigateToDetail: (ContentDetail, ContentType) -> Unit,
    onDrawerClicked: () -> Unit,
    navigationType: NavigationType
) {

    if (baseUIState.selectedContentDetail != null && baseUIState.isDetailOnlyOpen) {
        BackHandler {
            closeDetailScreen()
        }
        DetailContent(
            baseUIState = baseUIState,
            contentType = contentType,
            contentDetail = baseUIState.selectedContentDetail
        )
        {
            closeDetailScreen()
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



