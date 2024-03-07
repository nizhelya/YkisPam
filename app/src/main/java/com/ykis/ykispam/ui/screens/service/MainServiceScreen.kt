package com.ykis.ykispam.ui.screens.service

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.layout.DisplayFeature
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.components.BaseDualPanelContent
import com.ykis.ykispam.ui.components.DetailPanel
import com.ykis.ykispam.ui.navigation.ContentDetail
import com.ykis.ykispam.ui.navigation.ContentType
import com.ykis.ykispam.ui.navigation.NavigationType
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
                    setContentDetail = { contentDetail -> viewModel.setContentDetail(contentDetail)}
                )
                          },
            secondScreen = {
                DetailPanel(
                    showDetail = totalDebtState.showDetail,
                    detailContent = {

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
) {

    if(!totalDebtState.showDetail){
        ServiceListScreen(
            baseUIState =baseUIState ,
            navigationType = navigationType,
            onDrawerClick = onDrawerClick,
            totalDebtState = totalDebtState,
            getTotalServiceDebt = { params -> viewModel.getTotalServiceDebt(params = params)},
            setContentDetail = { content -> viewModel.setContentDetail(contentDetail = content)}
        )
    }

    AnimatedVisibility(
        visible = totalDebtState.showDetail
    ) {
        BackHandler {
            viewModel.closeContentDetail()
        }
        ServiceDetailScreen(
            navigationType = navigationType,
            viewModel = viewModel,
            contentDetail = contentDetail,
            baseUIState =baseUIState
        )
    }

}