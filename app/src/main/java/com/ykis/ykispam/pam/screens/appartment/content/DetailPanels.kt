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


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ykis.ykispam.BaseUIState
import com.ykis.ykispam.navigation.ContentDetail
import com.ykis.ykispam.navigation.ContentType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    baseUIState: BaseUIState,
    contentType: ContentType,
    contentDetail: ContentDetail,
    isFullScreen: Boolean = true,
    onBackPressed: () -> Unit = {}



) {
//    DetailAppBar(modifier,baseUIState,contentDetail, isFullScreen) {
//        onBackPressed()
//    }
        when (contentDetail) {
            ContentDetail.BTI -> BtiPanelContent(contentType =contentType,contentDetail = contentDetail,baseUIState = baseUIState,onBackPressed=onBackPressed)
            ContentDetail.FAMALY -> FamilyContent(contentType =contentType,contentDetail = contentDetail,baseUIState = baseUIState,onBackPressed=onBackPressed)
            ContentDetail.OSBB -> OsbbContent(contentType =contentType,contentDetail = contentDetail,baseUIState = baseUIState,onBackPressed=onBackPressed)
            ContentDetail.WATER_SERVICE -> TODO()
            ContentDetail.WARM_SERVICE -> TODO()
            ContentDetail.GARBAGE_SERVICE -> TODO()
            ContentDetail.PAYMENTS -> TODO()

            else -> BtiPanelContent(contentType =contentType,contentDetail = contentDetail,baseUIState = baseUIState,onBackPressed=onBackPressed)
        }
    }








