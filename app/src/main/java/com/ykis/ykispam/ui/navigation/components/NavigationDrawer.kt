package com.ykis.ykispam.ui.navigation.components

import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.ui.BaseUIState

@Composable
fun ModalNavigationDrawerContent(
    baseUIState: BaseUIState,
    selectedDestination: String,
    navigateToDestination: (String) -> Unit,
    onMenuClick: () -> Unit = {},
    navigateToApartment: (Int) -> Unit,
    isApartmentsEmpty : Boolean
) {
    ModalDrawerSheet {
        ApartmentNavigationRail(
            baseUIState = baseUIState,
            selectedDestination = selectedDestination,
            isRailExpanded = true ,
            onMenuClick = onMenuClick,
            navigateToDestination = navigateToDestination,
            navigateToApartment = navigateToApartment,
            railWidth = 320.dp,
            maxApartmentListHeight = 194.dp,
            isApartmentsEmpty = isApartmentsEmpty
        )
    }
}