package com.ykis.ykispam.ui.screens.meter.heat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.R
import com.ykis.ykispam.domain.heat.meter.HeatMeterEntity
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.components.EmptyListState
import com.ykis.ykispam.ui.screens.meter.MeterViewModel

@Composable
fun HeatMeterList(
    modifier: Modifier = Modifier,
    viewModel: MeterViewModel,
    onHeatMeterClick : (HeatMeterEntity) -> Unit,
    baseUIState: BaseUIState,
    heatMeterState: HeatMeterState
) {
    LaunchedEffect(key1 = baseUIState.addressId) {
        viewModel.getHeatMeterList(baseUIState.uid!!,baseUIState.addressId)
    }
    if(heatMeterState.heatMeterList.isEmpty() && !heatMeterState.isLoading){
        EmptyListState(title = stringResource(R.string.no_meters) , subtitle = stringResource(R.string.no_heat_meters))
    }else {
        LazyColumn {
            items(
                heatMeterState.heatMeterList
            ) { heatMeter ->
                HeatMeterItem(
                    modifier = modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                        .clip(CardDefaults.outlinedShape)
                        .clickable {
                            onHeatMeterClick(heatMeter)
                        },
                    heatMeter = heatMeter)
            }
        }
    }
}