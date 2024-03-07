package com.ykis.ykispam.ui.screens.meter

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.ykis.ykispam.R
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.components.appbars.DefaultAppBar
import com.ykis.ykispam.ui.navigation.ContentDetail
import com.ykis.ykispam.ui.screens.meter.heat.HeatMeterState
import com.ykis.ykispam.ui.screens.meter.water.WaterMeterState

@Composable
fun MeterDetailScreen(
    contentDetail: ContentDetail,
    waterMeterState: WaterMeterState,
    heatMeterState: HeatMeterState,
    viewModel: MeterViewModel,
    baseUIState: BaseUIState
) {
    Column {
        DefaultAppBar(
            title = when (contentDetail) {
                ContentDetail.WATER_METER -> waterMeterState.selectedWaterMeter.model
                ContentDetail.HEAT_METER -> heatMeterState.selectedHeatMeter.model
                else -> stringResource(id = R.string.reading_history)
            },
            onBackClick = {
                when (contentDetail) {
                    ContentDetail.WATER_READINGS -> viewModel.setContentDetail(ContentDetail.WATER_METER)
                    ContentDetail.HEAT_READINGS -> viewModel.setContentDetail(ContentDetail.HEAT_METER)
                    else -> viewModel.closeContentDetail()
                }
            },
        ) {
            if (contentDetail == ContentDetail.HEAT_METER || contentDetail == ContentDetail.WATER_METER) {
                IconButton(
                    onClick = {
                        viewModel.setContentDetail(
                            if (contentDetail == ContentDetail.WATER_METER) ContentDetail.WATER_READINGS else ContentDetail.HEAT_READINGS
                        )
                    },
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_history),
                        contentDescription = stringResource(id = R.string.reading_history),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
        MeterDetailContent(
            baseUIState = baseUIState,
            contentDetail = contentDetail,
            waterMeterState = waterMeterState,
            viewModel = viewModel,
            heatMeterState = heatMeterState
        )
    }
}