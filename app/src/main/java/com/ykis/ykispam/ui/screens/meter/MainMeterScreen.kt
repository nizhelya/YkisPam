package com.ykis.ykispam.ui.screens.meter

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.layout.DisplayFeature
import com.ykis.ykispam.domain.heat.meter.HeatMeterEntity
import com.ykis.ykispam.domain.water.meter.WaterMeterEntity
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.components.BaseDualPanelContent
import com.ykis.ykispam.ui.components.DetailContent
import com.ykis.ykispam.ui.components.EmptyDetail
import com.ykis.ykispam.ui.components.appbars.DetailAppBar
import com.ykis.ykispam.ui.navigation.ContentDetail
import com.ykis.ykispam.ui.navigation.ContentType
import com.ykis.ykispam.ui.navigation.NavigationType
import com.ykis.ykispam.ui.screens.meter.heat.HeatMeterDetail
import com.ykis.ykispam.ui.screens.meter.heat.HeatMeterState
import com.ykis.ykispam.ui.screens.meter.heat.reading.HeatReadings
import com.ykis.ykispam.ui.screens.meter.water.WaterMeterDetail
import com.ykis.ykispam.ui.screens.meter.water.WaterMeterState
import com.ykis.ykispam.ui.screens.meter.water.reading.WaterReadings

@Composable
fun MainMeterScreen(
    modifier: Modifier = Modifier,
    viewModel: MeterViewModel = hiltViewModel(),
    baseUIState: BaseUIState,
    navigationType: NavigationType,
    onDrawerClick: () -> Unit,
    contentType: ContentType,
    displayFeature: List<DisplayFeature>
) {
    var selectedTab by rememberSaveable{
        mutableIntStateOf(0)
    }
    val waterMeterState by viewModel.waterMeterState.collectAsStateWithLifecycle()
    val heatMeterState by viewModel.heatMeterState.collectAsStateWithLifecycle()
    val showDetail by viewModel.showDetail.collectAsStateWithLifecycle()
    val contentDetail by viewModel.contentDetail.collectAsStateWithLifecycle()
    if (contentType == ContentType.DUAL_PANE) {
        BaseDualPanelContent(
            displayFeatures = displayFeature,
            firstScreen = {
                MeterListScreen(
                    baseUIState = baseUIState,
                    navigationType = navigationType,
                    onDrawerClick = onDrawerClick,
                    viewModel = viewModel,
                    onWaterMeterClick = { waterMeter ->
                        viewModel.setWaterMeterDetail(
                            waterMeterEntity = waterMeter
                        )
                    },
                    onHeatMeterClick = { heatMeter -> viewModel.setHeatMeterDetail(heatMeterEntity = heatMeter) },
                    waterMeterState = waterMeterState,
                    heatMeterState = heatMeterState,
                    selectedTab = selectedTab,
                    onTabClick = {selectedTab=it}
                )
            },
            secondScreen = {
                DetailContent(
                    baseUIState = baseUIState,
                    contentType = contentType,
                    contentDetail = contentDetail,
                    onBackPressed = {
                        when (contentDetail) {
                            ContentDetail.WATER_READINGS -> viewModel.setContentDetail(ContentDetail.WATER_METER)
                            ContentDetail.HEAT_READINGS -> viewModel.setContentDetail(ContentDetail.HEAT_METER)
                            else -> viewModel.closeContentDetail()
                        }
                    },
                    showDetail = showDetail,
                    onActionButtonClick = {
                        if (contentDetail == ContentDetail.WATER_METER) {
                            viewModel.setContentDetail(ContentDetail.WATER_READINGS)
                        } else viewModel.setContentDetail(ContentDetail.HEAT_READINGS)
                    }
                ) {
                    Crossfade(targetState = contentDetail, label = "") { targetState ->
                        when (targetState) {
                            ContentDetail.WATER_METER -> {
                                WaterMeterDetail(
                                    waterMeterEntity = waterMeterState.selectedWaterMeter,
                                    baseUIState = baseUIState,
                                    getLastReading = {
                                        viewModel.getLastWaterReading(
                                            vodomerId = waterMeterState.selectedWaterMeter.vodomerId,
                                            uid = baseUIState.uid!!
                                        )
                                    },
                                    lastReading = waterMeterState.lastReading
                                )
                            }

                            ContentDetail.HEAT_METER -> {
                                HeatMeterDetail(heatMeterEntity = heatMeterState.selectedHeatMeter)
                            }

                            ContentDetail.WATER_READINGS -> {
                                WaterReadings(
                                    baseUIState = baseUIState,
                                    waterMeterState = waterMeterState,
                                    getWaterReadings = {
                                        viewModel.getWaterReadings(
                                            baseUIState.uid!!,
                                            waterMeterState.selectedWaterMeter.vodomerId
                                        )
                                    }
                                )
                            }

                            ContentDetail.HEAT_READINGS -> {
                                HeatReadings(
                                    baseUIState = baseUIState,
                                    heatMeterState = heatMeterState,
                                    getHeatReadings = {viewModel.getHeatReadings(
                                        baseUIState.uid!! ,
                                        heatMeterState.selectedHeatMeter.teplomerId
                                    )}
                                )
                            }

                            else -> EmptyDetail()
                        }
                    }

                }
            }
        )
    } else SinglePanelMeter(
        contentDetail = contentDetail,
        baseUIState = baseUIState,
        navigationType = navigationType,
        onDrawerClick = onDrawerClick,
        showDetail = showDetail,
        viewModel = viewModel,
        contentType = contentType,
        waterMeterState = waterMeterState,
        heatMeterState = heatMeterState,
        onWaterMeterClick = { waterMeter -> viewModel.setWaterMeterDetail(waterMeterEntity = waterMeter) },
        onHeatMeterClick = { heatMeter -> viewModel.setHeatMeterDetail(heatMeterEntity = heatMeter) },
        selectedTab = selectedTab,
        onTabClick = {selectedTab=it}
    )
}


