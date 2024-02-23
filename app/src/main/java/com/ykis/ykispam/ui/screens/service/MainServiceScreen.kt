package com.ykis.ykispam.ui.screens.service

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.layout.DisplayFeature
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.components.BaseDualPanelContent
import com.ykis.ykispam.ui.components.appbars.DetailAppBar
import com.ykis.ykispam.ui.navigation.ContentDetail
import com.ykis.ykispam.ui.navigation.ContentType
import com.ykis.ykispam.ui.navigation.NavigationType
import com.ykis.ykispam.ui.screens.appartment.content.DetailContent
import com.ykis.ykispam.ui.screens.service.list.ServiceListScreen
import com.ykis.ykispam.ui.screens.service.list.TotalDebtState

@Composable
fun MainServiceScreen(
    modifier: Modifier = Modifier,
    viewModel : ServiceViewModel = hiltViewModel(),
    baseUIState: BaseUIState,
    navigationType: NavigationType,
    displayFeature: List<DisplayFeature>,
    contentType: ContentType,
    onDrawerClick :()->Unit,
) {
    val totalDebtState by viewModel.totalDebtState.collectAsStateWithLifecycle()
    val contentDetail : ContentDetail = totalDebtState.serviceDetail
    if(contentType==ContentType.DUAL_PANE){
        BaseDualPanelContent(
            modifier = modifier,
            displayFeatures = displayFeature ,
            firstScreen = {
                ServiceListScreen(
                    baseUIState =baseUIState ,
                    navigationType = navigationType,
                    onDrawerClick = onDrawerClick,
                    totalDebtState = totalDebtState,
                    getTotalServiceDebt = { params -> viewModel.getTotalServiceDebt(params = params)},
                    onServiceClick = {contentDetail -> viewModel.setContentDetail(contentDetail)}
                )
                          },
            secondScreen = {
                DetailContent(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    baseUIState = baseUIState,
                    contentType = ContentType.DUAL_PANE,
                    contentDetail = totalDebtState.serviceDetail,
                    onBackPressed = {viewModel.setContentDetail(ContentDetail.EMPTY)},
                    detailContent = {
                            ServicesContent(
                                contentDetail = contentDetail,
                                baseUIState = baseUIState
                            )
                    }
                )

            }
        )
    }else{
        SinglePanelService(
            contentDetail = contentDetail,
            baseUIState = baseUIState,
            navigationType = navigationType,
            onDrawerClick = onDrawerClick,
            totalDebtState = totalDebtState,
            viewModel = viewModel,
            contentType = contentType
        )
    }

}

@Composable
fun SinglePanelService(
    modifier: Modifier = Modifier,
    contentDetail: ContentDetail,
    baseUIState: BaseUIState,
    navigationType: NavigationType,
    onDrawerClick: () -> Unit,
    totalDebtState: TotalDebtState,
    viewModel: ServiceViewModel,
    contentType: ContentType
) {

    if( contentDetail == ContentDetail.EMPTY){
        ServiceListScreen(
            baseUIState =baseUIState ,
            navigationType = navigationType,
            onDrawerClick = onDrawerClick,
            totalDebtState = totalDebtState,
            getTotalServiceDebt = { params -> viewModel.getTotalServiceDebt(params = params)},
            onServiceClick = { viewModel.setContentDetail(it)}
        )
    }

    AnimatedVisibility(
        visible = contentDetail!=ContentDetail.EMPTY
    ) {
        Column {
            DetailAppBar(
                contentType = contentType,
                baseUIState = baseUIState,
                contentDetail = contentDetail
            )
             {
                viewModel.setContentDetail(ContentDetail.EMPTY)
            }
            ServicesContent(
                contentDetail = contentDetail,
                baseUIState = baseUIState
            )
        }
    }

}