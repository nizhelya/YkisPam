package com.ykis.ykispam.ui.screens.appartment

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
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
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.YkisPamAppState
import com.ykis.ykispam.ui.components.appbars.ApartmentTopAppBar
import com.ykis.ykispam.ui.navigation.INFO_APARTMENT_TAB_ITEM
import com.ykis.ykispam.ui.navigation.NavigationType
import com.ykis.ykispam.ui.screens.bti.BtiPanelContent
import com.ykis.ykispam.ui.screens.family.FamilyContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoApartmentScreen(
    modifier: Modifier = Modifier,
    baseUIState: BaseUIState,
    apartmentViewModel : ApartmentViewModel,
    appState: YkisPamAppState,
    deleteApartment: () -> Unit ,
    onDrawerClicked: () -> Unit ,
    navigationType: NavigationType,
    addressId : Int
    ) {
    var selectedTab by rememberSaveable{
        mutableIntStateOf(0)
    }
    LaunchedEffect(key1 = addressId, key2 = baseUIState.apartments) {
        if(baseUIState.apartments.isNotEmpty()) {
            if (addressId == 0) {
                apartmentViewModel.getApartment(baseUIState.apartments.first().addressId)
            } else {
                apartmentViewModel.getApartment(addressId)
            }
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        ApartmentTopAppBar(
            appState =appState ,
            apartment = baseUIState.apartment,
            isButtonAction = baseUIState.apartments.isNotEmpty(),
            onButtonAction = {deleteApartment()},
            onButtonPressed = { onDrawerClicked() },
            navigationType = navigationType
        )
        PrimaryTabRow(
            selectedTabIndex = selectedTab ,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            INFO_APARTMENT_TAB_ITEM.forEachIndexed { index, tabItem ->
                LeadingIconTab(
                    selected = selectedTab==index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(text = stringResource( tabItem.titleId))
                    },
                    icon = {
                        Icon (
                           imageVector = if(index == selectedTab) tabItem.selectedIcon else tabItem.unselectedIcon,
                            contentDescription = stringResource( tabItem.titleId)
                        )
                    }
                )

            }
        }

            Box(
                modifier=modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
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
                        0 ->  BtiPanelContent(
                            baseUIState =baseUIState , viewModel = apartmentViewModel
                        )
                        else -> FamilyContent(baseUIState = baseUIState)
                    }
                }
            }
            
        }

}
