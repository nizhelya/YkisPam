package com.ykis.ykispam.ui.screens.appartment

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.ykis.ykispam.R
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.YkisPamAppState
import com.ykis.ykispam.ui.components.appbars.ApartmentTopAppBar
import com.ykis.ykispam.ui.navigation.ContentType
import com.ykis.ykispam.ui.navigation.INFO_APARTMENT_TAB_ITEM
import com.ykis.ykispam.ui.navigation.NavigationType
import com.ykis.ykispam.ui.screens.bti.BtiPanelContent
import com.ykis.ykispam.ui.screens.family.FamilyContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoApartmentScreen(
    modifier: Modifier = Modifier,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    baseUIState: BaseUIState,
    apartmentViewModel: ApartmentViewModel,
    appState: YkisPamAppState,
    deleteApartment: () -> Unit,
    onDrawerClicked: () -> Unit,
    navigationType: NavigationType,
    addressId: Int
) {
    var selectedTab by rememberSaveable {
        mutableIntStateOf(0)
    }
    LaunchedEffect(key1 = addressId, key2 = baseUIState.apartments) {
        if (baseUIState.apartments.isNotEmpty()) {
            if (addressId == 0) {
                apartmentViewModel.getApartment(baseUIState.apartments.first().addressId)
            } else {
                apartmentViewModel.getApartment(addressId)
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        ApartmentTopAppBar(
            appState = appState,
            apartment = baseUIState.apartment,
            isButtonAction = baseUIState.apartments.isNotEmpty(),
            onButtonAction = { deleteApartment() },
            onButtonPressed = { onDrawerClicked() },
            navigationType = navigationType
        )
        if (contentType == ContentType.DUAL_PANE) {
            DualPanelContentNew(
                appState = appState,
                baseUIState = baseUIState,
                displayFeatures = displayFeatures,
                apartmentViewModel = apartmentViewModel
            )
        } else {
            PrimaryTabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.background
            ) {
                INFO_APARTMENT_TAB_ITEM.forEachIndexed { index, tabItem ->
                    LeadingIconTab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(text = stringResource(tabItem.titleId))
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedTab) tabItem.selectedIcon else tabItem.unselectedIcon,
                                contentDescription = stringResource(tabItem.titleId)
                            )
                        }
                    )

                }
            }

            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                AnimatedContent(
                    targetState = selectedTab,
                    transitionSpec = {
                        fadeIn(
                            animationSpec = tween(600, easing = EaseIn)
                        ).togetherWith(
                            fadeOut(
                                animationSpec = tween(600, easing = EaseOut)
                            )
                        )
                    },
                    label = "",
                ) { targetState ->
                    when (targetState) {
                        0 -> BtiPanelContent(
                            baseUIState = baseUIState, viewModel = apartmentViewModel
                        )

                        else -> FamilyContent(baseUIState = baseUIState)
                    }
                }
            }
        }

    }
}

@Composable
fun DualPanelContentNew(
    appState: YkisPamAppState,
    baseUIState: BaseUIState,
    displayFeatures: List<DisplayFeature>,
    apartmentViewModel: ApartmentViewModel

) {
    TwoPane(
        modifier = Modifier.fillMaxSize(),
        first = {
            Column {
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                )
                {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Info",
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = stringResource(id = R.string.bti),
                        style = MaterialTheme.typography.bodyLarge

                    )
                }
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    BtiPanelContent(
                        baseUIState = baseUIState,
                        viewModel = apartmentViewModel
                    )
                }
            }


        },
        second = {
            Column {
                Row(modifier = Modifier
                    .fillMaxWidth(),
//                    .padding(bottom = 4.dp),
                    horizontalArrangement = Arrangement.Center
                )
                {
                    Icon(
                        imageVector = Icons.Filled.Group,
                        contentDescription = "Info",
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = stringResource(id = R.string.list_family),
                        style = MaterialTheme.typography.bodyLarge

                    )
                }
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    FamilyContent(baseUIState = baseUIState)

                }
            }
        },
        strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f),
        displayFeatures = displayFeatures
    )
}

