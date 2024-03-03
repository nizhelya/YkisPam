package com.ykis.ykispam.ui.screens.meter.heat.reading

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ykis.ykispam.R
import com.ykis.ykispam.domain.meter.heat.reading.HeatReadingEntity
import com.ykis.ykispam.ui.components.BaseCard
import com.ykis.ykispam.ui.components.LabelTextWithText
import com.ykis.ykispam.ui.theme.YkisPAMTheme
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun HeatReadingItem(
    reading: HeatReadingEntity,
    isAverage : Boolean
) {
    BaseCard(
        label = if(isAverage) stringResource(id = R.string.average) else null
    ) {
        HeatReadingItemContent(
            reading = reading,
            isAverage = isAverage
        )
    }
}


@Composable
fun HeatReadingItemContent(
    modifier: Modifier = Modifier,
    reading: HeatReadingEntity,
    isAverage: Boolean
) {
    val dateUnixOt = SimpleDateFormat("yyyy-MM-dd", Locale("uk")).parse(reading.dateOt)
    val dateUnixDo = SimpleDateFormat("yyyy-MM-dd", Locale("uk")).parse(reading.dateDo)
    if (isAverage) {
        LabelTextWithText(
            labelText = stringResource(id = R.string.period_colon),
            valueText = stringResource(
                R.string.date_format,
                dateUnixOt?.let { SimpleDateFormat("dd/MM/yyyy", Locale("uk")).format(it) }
                    .toString(),
                dateUnixDo?.let { SimpleDateFormat("dd/MM/yyyy", Locale("uk")).format(it) }
                    .toString()
            )
        )
        LabelTextWithText(
            labelText = stringResource(id = R.string.days_colon),
            valueText = reading.dayAvg
        )
        LabelTextWithText(
            labelText = stringResource(id = R.string.gkal_rasch_colon),
            valueText = reading.gkalRasch
        )
        LabelTextWithText(
            labelText = stringResource(id = R.string.gkal_day_colon),
            valueText = reading.gkalDay
        )
    } else {
        LabelTextWithText(
            labelText = stringResource(id = R.string.period_colon),
            valueText = stringResource(
                R.string.date_format,
                dateUnixOt?.let { SimpleDateFormat("dd/MM/yyyy", Locale("uk")).format(it) }
                    .toString(),
                dateUnixDo?.let { SimpleDateFormat("dd/MM/yyyy", Locale("uk")).format(it) }
                    .toString()
            )
        )
        LabelTextWithText(
            labelText = stringResource(id = R.string.days_colon),
            valueText = reading.days.toString()
        )
        Row {
            LabelTextWithText(
                modifier = modifier.weight(0.5f),
                labelText = stringResource(id = R.string.last_colon),
                valueText = reading.last.toString()
            )
            LabelTextWithText(
                modifier = modifier.weight(0.5f),
                labelText = stringResource(id = R.string.current_colon),
                valueText = reading.currant.toString()
            )
        }

        LabelTextWithText(
            labelText = stringResource(id = R.string.qty_colon),
            valueText = reading.qty.toString()
        )
        LabelTextWithText(
            labelText = stringResource(id = R.string.gkal_colon),
            valueText = reading.gkal.toString()
        )
        LabelTextWithText(
            labelText = stringResource(id = R.string.rate_colon),
            valueText = reading.tarif.toString()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHeatReadingItem() {
    YkisPAMTheme {
            HeatReadingItem(
                reading = HeatReadingEntity(),
                isAverage = true
            )
    }
}