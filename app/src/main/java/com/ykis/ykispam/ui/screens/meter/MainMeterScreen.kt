package com.ykis.ykispam.ui.screens.meter

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.layout.DisplayFeature
import com.ykis.ykispam.domain.heat.meter.HeatMeterEntity
import com.ykis.ykispam.domain.water.meter.WaterMeterEntity
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.components.BaseDualPanelContent
import com.ykis.ykispam.ui.components.appbars.DetailAppBar
import com.ykis.ykispam.ui.navigation.ContentDetail
import com.ykis.ykispam.ui.navigation.ContentType
import com.ykis.ykispam.ui.navigation.NavigationType
import com.ykis.ykispam.ui.screens.appartment.content.DetailContent
import com.ykis.ykispam.ui.screens.appartment.content.EmptyDetail
import com.ykis.ykispam.ui.screens.meter.heat.HeatMeterState
import com.ykis.ykispam.ui.screens.meter.water.WaterMeterDetail
import com.ykis.ykispam.ui.screens.meter.water.WaterMeterState

@Composable
fun MainMeterScreen(
        modifier: Modifier = Modifier,
        viewModel : MeterViewModel= hiltViewModel(),
        baseUIState: BaseUIState,
        navigationType:NavigationType,
        onDrawerClick: () ->Unit,
        contentType: ContentType,
        displayFeature: List<DisplayFeature>
    ) {
    val waterMeterState by viewModel.waterMeterState.collectAsStateWithLifecycle()
    val heatMeterState by viewModel.heatMeterState.collectAsStateWithLifecycle()
    val showDetail by viewModel.showDetail.collectAsStateWithLifecycle()
    val contentDetail by viewModel.contentDetail.collectAsStateWithLifecycle()
    if(contentType==ContentType.DUAL_PANE){
        BaseDualPanelContent(
            displayFeatures = displayFeature ,
            firstScreen = {
                MeterListScreen(
                    baseUIState = baseUIState,
                    navigationType = navigationType,
                    onDrawerClick = onDrawerClick,
                    viewModel = viewModel,
                    onWaterMeterClick = {waterMeter-> viewModel.setWaterMeterDetail(waterMeterEntity=waterMeter)},
                    onHeatMeterClick = {heatMeter -> viewModel.setHeatMeterDetail(heatMeterEntity = heatMeter)},
                    waterMeterState = waterMeterState,
                    heatMeterState = heatMeterState
                )
                          },
            secondScreen = {
                DetailContent(
                    baseUIState = baseUIState,
                    contentType = contentType,
                    contentDetail = contentDetail,
                    onBackPressed = { viewModel.closeContentDetail()},
                    showDetail = showDetail
                ) {
                }
            }
        )
    }else SinglePanelMeter(
        contentDetail = contentDetail,
        baseUIState = baseUIState,
        navigationType = navigationType,
        onDrawerClick = onDrawerClick,
        showDetail = showDetail,
        viewModel = viewModel,
        contentType = contentType,
        waterMeterState = waterMeterState,
        heatMeterState =heatMeterState ,
        onWaterMeterClick = {waterMeter-> viewModel.setWaterMeterDetail(waterMeterEntity=waterMeter)},
        onHeatMeterClick = {heatMeter -> viewModel.setHeatMeterDetail(heatMeterEntity = heatMeter)}
    )
}


@Composable
fun SinglePanelMeter(
    modifier: Modifier = Modifier,
    contentDetail: ContentDetail,
    baseUIState: BaseUIState,
    navigationType: NavigationType,
    onDrawerClick: () -> Unit,
    showDetail : Boolean,
    viewModel: MeterViewModel,
    contentType: ContentType,
    waterMeterState: WaterMeterState,
    heatMeterState: HeatMeterState,
    onWaterMeterClick : (WaterMeterEntity) ->Unit,
    onHeatMeterClick : (HeatMeterEntity) ->Unit,
) {

    if(!showDetail){
        MeterListScreen(
            baseUIState =baseUIState ,
            navigationType = navigationType,
            onDrawerClick = onDrawerClick,
            viewModel = viewModel,
            onHeatMeterClick = onHeatMeterClick,
            onWaterMeterClick = onWaterMeterClick,
            waterMeterState = waterMeterState,
            heatMeterState = heatMeterState
        )
    }

    AnimatedVisibility(
        visible = showDetail
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
            when(contentDetail){
                ContentDetail.WATER_METER -> {
                    WaterMeterDetail(waterMeterEntity = waterMeterState.selectedWaterMeter)
                }
                ContentDetail.HEAT_METER -> Text(text = heatMeterState.selectedHeatMeter.model)

                else -> EmptyDetail()
            }
        }
    }

}
