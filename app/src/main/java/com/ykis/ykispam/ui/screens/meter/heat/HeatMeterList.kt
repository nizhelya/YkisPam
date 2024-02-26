package com.ykis.ykispam.ui.screens.meter.heat

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ykis.ykispam.R
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.components.EmptyListState
import com.ykis.ykispam.ui.screens.meter.MeterViewModel

@Composable
fun HeatMeterList(
    modifier: Modifier = Modifier,
    viewModel: MeterViewModel,
    baseUIState: BaseUIState
) {
    val heatMeterState by viewModel.heatMeterState.collectAsStateWithLifecycle()
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
                HeatMeterItem(heatMeter = heatMeter)
            }
        }
    }
}