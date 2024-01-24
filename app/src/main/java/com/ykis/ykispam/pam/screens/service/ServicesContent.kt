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

package com.ykis.ykispam.pam.screens.service

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykis.ykispam.BaseUIState
import com.ykis.ykispam.R
import com.ykis.ykispam.navigation.ContentDetail
import com.ykis.ykispam.pam.domain.service.ServiceEntity
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


    var selectedChip by rememberSaveable {mutableStateOf(year)}

    LaunchedEffect(key1 = selectedChip) {
        baseUIState.uid?.let {
            viewModel.getDetailService(
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
                total = 0
            )
        }
    }
    val serviceDetail by viewModel.serviceDetail.observeAsState(listOf(ServiceEntity()))

    ServiceDetailContent(
        year = year,
        serviceEntyties = serviceDetail,
        onSelectedChanged = { selectedChip = it },
        selectedChip = selectedChip
    )
}

@Composable
fun ServiceDetailItem(
    modifier: Modifier = Modifier,
    serviceEntity: ServiceEntity = ServiceEntity()
) {
    val dateUnix = SimpleDateFormat("yyyy-MM-dd").parse(serviceEntity.data)

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondary,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ), modifier = modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMediumLow,

                    )
            )
    ) {
        Column(modifier = modifier.padding(top = 4.dp, bottom = 12.dp)) {
            Text(
                text = SimpleDateFormat("LLLL yyyy", Locale("uk")).format(Date(dateUnix.time))
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                    ), modifier = modifier
                        .padding(16.dp)
                )
            Row(
                modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 18.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Start,
            ) {
                ColumnItemInTable(
                    alignment = Alignment.Start,
                    value1 = serviceEntity.service1.toString(),
                    value2 = serviceEntity.service2.toString(),
                    value3 = serviceEntity.service3.toString(),
                    value4 = serviceEntity.service4.toString(),
                    header = stringResource(id = R.string.services),
                    summary = stringResource(id = R.string.summary)
                )
                ColumnItemInTable(
                    alignment = Alignment.End,
                    value1 = serviceEntity.zadol1.toString(),
                    value2 = serviceEntity.zadol2.toString(),
                    value3 = serviceEntity.zadol3.toString(),
                    value4 = serviceEntity.zadol4.toString(),
                    header = stringResource(id = R.string.start_debt),
                    summary = serviceEntity.nachisleno.toString()
                )
                ColumnItemInTable(
                    alignment = Alignment.End,
                    value1 = serviceEntity.nachisleno1.toString(),
                    value2 = serviceEntity.nachisleno2.toString(),
                    value3 = serviceEntity.nachisleno3.toString(),
                    value4 = serviceEntity.nachisleno4.toString(),
                    header = stringResource(id = R.string.paid),
                    summary = serviceEntity.nachisleno.toString()

                )
                ColumnItemInTable(
                    alignment = Alignment.End,
                    value1 = serviceEntity.oplacheno1.toString(),
                    value2 = serviceEntity.oplacheno2.toString(),
                    value3 = serviceEntity.oplacheno3.toString(),
                    value4 = serviceEntity.oplacheno4.toString(),
                    header = stringResource(id = R.string.accrued_text),
                    summary = serviceEntity.oplacheno.toString()

                )
                ColumnItemInTable(
                    alignment = Alignment.End,
                    value1 = serviceEntity.dolg1.toString(),
                    value2 = serviceEntity.dolg2.toString(),
                    value3 = serviceEntity.dolg3.toString(),
                    value4 = serviceEntity.dolg4.toString(),
                    header = stringResource(id = R.string.end_debt),
                    summary = serviceEntity.dolg.toString()
                )
            }
        }
    }

}

@Composable
fun ListServiceDetails(
    listServiceEntity: List<ServiceEntity> = listOf(ServiceEntity(), ServiceEntity()),
) {
    if(listServiceEntity.isEmpty()){
        EmptyListScreen()
    }else LazyColumn() {
        items(items = listServiceEntity) { serviceDetail ->
            ServiceDetailItem(serviceEntity = serviceDetail)
        }
    }
}

@Composable
fun ServiceDetailContent(
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
        ListServiceDetails(listServiceEntity = serviceEntyties)
    }
}

@Composable
fun HeaderInTable(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text, textAlign = TextAlign.Center, style = MaterialTheme.typography.labelLarge,
        modifier = modifier.padding(horizontal = 4.dp)
    )
}

@Composable
fun NumberInTable(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text, style = TextStyle(
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp
        ),
        modifier = modifier.padding(horizontal = 4.dp)
    )
}

@Composable
fun ColumnItemInTable(
    modifier: Modifier = Modifier,
    alignment: Alignment.Horizontal,
    value1: String,
    value2: String,
    value3: String,
    value4: String,
    header: String,
    summary: String
) {
    var componentWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current;
    Column(horizontalAlignment = alignment, verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .onGloballyPositioned {
                componentWidth = with(density) {
                    it.size.width.toDp()

                }
            }
    )
    {
        HeaderInTable(header)
        Spacer(modifier = modifier.widthIn(min = componentWidth))
            Column(
                horizontalAlignment = alignment,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (value1 !== "null" && value1 !== "none") {
                    NumberInTable(value1)
                    DividerInTable(componentWidth = componentWidth)
                }
                if (value2 != "null" && value2 != "none") {
                    Log.d("service_test", value2)
                    NumberInTable(value2)
                    DividerInTable(componentWidth = componentWidth)
                }
                if (value3 != "null" && value3 != "none") {
                    NumberInTable(value3)
                    DividerInTable(componentWidth = componentWidth)
                }
                if (value4 != "null" && value4 != "none") {
                    NumberInTable(value4)
                    DividerInTable(componentWidth = componentWidth)
                }
        }
        HeaderInTable(
            text = summary
        )
    }
}

@Composable
fun DividerInTable(componentWidth: Dp, modifier: Modifier = Modifier) {
    Box(
        modifier
            .width(componentWidth)
            .height(1.dp)
            .background(color = MaterialTheme.colorScheme.onSurfaceVariant)

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChipSample(
    text: String,
    modifier: Modifier = Modifier,
    onSelectedChanged: (String) -> Unit = {},
    isSelected: Boolean = false
) {
    FilterChip(
        modifier = modifier
            .padding(horizontal = 4.dp)
            .animateContentSize(),
        selected = isSelected,
        label = {
            Text(text)
        },
        onClick = { onSelectedChanged(text) },
        leadingIcon = if (isSelected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        },
    )
}

@Composable
fun GroupFilterChip(
    list: List<String>,
    selectedChip: Any? = null,
    onSelectedChanged: (String) -> Unit = {}
) {
    LazyRow {
        items(items = list) { text ->
            FilterChipSample(
                text = text,
                onSelectedChanged = { onSelectedChanged(text) },
                isSelected = text == selectedChip
            )
        }
    }
}

@Composable
fun EmptyListScreen(modifier: Modifier = Modifier,
                    useDarkTheme: Boolean = isSystemInDarkTheme()
) {
    val paintRes = if(useDarkTheme){
        R.drawable.ic_empty_box_dark
    }else{
        R.drawable.ic_empty_box_light
    }
    Column(modifier = modifier
        .fillMaxSize()
        .padding(bottom = 48.dp) , horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Image(painter = painterResource(id = paintRes), contentDescription = null)
        Text(text = stringResource(R.string.no_payment), style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp
        )
        )
        Text(text = stringResource(R.string.no_payment_year))
    }
}





