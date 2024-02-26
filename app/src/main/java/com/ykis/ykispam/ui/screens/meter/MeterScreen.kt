package com.ykis.ykispam.ui.screens.meter

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.ykis.ykispam.domain.heat.meter.HeatMeterEntity
import com.ykis.ykispam.domain.water.meter.WaterMeterEntity
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.components.EmptyListState
import com.ykis.ykispam.ui.components.appbars.DefaultAppBar
import com.ykis.ykispam.ui.navigation.METER_TAB_ITEM
import com.ykis.ykispam.ui.navigation.NavigationType
import com.ykis.ykispam.ui.screens.meter.heat.HeatMeterList
import com.ykis.ykispam.ui.screens.meter.water.WaterMeterList
import com.ykis.ykispam.ui.theme.YkisPAMTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeterScreen(
        modifier: Modifier = Modifier,
        viewModel : MeterViewModel= hiltViewModel(),
        baseUIState: BaseUIState,
        navigationType:NavigationType,
        onDrawerClick: () ->Unit,
    ) {
    var selectedTab by rememberSaveable{
        mutableIntStateOf(0)
    }

    Column(Modifier.fillMaxSize()) {
        DefaultAppBar(
            title = stringResource(id = R.string.meters),
            onBackClick ={},
            onDrawerClick = onDrawerClick,
            canNavigateBack = false,
            navigationType = navigationType
        )
        PrimaryTabRow(
            selectedTabIndex = selectedTab ,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            METER_TAB_ITEM.forEachIndexed { index, tabItem ->
                LeadingIconTab(
                    selected = selectedTab==index,
                    onClick = { selectedTab = index },
                    text = {
                        Text(text = stringResource( tabItem.titleId))
                    },
                    icon = {
                        Icon (
                            imageVector = if(index == selectedTab) tabItem.selectedIcon else tabItem.unselectedIcon,
                            contentDescription = stringResource( tabItem.titleId)
                        )
                    }
                )

            }
        }
        Box(
            modifier= modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Crossfade(targetState = selectedTab, label = "") { targetState ->
                when (targetState) {
                    0 -> WaterMeterList(viewModel = viewModel, baseUIState = baseUIState)
                    else -> HeatMeterList(viewModel = viewModel, baseUIState = baseUIState)
                }
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
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
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


@Composable
fun HeatMeterItem(
    modifier: Modifier = Modifier,
    heatMeter : HeatMeterEntity
) {
    val statusText :String
    val alpha: Float
    when {
        heatMeter.spisan.isTrue() ->{
            statusText = stringResource(R.string.written_off)
            alpha =0.5f
        }
        heatMeter.out.isTrue() -> {
            statusText =stringResource(R.string.on_the_test)
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
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = modifier
                    .padding(horizontal = 12.dp)
                    .size(48.dp),
                painter = painterResource(id = R.drawable.ic_heat_meter),
                contentDescription = null
            )
            Column(modifier = modifier.weight(1f)) {
                Text(
                    text = heatMeter.model,
                    style = MaterialTheme.typography.titleLarge
                )
                Row {
                    Text(
                        text = stringResource(id = R.string.number_colon)
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = heatMeter.number
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
@Composable
fun WaterMeterList(
    modifier: Modifier = Modifier,
    viewModel : MeterViewModel,
    baseUIState: BaseUIState
) {
    val waterMeterState by viewModel.waterMeterState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = baseUIState.addressId) {
        viewModel.getWaterMeterList(baseUIState.uid!!,baseUIState.addressId)
    }
    LazyColumn {
        items(
            waterMeterState.waterMeterList
        ) { waterMeter ->
            WaterMeterItem(waterMeter = waterMeter)
        }
    }
}

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

@Preview(showBackground = true)
@Composable
private fun PreviewHeatMeter() {
    YkisPAMTheme {
        HeatMeterItem(
            heatMeter = HeatMeterEntity(
                model = "Heat Turbo 1343",
                number = "1332342342"
            )
        )
    }
}