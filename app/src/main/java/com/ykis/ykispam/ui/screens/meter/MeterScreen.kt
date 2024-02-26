package com.ykis.ykispam.ui.screens.meter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ykis.ykispam.R
import com.ykis.ykispam.core.ext.isTrue
import com.ykis.ykispam.domain.water.meter.WaterMeterEntity
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.components.appbars.DefaultAppBar
import com.ykis.ykispam.ui.navigation.NavigationType
import com.ykis.ykispam.ui.theme.YkisPAMTheme

@Composable
fun MeterScreen(
        viewModel : MeterViewModel= hiltViewModel(),
        baseUIState: BaseUIState,
        navigationType:NavigationType,
        onDrawerClick: () ->Unit,
    ) {
    val waterMeterState by viewModel.waterMeterState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = baseUIState.addressId) {
        viewModel.getWaterMeterList(baseUIState.uid!!,baseUIState.addressId)
    }
    Column(Modifier.fillMaxSize()) {
        DefaultAppBar(
            title = stringResource(id = R.string.meters),
            onBackClick ={},
            onDrawerClick = onDrawerClick,
            canNavigateBack = false,
            navigationType = navigationType
        )
        LazyColumn{
            items(
                waterMeterState.waterMeterList
            ){
                waterMeter->
                    WaterMeterItem(waterMeter = waterMeter)
            }
        }
    }
}

@Composable
fun WaterMeterItem(
    modifier: Modifier = Modifier,
    waterMeter : WaterMeterEntity
) {
    val statusText :String
    val alpha: Float
    when {
        waterMeter.spisan.isTrue() ->{
            statusText = stringResource(R.string.written_off)
            alpha =0.5f
        }
        waterMeter.out.isTrue() -> {
            statusText =stringResource(R.string.on_the_test)
            alpha =0.5f
        }
        waterMeter.paused.isTrue() ->{
            statusText =stringResource(R.string.suspended)
            alpha =0.5f
        }
        else -> {
            statusText = stringResource(R.string.works)
            alpha = 1f
        }
    }
    Column(
        modifier = modifier.alpha(alpha)
    ) {
        Row(
            modifier = modifier.fillMaxWidth().padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
           Icon(
               modifier = modifier
                   .padding(horizontal = 12.dp)
                   .size(48.dp),
                painter = painterResource(id = R.drawable.ic_water_meter),
                contentDescription = null
            )
            Column(modifier = modifier.weight(1f)) {
                Text(
                    text = waterMeter.model,
                    style = MaterialTheme.typography.titleLarge
                )
                Row {
                    Text(
                        text = stringResource(id = R.string.number_colon)
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = waterMeter.nomer
                    )
                }
                Row {
                    Text(
                        text = stringResource(id = R.string.place_colon)
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = waterMeter.place
                    )
                }
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = statusText
                )

            }
        }
        HorizontalDivider()
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewWaterMeter() {
    YkisPAMTheme {
        WaterMeterItem(
           waterMeter = WaterMeterEntity(
               model = "GSD 8 METERS",
               nomer = "133932453245",
               place = "санвузол",
               work = 1,
//               spisan = 0
           )
        )
    }
    
}