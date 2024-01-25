package com.ykis.ykispam.pam.screens.appartment

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddHome
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.ykis.ykispam.BaseUIState
import com.ykis.ykispam.R
import com.ykis.ykispam.YkisPamAppState
import com.ykis.ykispam.navigation.ADD_APARTMENT_SCREEN
import com.ykis.ykispam.navigation.ContentDetail
import com.ykis.ykispam.navigation.ContentType
import com.ykis.ykispam.navigation.NavigationType
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.pam.screens.appartment.content.DetailContent
import com.ykis.ykispam.pam.screens.appartment.content.ListContent


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
    getApartments:() ->Unit,
    setApartment: (Int) -> Unit ,
    navigateToDetail: (ContentDetail, ContentType) -> Unit,
    onDrawerClicked: () -> Unit = {},
    deleteApartment:(addressId: Int, restartApp: (String) -> Unit)->Unit,
) {
//    baseUiState called 5 times
    LaunchedEffect(key1 = baseUIState.apartments) {
        getApartments()
        if (contentType == ContentType.SINGLE_PANE && !baseUIState.isDetailOnlyOpen) {
            closeDetailScreen()
        }
    }

LaunchedEffect(key1 = baseUIState.addressId,key2 = baseUIState.apartments) {
    if(baseUIState.apartment == ApartmentEntity()&& baseUIState.apartments.isNotEmpty()){
        setApartment(baseUIState.apartments.first().addressId)
    }else {
        setApartment(baseUIState.addressId)
    }

    if (contentType == ContentType.SINGLE_PANE && !baseUIState.isDetailOnlyOpen) {
        closeDetailScreen()
    }
}
if (contentType == ContentType.DUAL_PANE) {
    TwoPane(
        first = {
            ListContent(
                modifier = Modifier.fillMaxSize(),
                contentType = contentType,
                appState = appState,
                baseUIState = baseUIState,
                deleteApartment = { deleteApartment(baseUIState.addressId, restartApp) },
                navigateToDetail = navigateToDetail,
            )
        },
        second = {
                DetailContent(
                    baseUIState = baseUIState,
                    contentType = contentType,
                    contentDetail = baseUIState.selectedContentDetail ?: ContentDetail.EMPTY,
                )

        },
        strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f, gapWidth = 16.dp),
        displayFeatures = displayFeatures
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
//                navigateBack = { viewModel.navigateBack(popUpScreen) }


        )
        // When we have bottom navigation we show FAB at the bottom end.
        if (navigationType == NavigationType.BOTTOM_NAVIGATION && !baseUIState.isDetailOnlyOpen) {
            FloatingActionButton(
                onClick = {
                    openScreen(ADD_APARTMENT_SCREEN)
                },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd),
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer         ,
            ) {
                Icon(
                    imageVector = Icons.Default.AddHome,
                    contentDescription = stringResource(id = R.string.add_appartment),
                )
            }

        }
    }

}
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

    ) {

    if (baseUIState.selectedContentDetail != null && baseUIState.isDetailOnlyOpen) {
        BackHandler {
            closeDetailScreen()
        }
        DetailContent(
            baseUIState = baseUIState,
            contentType = contentType,
            contentDetail = baseUIState.selectedContentDetail
        ) {
            closeDetailScreen()
        }

    } else {
        ListContent(
            modifier = modifier,
            contentType = contentType,
            appState = appState,
            baseUIState = baseUIState,
            deleteApartment = deleteApartment,
            navigateToDetail = navigateToDetail,
            onDrawerClicked = onDrawerClicked,
        )

    }
}



