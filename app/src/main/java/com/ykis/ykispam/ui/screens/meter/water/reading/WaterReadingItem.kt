package com.ykis.ykispam.ui.screens.meter.water.reading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.R
import com.ykis.ykispam.core.ext.isTrue
import com.ykis.ykispam.domain.water.reading.WaterReadingEntity
import com.ykis.ykispam.ui.components.LabelTextWithText
import com.ykis.ykispam.ui.theme.YkisPAMTheme
import com.ykis.ykispam.ui.theme.customTitleForCard
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun WaterReadingItem(
    modifier: Modifier = Modifier,
    reading: WaterReadingEntity
) {
    val dateUnixOt = SimpleDateFormat("yyyy-MM-dd" , Locale("uk")).parse(reading.dateOt)
    val dateUnixDo = SimpleDateFormat("yyyy-MM-dd", Locale("uk")).parse(reading.dateDo)
    Column(modifier = modifier.fillMaxWidth()){
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(48.dp),
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Column(
                modifier = modifier.padding(all = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ){
                LabelTextWithText(
                    labelText = stringResource(id = R.string.period_colon) ,
                    valueText = stringResource(
                        R.string.date_format,
                        dateUnixOt?.let { SimpleDateFormat("dd/MM/yyyy", Locale("uk")).format(it) }.toString(),
                        dateUnixDo?.let { SimpleDateFormat("dd/MM/yyyy", Locale("uk")).format(it) }.toString())
                )
                LabelTextWithText(
                    labelText = stringResource(id = R.string.days_colon) ,
                    valueText = reading.days.toString()
                )
                Row{
                    LabelTextWithText(
                        modifier = modifier.weight(0.5f),
                        labelText = stringResource(id = R.string.last_colon) ,
                        valueText = reading.last.toString()
                    )
                    LabelTextWithText(
                        modifier = modifier.weight(0.5f),
                        labelText = stringResource(id = R.string.current_colon) ,
                        valueText = reading.currant.toString()
                    )
                }

                LabelTextWithText(
                    labelText = stringResource(id = R.string.cubs_colon) ,
                    valueText = reading.kub.toString()
                )
            }

        }
        if(reading.avg.isTrue()){
            Text(
                text = stringResource(id = R.string.average),
                style = customTitleForCard
            )
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(48.dp),
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Column(
                    modifier = modifier.padding(all = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ){
                    LabelTextWithText(
                        labelText = stringResource(id = R.string.last_colon) ,
                        valueText = reading.pokOt.toString()
                    )
                    LabelTextWithText(
                        labelText = stringResource(id = R.string.current_colon) ,
                        valueText = reading.pokDo.toString()
                    )
                    LabelTextWithText(
                        labelText = stringResource(id = R.string.qty_cube_colon) ,
                        valueText = reading.qtyKub.toString()
                    )
                    LabelTextWithText(
                        labelText = stringResource(id = R.string.days_colon) ,
                        valueText = reading.rday.toString()
                    )
                    LabelTextWithText(
                        labelText = stringResource(id = R.string.cube_day_colon) ,
                        valueText = reading.kubDay.toString()
                    )
                }

            }
            HorizontalDivider(
                modifier = modifier.padding(vertical = 4.dp)
            )
        }


    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewWaterReadingItem() {
    YkisPAMTheme {
        WaterReadingItem(
            reading = WaterReadingEntity(
                avg = 1
            )
        )
    }
}
