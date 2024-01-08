package com.ykis.ykispam.pam.screens.appartment.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Water
import androidx.compose.material.icons.twotone.Commute
import androidx.compose.material.icons.twotone.CorporateFare
import androidx.compose.material.icons.twotone.FamilyRestroom
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.HotTub
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material.icons.twotone.MonetizationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.BaseUIState
import com.ykis.ykispam.R
import com.ykis.ykispam.YkisPamAppState
import com.ykis.ykispam.core.composable.HelpAlertCard
import com.ykis.ykispam.navigation.ContentDetail
import com.ykis.ykispam.navigation.ContentType
import com.ykis.ykispam.pam.screens.appbars.ApartmentTopAppBar


@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    contentType: ContentType,
    appState: YkisPamAppState,
    baseUIState: BaseUIState,
    deleteApartment: () -> Unit,
    navigateToDetail: (ContentDetail, ContentType) -> Unit,
    onDrawerClicked: () -> Unit = {},
) {

    var showDialog by rememberSaveable { mutableStateOf(false) }
    val yes: Byte = 1
    Row(
        modifier = Modifier
            .padding(PaddingValues(4.dp))
//            .verticalScroll(rememberScrollState())
            .fillMaxSize()
//            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.inverseOnSurface),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.inverseOnSurface),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ApartmentTopAppBar(
                contentType = contentType,
                appState = appState,
                apartment = baseUIState.apartment,
                isDriverClicked = true,
                isButtonAction = baseUIState.apartment.address.isNotEmpty(),
                onButtonAction = { deleteApartment() },
                onButtonPressed = { onDrawerClicked() }
            )
            Row(
                modifier = Modifier
                    .padding(PaddingValues(4.dp))
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.inverseOnSurface),

                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .padding(PaddingValues(4.dp))
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.inverseOnSurface),

                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    if (baseUIState.addressId != 0) {
                        Card(
                            modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)

                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.xp),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .weight(1f),

                                        )
                                    IconButton(
                                        onClick = { showDialog = true },
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.inverseOnSurface)
                                    ) {
                                        Icon(
                                            imageVector = Icons.TwoTone.Info,
                                            contentDescription = "Info",
                                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }

                                }
                                ListItem(
                                    modifier = modifier,
                                    imageVector = Icons.TwoTone.Home,
                                    serviseName = stringResource(id = R.string.bti),
                                    baseUIState = baseUIState,
                                    org = baseUIState.apartment.osbb,
                                    contentDetail = ContentDetail.BTI,
                                ) { contentDetail ->
                                    navigateToDetail(contentDetail, ContentType.SINGLE_PANE)
                                }
                                ListItem(
                                    modifier = modifier,
                                    imageVector = Icons.TwoTone.FamilyRestroom,
                                    serviseName = stringResource(id = R.string.list_family),
                                    baseUIState = baseUIState,
                                    org = baseUIState.apartment.osbb,
                                    contentDetail = ContentDetail.FAMALY,
                                ) { contentDetail ->
                                    navigateToDetail(contentDetail, ContentType.SINGLE_PANE)
                                }
                                if (showDialog) {
                                    HelpAlertCard(
                                        title = stringResource(id = R.string.consumed_services),
                                        text = stringResource(id = R.string.consumed_services),
                                        org = "",
                                        showDialog = true,
                                        onShowDialogChange = { showDialog = it }
                                    )
                                }
                            }
                        }
                        Card(
                            modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.consumed_services),
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .weight(1f),

                                        )
                                    IconButton(
                                        onClick = { showDialog = true },
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.inverseOnSurface)
                                    ) {
                                        Icon(
                                            imageVector = Icons.TwoTone.Info,
                                            contentDescription = "Info",
                                            tint = MaterialTheme.colorScheme.outline
                                        )
                                    }

                                }
                                if (baseUIState.apartment.kvartplata == yes) {
                                    ListItem(
                                        modifier = modifier,
                                        imageVector = Icons.TwoTone.CorporateFare,
                                        serviseName = baseUIState.apartment.osbb,
                                        baseUIState = baseUIState,
                                        org = baseUIState.apartment.osbb,
                                        contentDetail = ContentDetail.OSBB,
                                    ) { contentDetail ->
                                        navigateToDetail(contentDetail, ContentType.SINGLE_PANE)
                                    }

                                }
                                if (baseUIState.apartment.voda == yes || baseUIState.apartment.stoki == yes) {
                                    ListItem(
                                        modifier = modifier,
                                        imageVector = Icons.Default.Water,
                                        serviseName = stringResource(id = R.string.vodokanal),
                                        baseUIState = baseUIState,
                                        org = stringResource(id = R.string.vodokanal),
                                        contentDetail = ContentDetail.WATER_SERVICE,
                                    ) { contentDetail ->
                                        navigateToDetail(contentDetail, ContentType.SINGLE_PANE)
                                    }
                                }
                                if (baseUIState.apartment.otoplenie == yes) {
                                    ListItem(
                                        modifier = modifier,
                                        imageVector = Icons.TwoTone.HotTub,
                                        serviseName = stringResource(id = R.string.ytke),
                                        baseUIState = baseUIState,
                                        org = stringResource(id = R.string.ytke),
                                        contentDetail = ContentDetail.WARM_SERVICE,
                                    ) { contentDetail ->
                                        navigateToDetail(contentDetail, ContentType.SINGLE_PANE)
                                    }

                                }
                                if (baseUIState.apartment.tbo == yes) {
                                    ListItem(
                                        modifier = modifier,
                                        imageVector = Icons.TwoTone.Commute,
                                        serviseName = stringResource(id = R.string.yzhtrans),
                                        baseUIState = baseUIState,
                                        org = stringResource(id = R.string.yzhtrans),
                                        contentDetail = ContentDetail.GARBAGE_SERVICE,
                                    ) { contentDetail ->
                                        navigateToDetail(contentDetail, ContentType.SINGLE_PANE)
                                    }
                                }
                                ListItem(
                                    modifier = modifier,
                                    imageVector = Icons.TwoTone.MonetizationOn,
                                    serviseName = stringResource(id = R.string.payment_list),
                                    baseUIState = baseUIState,
                                    org = stringResource(id = R.string.payment_list),
                                    contentDetail = ContentDetail.PAYMENTS,
                                ) { contentDetail ->
                                    navigateToDetail(contentDetail, ContentType.SINGLE_PANE)
                                }
                                ListItem(
                                    modifier = modifier,
                                    imageVector = Icons.TwoTone.MonetizationOn,
                                    serviseName = stringResource(id = R.string.payment_list),
                                    baseUIState = baseUIState,
                                    org = stringResource(id = R.string.payment_list),
                                    contentDetail = ContentDetail.PAYMENTS,
                                ) { contentDetail ->
                                    navigateToDetail(contentDetail, ContentType.SINGLE_PANE)
                                }
                                ListItem(
                                    modifier = modifier,
                                    imageVector = Icons.TwoTone.MonetizationOn,
                                    serviseName = stringResource(id = R.string.payment_list),
                                    baseUIState = baseUIState,
                                    org = stringResource(id = R.string.payment_list),
                                    contentDetail = ContentDetail.PAYMENTS,
                                ) { contentDetail ->
                                    navigateToDetail(contentDetail, ContentType.SINGLE_PANE)
                                }
                                if (showDialog) {
                                    HelpAlertCard(
                                        title = stringResource(id = R.string.consumed_services),
                                        text = stringResource(id = R.string.consumed_services),
                                        org = "",
                                        showDialog = true,
                                        onShowDialogChange = { showDialog = it }
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}