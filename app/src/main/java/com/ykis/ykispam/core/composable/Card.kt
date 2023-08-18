/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.ykis.ykispam.core.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp


@ExperimentalMaterialApi
@Composable
fun DangerousCardEditor(
    @StringRes title: Int,
    @DrawableRes icon: Int,
    content: String,
    modifier: Modifier,
    onEditClick: () -> Unit
) {
    CardEditor(title, icon, content, onEditClick, MaterialTheme.colors.primary, modifier)
}

@ExperimentalMaterialApi
@Composable
fun RegularCardEditor(
    @StringRes title: Int,
    @DrawableRes icon: Int,
    content: String,
    modifier: Modifier,
    onEditClick: () -> Unit
) {
    CardEditor(title, icon, content, onEditClick, MaterialTheme.colors.onSurface, modifier)
}

@ExperimentalMaterialApi
@Composable
fun InfoCardEditor(
    @StringRes title: Int,
    @DrawableRes icon: Int,
    content: String,
    modifier: Modifier,
    onEditClick: () -> Unit
) {
    CardEditorInfo(title, icon, content, onEditClick, Color(0xFFFFB945), modifier)
}
@ExperimentalMaterialApi
@Composable
private fun CardEditor(
    @StringRes title: Int,
    @DrawableRes icon: Int,
    content: String,
    onEditClick: () -> Unit,
    highlightColor: Color,
    modifier: Modifier

) {
    Card(
        backgroundColor = MaterialTheme.colors.background,
        modifier = modifier,
        elevation = 20.dp,
        shape = RoundedCornerShape(20.dp),
        onClick = onEditClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) { Text(stringResource(title), color = highlightColor) }

            if (content.isNotBlank()) {
                Text(text = content, modifier = Modifier.padding(16.dp, 0.dp))
            }

            Icon(painter = painterResource(icon), contentDescription = "Icon", tint = highlightColor)
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun CardEditorInfo(
    @StringRes title: Int,
    @DrawableRes icon: Int,
    content: String,
    onEditClick: () -> Unit,
    highlightColor: Color,
    modifier: Modifier

) {
    Card(
        backgroundColor = MaterialTheme.colors.background,
        modifier = modifier,
        elevation = 20.dp,
        shape = RoundedCornerShape(5.dp),
        onClick = onEditClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(
                    start = 16.dp,
                    top = 6.dp,
                    end = 8.dp,
                    bottom = 6.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ){
            Column(modifier = Modifier.weight(1f)) { Text(stringResource(title), color = highlightColor) }

            if (content.isNotBlank()) {
                Text(text = content, modifier =Modifier.padding(16.dp, 0.dp)
                )
            }

            Icon(painter = painterResource(icon), contentDescription = "Icon", tint = highlightColor)
        }
    }
}


