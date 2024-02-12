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


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.ui.navigation.ContentDetail
import com.ykis.ykispam.ui.navigation.ContentType
import com.ykis.ykispam.ui.screens.appbars.DetailAppBar
import com.ykis.ykispam.ui.screens.bti.BtiPanelContent
import com.ykis.ykispam.ui.screens.family.FamilyContent
import com.ykis.ykispam.ui.screens.service.ServicesContent
import com.ykis.ykispam.ui.BaseUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    baseUIState: BaseUIState,
    contentType: ContentType,
    contentDetail: ContentDetail,
    onBackPressed: () -> Unit = {}


) {
    Card(
        modifier = modifier
            .fillMaxSize(),
        shape = if(contentType== ContentType.DUAL_PANE)MaterialTheme.shapes.large else RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (contentDetail != ContentDetail.EMPTY && contentType == ContentType.DUAL_PANE) {
                MaterialTheme.colorScheme.surfaceContainerHighest
            } else Color.Transparent
        )
    ) {
        Column {
            if (contentDetail != ContentDetail.EMPTY)
                DetailAppBar(
                   contentType =  contentType,
                    baseUIState = baseUIState,
                    contentDetail =  contentDetail
                ) {
                    onBackPressed()
                }
            when (contentDetail) {
                ContentDetail.BTI -> BtiPanelContent(
                    contentType = contentType,
                    contentDetail = contentDetail,
                    baseUIState = baseUIState,
                    onBackPressed = onBackPressed
                )

                ContentDetail.FAMILY -> FamilyContent(
                    contentType = contentType,
                    contentDetail = contentDetail,
                    baseUIState = baseUIState,
                    onBackPressed = onBackPressed
                )

                ContentDetail.OSBB -> ServicesContent(
                    contentDetail = contentDetail,
                    baseUIState = baseUIState,
                )

                ContentDetail.WATER_SERVICE -> ServicesContent(
                    contentDetail = contentDetail,
                    baseUIState = baseUIState,
                )

                ContentDetail.WARM_SERVICE -> ServicesContent(
                    contentDetail = contentDetail,
                    baseUIState = baseUIState,
                )

                ContentDetail.GARBAGE_SERVICE -> ServicesContent(
                    contentDetail = contentDetail,
                    baseUIState = baseUIState,
                )

                ContentDetail.PAYMENTS -> ServicesContent(
                    contentDetail = contentDetail,
                    baseUIState = baseUIState,
                )

                else -> EmptyDetail(
                )
            }
        }
    }
}









