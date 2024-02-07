package com.ykis.ykispam.pam.screens.appartment

import android.util.Log
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
import com.ykis.ykispam.navigation.AddApartmentScreen
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
    addressId: Int
) {
    Log.d("argument_test", "address_id : $addressId")
//    baseUiState called 5 times
    LaunchedEffect(key1 = baseUIState.apartments) {
        getApartments()
        if (contentType == ContentType.SINGLE_PANE && !baseUIState.isDetailOnlyOpen) {
            closeDetailScreen()
        }
    }
    Log.d("navigation_test", "addressId:$addressId")
LaunchedEffect(key1 = addressId,key2 = baseUIState.apartments) {
    if(baseUIState.apartment == ApartmentEntity() && baseUIState.apartments.isNotEmpty()){
        setApartment(baseUIState.apartments.first().addressId)
    }else {
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
            navigationType=navigationType
        )
        // When we have bottom navigation we show FAB at the bottom end.
        if (navigationType == NavigationType.BOTTOM_NAVIGATION && !baseUIState.isDetailOnlyOpen) {
            FloatingActionButton(
                onClick = {
                    openScreen(AddApartmentScreen.route)
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
fun DualPanelContent(
    appState: YkisPamAppState,
    baseUIState: BaseUIState,
    deleteApartment:(addressId: Int, restartApp: (String) -> Unit)->Unit,
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
                baseUIState = baseUIState,
                contentType = contentType,
                contentDetail = baseUIState.selectedContentDetail ?: ContentDetail.EMPTY,
            ){
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



