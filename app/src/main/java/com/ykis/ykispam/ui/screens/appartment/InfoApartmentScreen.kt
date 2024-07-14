package com.ykis.ykispam.ui.screens.appartment

import android.util.Log
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
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.ykis.ykispam.R
import com.ykis.ykispam.core.composable.DialogCancelButton
import com.ykis.ykispam.core.composable.DialogConfirmButton
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.YkisPamAppState
import com.ykis.ykispam.ui.components.appbars.DefaultAppBar
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
) {
    var selectedTab by rememberSaveable {
        mutableIntStateOf(0)
    }
    var showWarningDialog by remember { mutableStateOf(false) }
    if (showWarningDialog) {

        AlertDialog(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(48.dp),
            title = { Text(stringResource(R.string.title_delete_appartment)) },
            dismissButton = { DialogCancelButton(R.string.cancel) { showWarningDialog = false } },
            confirmButton = {
                DialogConfirmButton(R.string.title_delete_appartment) {
                    deleteApartment()
                    showWarningDialog = false
                }
            },
            onDismissRequest = { showWarningDialog = false }
        )


    }
    LaunchedEffect(key1 = baseUIState.addressId) {
        if (baseUIState.addressId == 0) {
            Log.d("debug_test1", "baseUIState.addressId == 0")
            apartmentViewModel.getApartment(baseUIState.apartments.first().addressId)
        } else {
            Log.d("debug_test1", "else")
            apartmentViewModel.getApartment(baseUIState.addressId)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DefaultAppBar(
            title = baseUIState.address,
            canNavigateBack = false,
            onDrawerClick = { onDrawerClicked() },
            navigationType = navigationType,
            actionButton = {
                IconButton(
                    onClick = {
                        showWarningDialog = true
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(id = R.string.delete_appartment),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        )
        if (contentType == ContentType.DUAL_PANE) {
            InfoScreenDualPanelContent(
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
fun InfoScreenDualPanelContent(
    appState: YkisPamAppState,
    baseUIState: BaseUIState,
    displayFeatures: List<DisplayFeature>,
    apartmentViewModel: ApartmentViewModel

) {
    TwoPane(
        modifier = Modifier.fillMaxSize(),
        first = {
            Column {
                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                )
                {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Info",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = stringResource(id = R.string.bti),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
//                HorizontalDivider()
                BtiPanelContent(
                    baseUIState = baseUIState,
                    viewModel = apartmentViewModel
                )
            }


        },
        second = {
            Column {
                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                )
                {
                    Icon(
                        imageVector = Icons.Filled.Group,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = stringResource(id = R.string.list_family),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
//                HorizontalDivider()
                FamilyContent(baseUIState = baseUIState)
            }
        },
        strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f),
        displayFeatures = displayFeatures
    )
}

