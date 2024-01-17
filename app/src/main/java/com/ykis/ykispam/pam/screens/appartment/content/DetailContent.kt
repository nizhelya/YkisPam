package com.ykis.ykispam.pam.screens.appartment.content

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


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.BaseUIState
import com.ykis.ykispam.navigation.ContentDetail
import com.ykis.ykispam.navigation.ContentType
import com.ykis.ykispam.pam.screens.appbars.DetailAppBar
import com.ykis.ykispam.pam.screens.bti.BtiPanelContent
import com.ykis.ykispam.pam.screens.family.FamilyContent
import com.ykis.ykispam.pam.screens.service.ServicesContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    baseUIState: BaseUIState,
    contentType: ContentType,
    contentDetail: ContentDetail,
    onBackPressed: () -> Unit = {}


) {
    Row(
        modifier = Modifier
            .padding(PaddingValues(4.dp))
            .fillMaxSize(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (contentDetail != ContentDetail.EMPTY ) {
                DetailAppBar(modifier, contentType, baseUIState, contentDetail) {
                    onBackPressed()
                }
            }
            when (contentDetail) {
                ContentDetail.BTI -> BtiPanelContent(
                    contentType = contentType,
                    contentDetail = contentDetail,
                    baseUIState = baseUIState,
                    onBackPressed = onBackPressed
                )

                ContentDetail.FAMALY -> FamilyContent(
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








