package com.ykis.ykispam.ui.screens.service

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.layout.DisplayFeature
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.components.BaseDualPanelContent
import com.ykis.ykispam.ui.components.DetailPanel
import com.ykis.ykispam.ui.components.appbars.DetailAppBar
import com.ykis.ykispam.ui.navigation.ContentDetail
import com.ykis.ykispam.ui.navigation.ContentType
import com.ykis.ykispam.ui.navigation.NavigationType
import com.ykis.ykispam.ui.screens.service.detail.ServicesContent
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
                DetailPanel(
                    baseUIState = baseUIState,
                    contentType = ContentType.DUAL_PANE,
                    contentDetail = totalDebtState.serviceDetail,
                    onBackPressed = {viewModel.closeContentDetail()},
                    showDetail = totalDebtState.showDetail,
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

    if(!totalDebtState.showDetail){
        ServiceListScreen(
            baseUIState =baseUIState ,
            navigationType = navigationType,
            onDrawerClick = onDrawerClick,
            totalDebtState = totalDebtState,
            getTotalServiceDebt = { params -> viewModel.getTotalServiceDebt(params = params)},
            onServiceClick = {content -> viewModel.setContentDetail(contentDetail = content)}
        )
    }

    AnimatedVisibility(
        visible = totalDebtState.showDetail
    ) {
        BackHandler {
            viewModel.closeContentDetail()
        }
        Column(
            modifier = modifier.background(
                MaterialTheme.colorScheme.background
            )
        ){
            DetailAppBar(
                contentType = contentType,
                baseUIState = baseUIState,
                contentDetail = contentDetail
            )
             {
                viewModel.closeContentDetail()
            }
            ServicesContent(
                contentDetail = contentDetail,
                baseUIState = baseUIState
            )
        }
    }

}