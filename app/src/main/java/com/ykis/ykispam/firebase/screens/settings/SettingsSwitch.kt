package com.ykis.ykispam.firebase.screens.settings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.SwitchColors
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter.State.Empty.painter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingsSwitch(
    @DrawableRes icon: Int,
    @StringRes iconDesc: Int,
    @StringRes name: Int,
    state: State<Boolean>,
    onClick: () -> Unit
) {
    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        onClick = onClick,
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painterResource(id = icon),
                        contentDescription = stringResource(id = iconDesc),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = name),
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.subtitle1,
                        textAlign = TextAlign.Start,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = state.value,
                    modifier = Modifier.scale(1.2f),
                    colors = SwitchDefaults.colors(
//                        checkedThumbColor = Color(0xFFFFB945),
                        checkedThumbColor = Color.White,
//                        checkedTrackColor = Color(0xFF3A4C2B),
//                        uncheckedThumbColor = Color(0xFF3A4C2B)
                    ),
                    onCheckedChange = { onClick() }
                )
            }
            Divider()
        }
    }
}
