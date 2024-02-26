package com.ykis.ykispam.ui.screens.service.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderInTable(text: String, modifier: Modifier = Modifier, textAlign: TextAlign) {
    Text(
        text = text, textAlign = textAlign, style = MaterialTheme.typography.labelLarge,
        modifier = modifier

    )
}

@Composable
fun NumberInTable(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text, style = TextStyle(
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp
        ),

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
    Column(horizontalAlignment = alignment, verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.width(
            IntrinsicSize.Max
        ).padding(horizontal = 4.dp)
    )
    {
        HeaderInTable(
            header,
            textAlign = headerAlign
        )
        Column(
            horizontalAlignment = alignment,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (value1 !== "null" && value1 !== "none") {
                NumberInTable(value1)
            }
            if (value2 != "null" && value2 != "none") {
                NumberInTable(value2)
            }
            if (value3 != "null" && value3 != "none") {
                NumberInTable(value3)
            }
            if (value4 != "null" && value4 != "none") {
                NumberInTable(value4)
            }
        }
        HeaderInTable(
            text = summary,
            textAlign = TextAlign.End
        )
    }
}