package com.ykis.ykispam.ui.screens.meter.water

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.R
import com.ykis.ykispam.core.ext.isTrue
import com.ykis.ykispam.domain.water.meter.WaterMeterEntity
import com.ykis.ykispam.ui.theme.YkisPAMTheme

@Composable
fun WaterMeterDetail(
    modifier: Modifier = Modifier,
    waterMeterEntity: WaterMeterEntity
) {
    Column(
        modifier = modifier.fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.water_detail_text),
            style = MaterialTheme.typography.headlineSmall
        )
        Column(
            modifier = modifier.padding(all = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row {
                Text(text = stringResource(id = R.string.model_colon))
                Text(
                    modifier = modifier.padding(start = 8.dp),
                    text = waterMeterEntity.model,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Row {
                Text(text = stringResource(id = R.string.number_colon))
                Text(
                    modifier = modifier.padding(start = 8.dp),
                    text = waterMeterEntity.nomer,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Row {
                Text(text = stringResource(id = R.string.place_colon))
                Text(
                    modifier = modifier.padding(start = 8.dp),
                    text = waterMeterEntity.place,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.position_colon))
                Text(
                    modifier = modifier.padding(start = 8.dp),
                    text = waterMeterEntity.position,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.stoki_colon))
                Checkbox(
                    checked = waterMeterEntity.st.isTrue(),
                    onCheckedChange = {}
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.general_colon))
                Checkbox(
                    checked = waterMeterEntity.avg.isTrue(),
                    onCheckedChange = {}
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.zdate_colon))
                Text(
                    modifier = modifier.padding(start = 8.dp),
                    text = waterMeterEntity.zdate,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.sdate_colon))
                Text(
                    modifier = modifier.padding(start = 8.dp),
                    text = waterMeterEntity.sdate,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
            HorizontalDivider()
            Text(
                text = stringResource(id = R.string.check_water_meter),
                style = MaterialTheme.typography.headlineSmall
            )
        Column(
            modifier = Modifier.padding(all=8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.pdate_colon))
                Text(
                    modifier = modifier.padding(start = 8.dp),
                    text = waterMeterEntity.pdate,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.fdate_colon))
                Text(
                    modifier = modifier.padding(start = 8.dp),
                    text = waterMeterEntity.fpdate,
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.stop_colon)
                )
                Checkbox(
                    checked = waterMeterEntity.spisan.isTrue(),
                    onCheckedChange = {}
                )
            }
        }
    }
}

@Preview(showBackground = true, device = "id:pixel_6")
@Composable
private fun PreviewWaterMeterDetail() {
    YkisPAMTheme {
        WaterMeterDetail(
            waterMeterEntity = WaterMeterEntity(
                model = "GLS 3 ULTRA"
            )
        )
    }
}