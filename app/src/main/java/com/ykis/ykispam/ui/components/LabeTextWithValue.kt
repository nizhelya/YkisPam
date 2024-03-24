package com.ykis.ykispam.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LabelTextWithText(
    modifier: Modifier = Modifier,
    labelText: String = "",
    valueText:String =""
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = labelText,
            style =  MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Normal
            )
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = valueText,
            style =  MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Light
            )
        )
    }
}

@Composable
fun LabelTextWithTextAndIcon(
    modifier: Modifier = Modifier,
    labelText: String = "",
    valueText:String ="",
    imageVector: ImageVector
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            modifier = modifier.padding(end = 4.dp),
            imageVector = imageVector ,
            contentDescription = null
        )

        Text(
            text = labelText,
            style =  MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Normal
            )        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = valueText,
            style =  MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Light
            )
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
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = labelText,
            style =  MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Normal
            )
        )
        Checkbox(
            modifier = Modifier
                .padding(start = 8.dp)
                .size(24.dp),
            checked = checked,
            onCheckedChange = {}
        )
    }
}

@Composable
fun ColumnLabelTextWithTextAndIcon(
    modifier: Modifier = Modifier,
    labelText: String = "",
    valueText:String = "",
    imageVector: ImageVector? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ){
            if(imageVector!=null){
                Icon(
                    imageVector = imageVector ,
                    contentDescription = null
                )
            }
            Text(
                text = labelText,
                style =  MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Normal
                )
            )
        }
        Text(
            modifier = Modifier,
            text = valueText,
            style =  MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Thin
            )
        )
    }
}