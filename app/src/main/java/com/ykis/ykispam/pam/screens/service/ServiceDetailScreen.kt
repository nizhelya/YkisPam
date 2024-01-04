package com.ykis.ykispam.pam.screens.service

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
    address: String
) {
    LaunchedEffect(key1 = addressId ) {
        viewModel.getDetailService(addressId = addressId.toInt() , houseId = houseId.toInt(), service = 1 , qty = 1, total = 0)
    }
    val serviceDetail by viewModel.serviceDetail.observeAsState(listOf(ServiceEntity()))
    ServiceDetailContent(serviceEntyties = serviceDetail, address = address , navigateBack ={viewModel.navigateBack(popUpScreen)})
}

// TODO: добавить divider , сделать кнопку expand рабочей ,поменять стиль summary , "внутренюю обрезку scroll елемента" , возможность смотреть другие услуги , добавить spacebetween для большого екрана

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
        Column(modifier= modifier.padding(top=12.dp , bottom = 12.dp)) {
            Row(modifier = modifier.padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = SimpleDateFormat("LLLL yyyy", Locale("uk")).format(Date(dateUnix.time)).capitalize(),                    style = MaterialTheme.typography.headlineSmall.copy(
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
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    HeaderInTable(text = stringResource(id = R.string.services))
                    AnimatedVisibility(expanded) {
                        Column(horizontalAlignment = Alignment.Start,verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            NumberInTable(text = serviceEntity.service1.toString())
                            NumberInTable(text = serviceEntity.service2.toString())
                            NumberInTable(text = serviceEntity.service3.toString())
                            NumberInTable(text = serviceEntity.service4.toString())
                        }
                    }
                    Text(
                        text = stringResource(id = R.string.summary),
                        style = MaterialTheme.typography.titleMedium)
                }
                Column(horizontalAlignment = Alignment.End , verticalArrangement = Arrangement.spacedBy(8.dp) )  {
                    HeaderInTable(text = stringResource(id = R.string.start_debt))
                    AnimatedVisibility(expanded) {
                        Column(horizontalAlignment = Alignment.End,verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            NumberInTable(serviceEntity.zadol1.toString())
                            NumberInTable(serviceEntity.zadol2.toString())
                            NumberInTable(serviceEntity.zadol3.toString())
                            NumberInTable(serviceEntity.zadol4.toString())
                        }
                    }
                    Text(
                        text = serviceEntity.zadol.toString(),
                        style = MaterialTheme.typography.titleMedium)
                }
                Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(8.dp))  {
                    HeaderInTable(text = stringResource(id = R.string.accured_text))
                    AnimatedVisibility(expanded) {
                        Column(horizontalAlignment = Alignment.End,verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            NumberInTable(serviceEntity.nachisleno1.toString())
                            NumberInTable(serviceEntity.nachisleno2.toString())
                            NumberInTable(serviceEntity.nachisleno3.toString())
                            NumberInTable(serviceEntity.nachisleno4.toString())
                        }
                    }
                    Text(
                        text = serviceEntity.nachisleno.toString(),
                        style = MaterialTheme.typography.titleMedium)
                }
                Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = modifier.wrapContentWidth(unbounded = true))  {
                    HeaderInTable(text = stringResource(id = R.string.paid))
                    AnimatedVisibility(expanded) {
                        Column(horizontalAlignment = Alignment.End,verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            NumberInTable(serviceEntity.oplacheno1.toString())
                            NumberInTable(serviceEntity.oplacheno2.toString())
                            NumberInTable(serviceEntity.oplacheno3.toString())
                            NumberInTable(serviceEntity.oplacheno4.toString())
                        }
                    }
                    Text(
                        text = serviceEntity.oplacheno.toString(),
                        style = MaterialTheme.typography.titleMedium)
                }
                Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    HeaderInTable(text = stringResource(id = R.string.end_debt))
                    AnimatedVisibility(expanded) {
                        Column(horizontalAlignment = Alignment.End,verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            NumberInTable(serviceEntity.dolg1.toString())
                            NumberInTable(serviceEntity.dolg2.toString())
                            NumberInTable(serviceEntity.dolg3.toString())
                            NumberInTable(serviceEntity.dolg4.toString())
                        }
                    }
                    Text(
                        text =serviceEntity.dolg.toString(),
                        style = MaterialTheme.typography.titleMedium)
                }
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
    address: String,
    navigateBack: () -> Unit,
) {

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
            stringResource(id = R.string.services_detail),
            address,
            navigateBack = { navigateBack() })
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
    Text(text = text , textAlign = TextAlign.Center , style = MaterialTheme.typography.labelLarge )
}

@Composable
fun NumberInTable(text: String) {
    Text(
        text = text, style = TextStyle(
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp
        )
    )
}