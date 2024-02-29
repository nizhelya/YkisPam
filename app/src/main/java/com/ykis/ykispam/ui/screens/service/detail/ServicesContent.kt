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

package com.ykis.ykispam.ui.screens.service.detail

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ykis.ykispam.R
import com.ykis.ykispam.domain.service.ServiceEntity
import com.ykis.ykispam.domain.service.request.ServiceParams
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.components.EmptyListState
import com.ykis.ykispam.ui.components.GroupFilterChip
import com.ykis.ykispam.ui.navigation.ContentDetail
import com.ykis.ykispam.ui.screens.service.ServiceViewModel
import com.ykis.ykispam.ui.theme.YkisPAMTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ServicesContent(
    contentDetail: ContentDetail,
    baseUIState: BaseUIState,
    viewModel: ServiceViewModel = hiltViewModel(),
) {
    val date = Date()
    val year = SimpleDateFormat("yyyy", Locale("uk")).format(Date(date.time))

    val serviceDetail by viewModel.detailState.collectAsStateWithLifecycle()
    var selectedChip by rememberSaveable {mutableStateOf(year)}

    LaunchedEffect(key1 = selectedChip , key2 = contentDetail) {
            baseUIState.uid?.let {
                viewModel.getDetailService(
                    params = ServiceParams(
                        uid = it,
                        addressId = baseUIState.apartment.addressId,
                        houseId = baseUIState.apartment.houseId,
                        service = when (contentDetail) {
                            ContentDetail.OSBB -> 4.toByte()
                            ContentDetail.WATER_SERVICE -> 1.toByte()
                            ContentDetail.WARM_SERVICE -> 2.toByte()
                            ContentDetail.GARBAGE_SERVICE -> 3.toByte()
                            else -> 4.toByte()
                        },
                        year = selectedChip,
                        total = 0,
                    )
                )
        }

    }
        ServiceDetailContent(
            state = serviceDetail,
            year = year,
            serviceEntyties = serviceDetail.services,
            onSelectedChanged = { selectedChip = it },
            selectedChip = selectedChip
        )
}

@Composable
fun ServiceDetailItem(
    modifier: Modifier = Modifier,
    serviceEntity: ServiceEntity = ServiceEntity()
) {
    val scrollState = rememberScrollState()
    val dateUnix = SimpleDateFormat("yyyy-MM-dd").parse(serviceEntity.data)
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ), modifier = modifier
            .padding(all = 8.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMediumLow,
                )
            )
    ) {
        Column(
//            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = SimpleDateFormat("LLLL yyyy", Locale("uk")).format(Date(dateUnix.time))
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                ), modifier = modifier.padding(start = 16.dp , top = 16.dp)
            )
                Row(
                    modifier
                        .fillMaxWidth()
                        .horizontalScroll(scrollState)
                        .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
                    ,
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {

                    ColumnItemInTable(
                        alignment = Alignment.Start,
                        value1 = serviceEntity.service1.toString(),
                        value2 = serviceEntity.service2.toString(),
                        value3 = serviceEntity.service3.toString(),
                        value4 = serviceEntity.service4.toString(),
                        header = stringResource(id = R.string.services),
                        summary = stringResource(id = R.string.summary),
                        headerAlign = TextAlign.Start
                    )
                    ColumnItemInTable(
                        alignment = Alignment.End,
                        value1 = serviceEntity.zadol1.toString(),
                        value2 = serviceEntity.zadol2.toString(),
                        value3 = serviceEntity.zadol3.toString(),
                        value4 = serviceEntity.zadol4.toString(),
                        header = stringResource(id = R.string.start_debt),
                        summary = serviceEntity.zadol.toString(),
                        headerAlign = TextAlign.End
                    )
                    ColumnItemInTable(

                        alignment = Alignment.End,
                        value1 = serviceEntity.nachisleno1.toString(),
                        value2 = serviceEntity.nachisleno2.toString(),
                        value3 = serviceEntity.nachisleno3.toString(),
                        value4 = serviceEntity.nachisleno4.toString(),
                        header = stringResource(id = R.string.paid),
                        summary = serviceEntity.nachisleno.toString(),
                        headerAlign = TextAlign.End
                    )
                    ColumnItemInTable(
                        alignment = Alignment.End,
                        value1 = serviceEntity.oplacheno1.toString(),
                        value2 = serviceEntity.oplacheno2.toString(),
                        value3 = serviceEntity.oplacheno3.toString(),
                        value4 = serviceEntity.oplacheno4.toString(),
                        header = stringResource(id = R.string.accrued_text),
                        summary = serviceEntity.oplacheno.toString(),
                        headerAlign = TextAlign.End
                    )
                    ColumnItemInTable(
                        alignment = Alignment.End,
                        value1 = serviceEntity.dolg1.toString(),
                        value2 = serviceEntity.dolg2.toString(),
                        value3 = serviceEntity.dolg3.toString(),
                        value4 = serviceEntity.dolg4.toString(),
                        header = stringResource(id = R.string.end_debt),
                        summary = serviceEntity.dolg.toString(),
                        headerAlign = TextAlign.End
                    )
                }
            }
        }
}

@Composable
fun ListServiceDetails(
    listServiceEntity: List<ServiceEntity> = listOf(ServiceEntity(), ServiceEntity()),
    isLoading : Boolean
) {

    if(listServiceEntity.isEmpty() && !isLoading){
        EmptyListState(
            title = stringResource(id = R.string.no_payment),
            subtitle = stringResource(id = R.string.no_payment_year)
        )
    }else LazyColumn {
        items(items = listServiceEntity) { serviceDetail ->
            ServiceDetailItem(serviceEntity = serviceDetail)
        }
    }
}

@Composable
fun ServiceDetailContent(
    state : ServiceState,
    year:String,
    serviceEntyties: List<ServiceEntity>,
    selectedChip: String,
    onSelectedChanged: (String) -> Unit
) {
    val years = mutableListOf<String>()

    for (i in 0 until 20) {
        years.add((year.toInt() + (-i)).toString())
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        GroupFilterChip(
            list = years, selectedChip = selectedChip,
            onSelectedChanged = onSelectedChanged
        )
        Box(modifier = Modifier.fillMaxSize()){
            androidx.compose.animation.AnimatedVisibility(
                modifier = Modifier.align(Alignment.Center),
                visible = state.isLoading,
                exit = fadeOut(),
                enter = fadeIn(tween(delayMillis = 150))
            ) {
                CircularProgressIndicator()
            }
            androidx.compose.animation.AnimatedVisibility(
                visible = !state.isLoading,
                enter = fadeIn(tween(300)),
                exit = fadeOut(tween(100))
            ) {
                ListServiceDetails(listServiceEntity = serviceEntyties , isLoading = state.isLoading)
            }
        }

    }
}



@Preview(device = "spec:width=1000px,height=3640px,dpi=440")
@Composable
private fun Test() {
    YkisPAMTheme {
        ServiceDetailItem(
         serviceEntity = ServiceEntity(
                 dolg1 = 2443545435344.3
         )
        )
    }

}