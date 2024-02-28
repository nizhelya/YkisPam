package com.ykis.ykispam.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LabelTextWithText(
    modifier: Modifier = Modifier,
    labelText: String,
    valueText:String
) {
    Row(
        modifier = modifier.height(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = labelText,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            modifier = modifier.padding(start = 8.dp),
            text = valueText,
        )
    }
}

@Composable
fun LabelTextWithCheckBox(
    modifier: Modifier = Modifier,
    labelText: String,
    checked:Boolean,
) {
    Row(
        modifier = modifier.height(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = labelText,
            style = MaterialTheme.typography.titleMedium
        )
        Checkbox(
            modifier = modifier
                .padding(start = 8.dp)
                .size(24.dp),
            checked = checked,
            onCheckedChange = {}
        )
    }
}