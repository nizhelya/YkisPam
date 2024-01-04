package com.ykis.ykispam.pam.screens.service

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykis.ykispam.R
import com.ykis.ykispam.pam.domain.service.ServiceEntity
import com.ykis.ykispam.pam.screens.appartment.DetailTopAppBar
import com.ykis.ykispam.theme.YkisPAMTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun ServiceDetailScreen(
    popUpScreen: () -> Unit,
    viewModel: ServiceViewModel = hiltViewModel(),
    addressId :String,
    houseId:String,
    address: String,
    service:String,
    serviceName:String
) {
    LaunchedEffect(key1 = addressId ) {
        viewModel.getDetailService(addressId = addressId.toInt() , houseId = houseId.toInt(), service = service.toByte() , qty = 1, total = 0)
    }
    val serviceDetail by viewModel.serviceDetail.observeAsState(listOf(ServiceEntity()))
    ServiceDetailContent(serviceEntyties = serviceDetail, service = serviceName, navigateBack ={viewModel.navigateBack(popUpScreen)})
}

// TODO:, "внутренюю обрезку scroll елемента", добавить spacebetween для большого екрана , убрать лаг header'а при первом открьІтии item'а

@Composable
fun ServiceDetailItem(
    modifier: Modifier = Modifier,
    serviceEntity: ServiceEntity = ServiceEntity()
) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }

    val dateUnix = SimpleDateFormat("yyyy-MM-dd").parse(serviceEntity.data)
    Card(colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer, )
        , modifier = modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMediumLow,

                    )
            )
    ) {
        Column(modifier= modifier.padding(top=4.dp , bottom = 12.dp)) {
            Row(modifier = modifier.padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = SimpleDateFormat("LLLL yyyy", Locale("uk")).format(Date(dateUnix.time)).capitalize(),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    ), modifier = modifier
                        .weight(1f)

                )
                IconButton(onClick = {expanded = !expanded}) {
                    Icon(
                        imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = if (expanded) "Expand less" else "Expand more"
                    )
                }
            }
            Row (
                modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 18.dp),
                verticalAlignment =  Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                ColumnItemInTable(
                    expanded =expanded ,
                    alignment = Alignment.Start,
                    value1 =serviceEntity.service1.toString(),
                    value2 =serviceEntity.service2.toString(),
                    value3 =serviceEntity.service3.toString(),
                    value4 =serviceEntity.service4.toString(),
                    header = stringResource(id = R.string.services) ,
                    summary = stringResource(id = R.string.summary)
                )
                ColumnItemInTable(
                    expanded = expanded,
                    alignment =  Alignment.End,
                    value1 =serviceEntity.zadol1.toString() ,
                    value2 =serviceEntity.zadol2.toString() ,
                    value3 =serviceEntity.zadol3.toString() ,
                    value4 =serviceEntity.zadol4.toString() ,
                    header =stringResource(id = R.string.start_debt) ,
                    summary = serviceEntity.nachisleno.toString()
                )
                ColumnItemInTable(
                    expanded = expanded,
                    alignment = Alignment.End,
                    value1 =serviceEntity.nachisleno1.toString() ,
                    value2 =serviceEntity.nachisleno2.toString() ,
                    value3 =serviceEntity.nachisleno3.toString() ,
                    value4 =serviceEntity.nachisleno4.toString() ,
                    header = stringResource(id = R.string.paid) ,
                    summary = serviceEntity.nachisleno.toString()

                )
                ColumnItemInTable(
                    expanded = expanded,
                    alignment = Alignment.End,
                    value1 =serviceEntity.oplacheno1.toString() ,
                    value2 =serviceEntity.oplacheno2.toString() ,
                    value3 =serviceEntity.oplacheno3.toString() ,
                    value4 =serviceEntity.oplacheno4.toString() ,
                    header = stringResource(id = R.string.paid) ,
                    summary = serviceEntity.oplacheno.toString()

                )
                ColumnItemInTable(
                    expanded = expanded,
                    alignment = Alignment.End,
                    value1 = serviceEntity.dolg1.toString(),
                    value2 = serviceEntity.dolg2.toString(),
                    value3 = serviceEntity.dolg3.toString(),
                    value4 = serviceEntity.dolg4.toString(),
                    header = stringResource(id = R.string.end_debt) ,
                    summary = serviceEntity.dolg.toString()
                )
            }
        }
    }

}

@Composable
fun ListServiceDetails(
    modifier:Modifier = Modifier,
    listServiceEntity: List<ServiceEntity> = listOf(ServiceEntity(), ServiceEntity()),
    ) {
        LazyColumn() {
            items(items = listServiceEntity) { serviceDetail ->
                ServiceDetailItem(serviceEntity = serviceDetail)
            }
        }
    }

@Composable
fun ServiceDetailContent(
    modifier: Modifier = Modifier,
    serviceEntyties: List<ServiceEntity>,
    service: String,
    navigateBack: () -> Unit,
) {
    val years = listOf(2024,2023,2022,2021,2020,2019,2018,2017,2016,2015,2014,2013,2012)
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DetailTopAppBar(
            modifier,
            service,
            stringResource(id = R.string.services_detail),
            navigateBack = { navigateBack() })
        // TODO: сделать логику для FilterChip
        LazyRow {
            items(items = years){
                year->
                FilterChipSample(text = year.toString())
            }
        }
        ListServiceDetails(listServiceEntity =serviceEntyties )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true , widthDp = 340)
@Composable
fun PreviewItem(){
    YkisPAMTheme {
       ServiceDetailItem()
    }
}

@Composable
fun HeaderInTable(text:String , modifier: Modifier = Modifier) {
    Text(text = text , textAlign = TextAlign.Center , style = MaterialTheme.typography.labelLarge ,
        modifier = modifier.padding(horizontal = 4.dp)
    )
}

@Composable
fun NumberInTable(text: String , modifier: Modifier = Modifier) {
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
fun ColumnItemInTable(modifier: Modifier = Modifier ,
                        expanded :Boolean ,
                        alignment: Alignment.Horizontal,
                        value1 :String,
                        value2 :String,
                        value3 :String,
                        value4 :String,
                        header : String,
                        summary :String
                        ) {
    var componentWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    Column(horizontalAlignment =alignment, verticalArrangement = Arrangement.spacedBy(8.dp),
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
        AnimatedVisibility(expanded) {
            Column(horizontalAlignment = alignment,verticalArrangement = Arrangement.spacedBy(8.dp)) {
                NumberInTable(value1)
                DividerInTable(componentWidth = componentWidth)
                NumberInTable(value2)
                DividerInTable(componentWidth = componentWidth)
                NumberInTable(value3)
                DividerInTable(componentWidth = componentWidth)
                NumberInTable(value4)
                DividerInTable(componentWidth = componentWidth)
            }
        }
        HeaderInTable(
            text = summary)
    }
}

@Composable
fun DividerInTable(componentWidth:Dp,modifier:Modifier = Modifier) {
    Box(
        modifier
            .width(componentWidth)
            .height(1.dp)
            .background(color = MaterialTheme.colorScheme.onSurfaceVariant)

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChipSample(text:String , modifier: Modifier=Modifier) {
    var chipSelected by remember {
        mutableStateOf(false)
    }
    FilterChip(
        modifier = modifier.padding(horizontal = 4.dp),
        selected = chipSelected,
        label = {
            Text(text)
        },
        onClick = {chipSelected = !chipSelected },
        leadingIcon = if (chipSelected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        })
}