@Composable
fun SinglePanelMeter(
    modifier: Modifier = Modifier,
    contentDetail: ContentDetail,
    baseUIState: BaseUIState,
    navigationType: NavigationType,
    onDrawerClick: () -> Unit,
    showDetail: Boolean,
    viewModel: MeterViewModel,
    contentType: ContentType,
    waterMeterState: WaterMeterState,
    heatMeterState: HeatMeterState,
    selectedTab:Int,
    onTabClick:(Int)->Unit,
    onWaterMeterClick: (WaterMeterEntity) -> Unit,
    onHeatMeterClick: (HeatMeterEntity) -> Unit,
) {
    if(!showDetail){
        MeterListScreen(
            baseUIState = baseUIState,
            navigationType = navigationType,
            onDrawerClick = onDrawerClick,
            viewModel = viewModel,
            onHeatMeterClick = onHeatMeterClick,
            onWaterMeterClick = onWaterMeterClick,
            waterMeterState = waterMeterState,
            heatMeterState = heatMeterState,
            selectedTab = selectedTab,
            onTabClick = onTabClick
        )
    }

    AnimatedVisibility(visible = showDetail) {
        BackHandler {
            when (contentDetail) {
                ContentDetail.WATER_READINGS -> viewModel.setContentDetail(ContentDetail.WATER_METER)
                ContentDetail.HEAT_READINGS -> viewModel.setContentDetail(ContentDetail.HEAT_METER)
                else -> viewModel.closeContentDetail()
            }
        }
        Column(
            modifier = modifier.background(
                MaterialTheme.colorScheme.background
            )
        ) {
            DetailAppBar(
                contentType = contentType,
                baseUIState = baseUIState,
                contentDetail = contentDetail,
                onActionButtonClick = {
                    if (contentDetail == ContentDetail.WATER_METER) {
                        viewModel.setContentDetail(ContentDetail.WATER_READINGS)
                    } else viewModel.setContentDetail(ContentDetail.HEAT_READINGS)
                },
                onBackPressed = {
                    when (contentDetail) {
                        ContentDetail.WATER_READINGS -> viewModel.setContentDetail(ContentDetail.WATER_METER)
                        ContentDetail.HEAT_READINGS -> viewModel.setContentDetail(ContentDetail.HEAT_METER)
                        else -> viewModel.closeContentDetail()
                    }
                }
            )

            Crossfade(targetState = contentDetail, label = "") { targetState ->
                when (targetState) {
                    ContentDetail.WATER_METER -> {
                        WaterMeterDetail(
                            waterMeterEntity = waterMeterState.selectedWaterMeter,
                            baseUIState = baseUIState,
                            getLastReading = {
                                viewModel.getLastWaterReading(
                                    vodomerId = waterMeterState.selectedWaterMeter.vodomerId,
                                    uid = baseUIState.uid!!
                                )
                            },
                            lastReading = waterMeterState.lastReading
                        )
                    }

                    ContentDetail.HEAT_METER -> {
                        HeatMeterDetail(heatMeterEntity = heatMeterState.selectedHeatMeter)
                    }

                    ContentDetail.WATER_READINGS -> {
                        WaterReadings(
                            baseUIState = baseUIState,
                            waterMeterState = waterMeterState,
                            getWaterReadings = {
                                viewModel.getWaterReadings(
                                    baseUIState.uid!!,
                                    waterMeterState.selectedWaterMeter.vodomerId
                                )
                            }
                        )
                    }

                    ContentDetail.HEAT_READINGS -> {
                        HeatReadings(
                            baseUIState = baseUIState,
                            heatMeterState = heatMeterState,
                            getHeatReadings = {}
                        )
                    }

                    else -> EmptyDetail()
                }
            }
        }
    }


}
