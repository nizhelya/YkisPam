package com.ykis.ykispam.ui.screens.meter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.surfaceColorAtElevation
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
import com.ykis.ykispam.ui.components.LabelTextWithText
import com.ykis.ykispam.ui.theme.YkisPAMTheme
import com.ykis.ykispam.ui.theme.customTitleForCard

@Composable
fun LastReadingCard(modifier: Modifier = Modifier) {
    Column(

        horizontalAlignment = Alignment.End
    ) {
       /* TextButton(
//            modifier = modifier.fillMaxWidth(),
            onClick = { /*TODO*/ },
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_history),
                    contentDescription = null
                )
                Text(
                    text = stringResource(id = R.string.reading_history)
                )
            }
        }*/
        Text(
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = stringResource(id = R.string.last_reading),
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
                    ) {
                        LabelTextWithText(
                            labelText = stringResource(id = R.string.date_colon),
                            valueText = "12-01-2024"
                        )
                        Row(
                            modifier = modifier.fillMaxWidth()
                        ) {
                            LabelTextWithText(
                                modifier = modifier.weight(1f),
                                labelText = stringResource(id = R.string.last_colon),
                                valueText = "934"
                            )
                            LabelTextWithText(
                                modifier = modifier.weight(1f),
                                labelText = stringResource(id = R.string.current_colon),
                                valueText = "935"
                            )
                        }
                        LabelTextWithText(
                            labelText = stringResource(id = R.string.cubs_colon),
                            valueText = "1"
                        )

                    }
        }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    )
    {
        TextButton(
            onClick = { /*TODO*/ },
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Color.Transparent,
//                contentColor = MaterialTheme.colorScheme.tertiary
//            )
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
//            colors = ButtonDefaults.buttonColors(
//                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
//                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
//            ),
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
        LastReadingCard()
    }
}

