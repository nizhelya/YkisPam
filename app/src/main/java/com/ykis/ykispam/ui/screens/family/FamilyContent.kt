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

package com.ykis.ykispam.ui.screens.family

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykis.ykispam.domain.family.FamilyEntity
import com.ykis.ykispam.ui.BaseUIState

@Composable
fun FamilyContent(
    modifier: Modifier = Modifier,
    baseUIState: BaseUIState,
    viewModel: FamilyListViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = baseUIState.apartment) {
        viewModel.getFamilyList(baseUIState.uid ?: "", baseUIState.addressId)
    }
    val state by viewModel.state.collectAsState()

    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .fillMaxSize()
    ) {
        AnimatedVisibility(
            visible = !state.isLoading,
            enter = fadeIn(tween(delayMillis =500)),
            exit = fadeOut(tween(delayMillis =500))
        ) {
            FamilyList(
                familyList = state.familyList,
                modifier = modifier,
            )
        }
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.Center),
            visible = state.isLoading,
            exit = fadeOut(tween(delayMillis =500)),
            enter = fadeIn(tween(delayMillis =500))
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun FamilyList(
    familyList: List<FamilyEntity>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {

        items(items = familyList, key = { it.recId }) { person ->
            FamilyListItem(person = person)
        }
    }
}






