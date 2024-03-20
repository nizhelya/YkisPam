package com.ykis.ykispam.ui.screens.service.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.R
import com.ykis.ykispam.core.ext.formatDebt

@Composable
fun ServiceRow(
    modifier: Modifier = Modifier,
    color: Color,
    title: String,
    debt: Double,
    icon: ImageVector,
    onClick: () -> Unit
) {
    val formattedDebt = formatDebt(debt)

    Card(

        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ) {

        Row(
            modifier = modifier
                .padding(all = 4.dp)
                .clearAndSetSemantics {
                    contentDescription =
                        "Ваш борг для компанії $title становить $debt"
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            val typography = MaterialTheme.typography
            ServiceIndicator(
                color = color,
                modifier = Modifier
            )
            Icon(
                modifier = modifier.padding(horizontal = 8.dp),
                imageVector = icon,
                contentDescription = null
            )
            Column(Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = typography.titleMedium,
                    maxLines = 1
                )

            }
            Spacer(modifier = modifier.width(4.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formattedDebt,
                    style = typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = stringResource(R.string.uah),
                    style = typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterVertically),
                )
            }
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(24.dp)
            )
        }
//        }
    }
    HorizontalDivider(thickness = 1.dp)
}