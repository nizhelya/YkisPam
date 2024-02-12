package com.ykis.ykispam.ui.screens.appartment.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.R
import com.ykis.ykispam.ui.YkisPamAppState
import com.ykis.ykispam.core.composable.HelpAlertCard
import com.ykis.ykispam.ui.navigation.ContentDetail
import com.ykis.ykispam.ui.navigation.ContentType
import com.ykis.ykispam.ui.navigation.NavigationType
import com.ykis.ykispam.ui.screens.appbars.ApartmentTopAppBar


@Composable
fun ListContent(
    modifier: Modifier = Modifier,
    appState: YkisPamAppState,
    baseUIState: BaseUIState,
    deleteApartment: () -> Unit,
    navigateToDetail: (ContentDetail, ContentType) -> Unit,
    onDrawerClicked: () -> Unit = {},
    navigationType: NavigationType
) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var setSelected by rememberSaveable { mutableStateOf<ContentDetail?>(null) }

    val yes: Byte = 1
    Row(
        modifier = Modifier
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

            ApartmentTopAppBar(
                appState = appState,
                apartment = baseUIState.apartment,
                isButtonAction = baseUIState.apartment.address.isNotEmpty(),
                onButtonAction = { deleteApartment() },
                onButtonPressed = { onDrawerClicked() },
                navigationType = navigationType
            )
            Row(
                modifier = Modifier
                    .fillMaxSize(),

                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .fillMaxHeight()
                    ,

                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    if (baseUIState.addressId != 0) {
                        Card(
                            modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)

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
                                    serviceName = stringResource(id = R.string.bti),
                                    isSelected = setSelected?.let {
                                        it == ContentDetail.BTI
                                    } ?: false,
                                    navigateToDetail = navigateToDetail,
                                    contentDetail = ContentDetail.BTI,
                                ) {
                                    setSelected = ContentDetail.BTI
                                }
                                ListItem(
                                    modifier = modifier,
                                    imageVector = Icons.TwoTone.FamilyRestroom,
                                    serviceName = stringResource(id = R.string.list_family),
                                    isSelected = setSelected?.let {
                                        it == ContentDetail.FAMILY
                                    } ?: false,
                                    navigateToDetail = navigateToDetail,
                                    contentDetail = ContentDetail.FAMILY,
                                ) {
                                    setSelected = ContentDetail.FAMILY
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
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
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
                                        serviceName = baseUIState.apartment.osbb,
                                        isSelected = setSelected?.let {
                                            it == ContentDetail.OSBB
                                        } ?: false,
                                        navigateToDetail = navigateToDetail,
                                        contentDetail = ContentDetail.OSBB,
                                    ) {
                                        setSelected = ContentDetail.OSBB
                                    }

                                }
                                if (baseUIState.apartment.voda == yes || baseUIState.apartment.stoki == yes) {
                                    ListItem(
                                        modifier = modifier,
                                        imageVector = Icons.Default.Water,
                                        serviceName = stringResource(id = R.string.vodokanal),
                                        isSelected = setSelected?.let {
                                            it == ContentDetail.WARM_SERVICE
                                        } ?: false,
                                        navigateToDetail = navigateToDetail,
                                        contentDetail = ContentDetail.WATER_SERVICE,
                                    ) {
                                        setSelected = ContentDetail.WATER_SERVICE
                                    }
                                }
                                if (baseUIState.apartment.otoplenie == yes) {
                                    ListItem(
                                        modifier = modifier,
                                        imageVector = Icons.TwoTone.HotTub,
                                        serviceName = stringResource(id = R.string.ytke),
                                        isSelected = setSelected?.let {
                                            it == ContentDetail.WARM_SERVICE
                                        } ?: false,
                                        navigateToDetail = navigateToDetail,
                                        contentDetail = ContentDetail.WARM_SERVICE,
                                    ) {
                                        setSelected = ContentDetail.WARM_SERVICE
                                    }
                                }
                                if (baseUIState.apartment.tbo == yes) {
                                    ListItem(
                                        modifier = modifier,
                                        imageVector = Icons.TwoTone.Commute,
                                        serviceName = stringResource(id = R.string.yzhtrans),
                                        isSelected = setSelected?.let {
                                            it == ContentDetail.GARBAGE_SERVICE
                                        } ?: false,
                                        navigateToDetail = navigateToDetail,
                                        contentDetail = ContentDetail.GARBAGE_SERVICE,
                                    ) {
                                        setSelected = ContentDetail.GARBAGE_SERVICE
                                    }
                                }
                                ListItem(
                                    modifier = modifier,
                                    imageVector = Icons.TwoTone.MonetizationOn,
                                    serviceName = stringResource(id = R.string.payment_list),
                                    isSelected = setSelected?.let {
                                        it == ContentDetail.PAYMENTS
                                    } ?: false,
                                    navigateToDetail = navigateToDetail,
                                    contentDetail = ContentDetail.PAYMENTS,
                                ) {
                                    setSelected = ContentDetail.PAYMENTS
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