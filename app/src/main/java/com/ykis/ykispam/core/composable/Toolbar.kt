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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.R


@Composable
fun BackIcon(
    navigateBack: () -> Unit
) {
    IconButton(
        onClick = navigateBack
    ) {
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            contentDescription = null,
        )
    }
}

@Composable
fun BasicToolbar(
    @StringRes title: Int,
    isFullScreen: Boolean
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 12.dp,
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = if (isFullScreen) Alignment.CenterHorizontally
                else Alignment.Start
            ) {
                Text(
                    text = stringResource(title),
                    color = MaterialTheme.colors.onSurface
                )
            }

        }
    )
}


    @Composable
    fun ActionToolbar(
        @StringRes title: Int,
        @DrawableRes endActionIcon: Int,
        modifier: Modifier,
        isFullScreen: Boolean,
        endAction: () -> Unit
    ) {
        TopAppBar(
            modifier = modifier,
            backgroundColor = MaterialTheme.colors.background,
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = if (isFullScreen) Alignment.CenterHorizontally
                    else Alignment.Start
                ) {
                    Text(
                        text = stringResource(title),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            },
            navigationIcon = {
                if (isFullScreen) {
                    IconButton(
                        onClick = endAction,
                        modifier = Modifier.padding(8.dp),

                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_button),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            },
            actions = {
                IconButton(
                    onClick = { /*TODO*/ },
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = stringResource(id = R.string.more_options_button),
                        tint = MaterialTheme.colors.onSurface
                    )
                }

            }
        )
    }
