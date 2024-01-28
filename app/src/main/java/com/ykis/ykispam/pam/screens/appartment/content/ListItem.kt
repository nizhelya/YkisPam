package com.ykis.ykispam.pam.screens.appartment.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.BaseUIState
import com.ykis.ykispam.navigation.ContentDetail
import com.ykis.ykispam.navigation.ContentType


@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    serviceName: String ,
    isSelected: Boolean = false,
    contentDetail: ContentDetail,
    navigateToDetail: (ContentDetail,ContentType) -> Unit,
    isSelectable: (ContentDetail) -> Unit


    ) {

    val semanticsModifier =
        if (isSelected)
            modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .semantics { selected = isSelected }
        else modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    Card(
        modifier = semanticsModifier.clickable {
            navigateToDetail(contentDetail, ContentType.SINGLE_PANE)
            (if(!isSelected) contentDetail else null)?.let { isSelectable(it) }
        },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.background
            else MaterialTheme.colorScheme.onSecondary
        )

    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(8.dp),
                imageVector = imageVector,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.outline
            )
            Text(
                text = serviceName,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
            )
        }
    }
}


