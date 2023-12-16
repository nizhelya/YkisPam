package com.ykis.ykispam.pam.screens.appartment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.BaseUIState
import com.ykis.ykispam.core.composable.HelpAlertCard
import com.ykis.ykispam.navigation.ADDRESS
import com.ykis.ykispam.navigation.ADDRESS_ID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuItem(
    imageVector: ImageVector,
    baseUIState: BaseUIState,
    screen:String,
    navigateToDestination: (String) -> Unit,
    dolg: Double = 0.0,
    serviseName: String = "",
    org: String = "",
    description: String = "",
    isSelectable: Boolean = false,
    isSelected: Boolean = false,
    modifier: Modifier = Modifier,

    ) {
    var showDialog by rememberSaveable { mutableStateOf(false) }

    val semanticsModifier =
        if (isSelectable)
            modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .semantics { selected = isSelected }
        else modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    Card(
        modifier = semanticsModifier.clickable {
            navigateToDestination(
                "$screen?$ADDRESS_ID=${baseUIState.addressId},$ADDRESS=${baseUIState.address}"
            )
        },
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(
            containerColor =  MaterialTheme.colorScheme.inverseOnSurface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
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
                    text = serviseName,
                    style = MaterialTheme.typography.titleMedium,
                    color =  MaterialTheme.colorScheme.secondary,
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(1f),
                )
            }


        }
    }
}


