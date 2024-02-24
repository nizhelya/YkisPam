package com.ykis.ykispam.ui.screens.appartment.content

/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.components.appbars.DetailAppBar
import com.ykis.ykispam.ui.navigation.ContentDetail
import com.ykis.ykispam.ui.navigation.ContentType

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    baseUIState: BaseUIState,
    contentType: ContentType,
    contentDetail: ContentDetail,
    onBackPressed: () -> Unit,
    showDetail : Boolean,
    detailContent: @Composable () -> Unit
) {
    androidx.compose.animation.AnimatedVisibility(
        visible = showDetail ,
        enter = slideInVertically(
            tween(
                durationMillis = 550,
                easing = EaseOutCubic
            ),
            initialOffsetY = {
                it
            },
        )+ fadeIn(
            tween(
                durationMillis = 400
            )
        ),
        exit = slideOutVertically(
            targetOffsetY = { it }
        )
        + fadeOut()
    ) {
        Card(
            modifier = modifier
                .fillMaxSize(),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor =
                    MaterialTheme.colorScheme.surfaceContainer
            )
        ) {
            Column {
                    DetailAppBar(
                        contentType = contentType,
                        baseUIState = baseUIState,
                        contentDetail = contentDetail
                    ) {
                        onBackPressed()
                    }
                detailContent()
            }
        }
    }
}









