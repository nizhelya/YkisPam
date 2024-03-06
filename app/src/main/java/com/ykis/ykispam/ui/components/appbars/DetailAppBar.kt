package com.ykis.ykispam.ui.components.appbars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Commute
import androidx.compose.material.icons.filled.CorporateFare
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.HotTub
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Water
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.R
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.navigation.ContentDetail
import com.ykis.ykispam.ui.navigation.ContentType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAppBar(
    modifier: Modifier = Modifier,
    contentType: ContentType,
    baseUIState: BaseUIState,
    contentDetail: ContentDetail,
    onActionButtonClick : () -> Unit ={},
    onBackPressed: () -> Unit,
) {
    if (contentType == ContentType.SINGLE_PANE) {
        TopAppBar(
            modifier = modifier,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = baseUIState.apartment.address,
                        style = MaterialTheme.typography.titleMedium,
//                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(24.dp),
                            imageVector = when (contentDetail) {
                                ContentDetail.BTI -> Icons.Default.Home
                                ContentDetail.FAMILY -> Icons.Default.FamilyRestroom
                                ContentDetail.OSBB -> Icons.Default.CorporateFare
                                ContentDetail.WATER_SERVICE -> Icons.Default.Water
                                ContentDetail.WARM_SERVICE -> Icons.Default.HotTub
                                ContentDetail.GARBAGE_SERVICE -> Icons.Default.Commute
                                ContentDetail.PAYMENT_LIST -> Icons.Default.MonetizationOn
                                    ContentDetail.WATER_METER -> ImageVector.vectorResource(id = R.drawable.ic_water_meter)
                                    ContentDetail.HEAT_METER -> ImageVector.vectorResource(id = R.drawable.ic_heat_meter)
                                ContentDetail.WATER_READINGS ->  ImageVector.vectorResource(id = R.drawable.ic_reading)
                                ContentDetail.HEAT_READINGS ->  ImageVector.vectorResource(id = R.drawable.ic_reading)
                            },
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            modifier = Modifier.padding(top = 4.dp),
                            text = when (contentDetail) {
                                ContentDetail.BTI -> stringResource(id = R.string.bti)
                                ContentDetail.FAMILY -> stringResource(id = R.string.list_family)
                                ContentDetail.OSBB -> stringResource(id = R.string.vneski)
                                ContentDetail.WATER_SERVICE -> stringResource(id = R.string.vodokanal)
                                ContentDetail.WARM_SERVICE -> stringResource(id = R.string.ytke)
                                ContentDetail.GARBAGE_SERVICE -> stringResource(id = R.string.yzhtrans)
                                ContentDetail.PAYMENT_LIST -> stringResource(id = R.string.payment_list)
                                ContentDetail.WATER_METER -> stringResource(id = R.string.water_meter_label)
                                ContentDetail.HEAT_METER -> stringResource(id = R.string.heat_meter_label)
                                ContentDetail.WATER_READINGS -> stringResource(id = R.string.reading_history)
                                ContentDetail.HEAT_READINGS -> stringResource(id = R.string.reading_history)
                            },
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                }
            },
            navigationIcon = {
                IconButton(
                    onClick = onBackPressed
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }

            },
            actions = {
                if(contentDetail == ContentDetail.WATER_METER || contentDetail == ContentDetail.HEAT_METER)
                    IconButton(
                        onClick = onActionButtonClick,
                    ){
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_history),
                            contentDescription = stringResource(id = R.string.reading_history),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

            }
        )

    } else {
        TopAppBar(
            modifier = modifier,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent
            ),
            title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(8.dp)
                                .size(24.dp),
                            imageVector = when (contentDetail) {
                                ContentDetail.BTI -> Icons.Default.Home
                                ContentDetail.FAMILY -> Icons.Default.FamilyRestroom
                                ContentDetail.OSBB -> Icons.Default.CorporateFare
                                ContentDetail.WATER_SERVICE -> Icons.Default.Water
                                ContentDetail.WARM_SERVICE -> Icons.Default.HotTub
                                ContentDetail.GARBAGE_SERVICE -> Icons.Default.Commute
                                ContentDetail.PAYMENT_LIST -> Icons.Default.MonetizationOn
                                ContentDetail.WATER_METER -> ImageVector.vectorResource(id = R.drawable.ic_water_meter)
                                ContentDetail.HEAT_METER -> ImageVector.vectorResource(id = R.drawable.ic_heat_meter)
                                ContentDetail.WATER_READINGS -> ImageVector.vectorResource(id = R.drawable.ic_reading)
                                ContentDetail.HEAT_READINGS -> ImageVector.vectorResource(id = R.drawable.ic_reading)

                            },
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.outline
                        )
                        Text(
                            text = when (contentDetail) {
                                ContentDetail.BTI -> stringResource(id = R.string.bti)
                                ContentDetail.FAMILY -> stringResource(id = R.string.list_family)
                                ContentDetail.OSBB -> baseUIState.apartment.osbb.toString()
                                ContentDetail.WATER_SERVICE -> stringResource(id = R.string.vodokanal)
                                ContentDetail.WARM_SERVICE -> stringResource(id = R.string.ytke)
                                ContentDetail.GARBAGE_SERVICE -> stringResource(id = R.string.yzhtrans)
                                ContentDetail.PAYMENT_LIST -> stringResource(id = R.string.payment_list)
                                ContentDetail.WATER_METER -> stringResource(id = R.string.water_meter_label)
                                ContentDetail.HEAT_METER -> stringResource(id = R.string.heat_meter_label)
                                ContentDetail.WATER_READINGS -> stringResource(id = R.string.reading_history)
                                ContentDetail.HEAT_READINGS -> stringResource(id = R.string.reading_history)
                            },
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
            },
            navigationIcon = {
                IconButton(
                    onClick = onBackPressed,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button),
                    )
                }

            },
            actions = {
                if(contentDetail == ContentDetail.WATER_METER || contentDetail == ContentDetail.HEAT_METER)
                    IconButton(
                        onClick = onActionButtonClick,
                    ){
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_history),
                            contentDescription = stringResource(id = R.string.reading_history),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

            }
        )
    }
}