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

package com.ykis.ykispam.pam.screens.osbb

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ykis.ykispam.R
import com.ykis.ykispam.pam.domain.family.FamilyEntity
import com.ykis.ykispam.pam.domain.service.ServiceEntity
import com.ykis.ykispam.pam.screens.appartment.DetailTopAppBar
import com.ykis.ykispam.pam.screens.family.FamilyListViewModel
import com.ykis.ykispam.theme.YkisPAMTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OsbbServiceScreen(
    modifier: Modifier = Modifier,
    popUpScreen: () -> Unit,
    viewModel: OsbbServiceViewModel = hiltViewModel(),
    addressId: String,
    address: String
) {

    LaunchedEffect(addressId) {
//        viewModel.servicesFlat(addressId.toInt())
    }

    val service by viewModel.servicesFlat.observeAsState(listOf(ServiceEntity()))

    OsbbServiceScreenContent(
        service = service,
        address = address,
        navigateBack = { viewModel.navigateBack(popUpScreen) }
    )
}

@ExperimentalMaterial3Api
@Composable
fun OsbbServiceScreenContent(
    modifier: Modifier = Modifier,
    service : List<ServiceEntity>,
    address: String,
    navigateBack: () -> Unit,
) {

    val familyLazyListState = rememberLazyListState()

    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DetailTopAppBar(modifier,stringResource(id = R.string.kvartplata_text),address, navigateBack = { navigateBack() })
        OsbbServiceList(
            service = service,
            familyLazyListState = familyLazyListState,
            modifier = modifier,
        )
    }
}

@Composable
fun OsbbServiceList(
    service: List<ServiceEntity>,
    familyLazyListState: LazyListState,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier, state = familyLazyListState) {

        items(items = service, key = { it.data }) { service ->
            OsbbServiceListItem(service = service)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OsbbServiceListItem(
    service: ServiceEntity,
    isSelectable: Boolean = false,
    isSelected: Boolean = false,
    modifier: Modifier = Modifier,


    ) {
    val semanticsModifier =
        if (isSelectable)
            modifier
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .semantics { selected = isSelected }
        else modifier.padding(horizontal = 16.dp, vertical = 4.dp)
    Card(
        modifier = semanticsModifier.clickable { },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("photoUrl")
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    error = painterResource(R.drawable.ic_account_circle),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .width(48.dp)
                        .height(48.dp)
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = service.service1.toString(),
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = service.service1.toString(),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.status),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = service.service1.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.born_text),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = service.service1.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.doc_text),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = service.service1.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.inn_text),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = service.service1.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@ExperimentalMaterial3Api
@Composable
fun VneskiScreenPreview() {
    YkisPAMTheme {
        OsbbServiceScreenContent(
            service = listOf(ServiceEntity(addressId = 6314)),
            address = "Гр.Десанта 21/71",
            navigateBack = {},

            )
    }
}




