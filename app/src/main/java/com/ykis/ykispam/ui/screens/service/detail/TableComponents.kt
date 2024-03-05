package com.ykis.ykispam.ui.screens.service.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HeaderInTable(text: String, modifier: Modifier = Modifier, textAlign: TextAlign) {
    Text(
        text = text, style = MaterialTheme.typography.labelLarge,
        modifier = modifier.height(24.dp).wrapContentHeight(
            Alignment.CenterVertically
        ),
        textAlign =  textAlign,


    )
}


@Composable
fun ColumnItemInTable(
    modifier: Modifier = Modifier,
    alignment: Alignment.Horizontal,
    value1: String,
    value2: String,
    value3: String,
    value4: String,
    header: String,
    summary: String,
    headerAlign: TextAlign
)
{
    Column(
        horizontalAlignment = alignment,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(end=8.dp)
    )
    {
        HeaderInTable(
            modifier = modifier.height(36.dp),
            text =  header,
            textAlign = headerAlign
        )
        Column(
            horizontalAlignment = alignment,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (value1 !== "null" && value1 !== "none") {
                Text(
                    text = value1,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Light
                    )
                )
            }
            if (value2 != "null" && value2 != "none") {
                Text(
                    text = value2,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Light
                    )
                )
            }
            if (value3 != "null" && value3 != "none") {
                Text(
                    text = value3,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Light
                    )
                )
            }
            if (value4 != "null" && value4 != "none") {
                Text(
                    text = value4,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Light
                    )
                )
            }
        }
        HeaderInTable(
            text = summary,
            textAlign = TextAlign.End
        )
    }
}