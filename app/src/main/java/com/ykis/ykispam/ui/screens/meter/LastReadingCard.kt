package com.ykis.ykispam.ui.screens.meter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.R
import com.ykis.ykispam.domain.water.reading.WaterReadingEntity
import com.ykis.ykispam.ui.screens.meter.water.reading.WaterReadingItem
import com.ykis.ykispam.ui.theme.YkisPAMTheme
import com.ykis.ykispam.ui.theme.customTitleForCard

@Composable
fun LastReadingCard(
    modifier: Modifier = Modifier,
    lastReading:WaterReadingEntity
) {
    Column(
        horizontalAlignment = Alignment.End
    ) {
        Text(
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = stringResource(id = R.string.last_reading),
            style = customTitleForCard
        )
        WaterReadingItem(reading = lastReading)
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    )
    {
        TextButton(
            onClick = { /*TODO*/ },
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null
                )
                Text(
                    text = stringResource(id = R.string.delete)
                )
            }
        }
        Button(
            modifier = modifier.padding(horizontal = 4.dp),
            onClick = {}
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_add_reading),
                    contentDescription = null
                )
                Text(
                    text = stringResource(id = R.string.add_reading)
                )
            }
        }
    }
    }
}

@Preview(
    showBackground = true,
    device = "id:pixel_6"
)
@Composable
private fun PreviewLastReadingCard() {
    YkisPAMTheme {
        LastReadingCard(
            lastReading = WaterReadingEntity()
        )
    }
}

