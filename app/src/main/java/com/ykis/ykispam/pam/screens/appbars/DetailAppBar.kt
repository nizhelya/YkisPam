package com.ykis.ykispam.pam.screens.appbars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.twotone.Commute
import androidx.compose.material.icons.twotone.CorporateFare
import androidx.compose.material.icons.twotone.FamilyRestroom
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.HotTub
import androidx.compose.material.icons.twotone.MonetizationOn
import androidx.compose.material.icons.twotone.Water
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.BaseUIState
import com.ykis.ykispam.R
import com.ykis.ykispam.navigation.ContentDetail
import com.ykis.ykispam.navigation.ContentType


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAppBar(
    modifier: Modifier = Modifier,
    contentType: ContentType,
    baseUIState: BaseUIState,
    contentDetail: ContentDetail,
    onBackPressed: () -> Unit
) {
    if (contentType == ContentType.SINGLE_PANE) {
        TopAppBar(
            modifier = modifier,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.inverseOnSurface
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
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(4.dp),
                            imageVector = when (contentDetail) {
                                ContentDetail.BTI -> Icons.TwoTone.Home
                                ContentDetail.FAMALY -> Icons.TwoTone.FamilyRestroom
                                ContentDetail.OSBB -> Icons.TwoTone.CorporateFare
                                ContentDetail.WATER_SERVICE -> Icons.TwoTone.Water
                                ContentDetail.WARM_SERVICE -> Icons.TwoTone.HotTub
                                ContentDetail.GARBAGE_SERVICE -> Icons.TwoTone.Commute
                                ContentDetail.PAYMENTS -> Icons.TwoTone.MonetizationOn
                                else -> Icons.TwoTone.FamilyRestroom
                            },
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.outline
                        )
                        Text(
                            modifier = Modifier.padding(top = 4.dp),
                            text = when (contentDetail) {
                                ContentDetail.BTI -> stringResource(id = R.string.bti)
                                ContentDetail.FAMALY -> stringResource(id = R.string.list_family)
                                ContentDetail.OSBB -> stringResource(id = R.string.vneski)
                                ContentDetail.WATER_SERVICE -> stringResource(id = R.string.vodokanal)
                                ContentDetail.WARM_SERVICE -> stringResource(id = R.string.ytke)
                                ContentDetail.GARBAGE_SERVICE -> stringResource(id = R.string.yzhtrans)
                                ContentDetail.PAYMENTS -> stringResource(id = R.string.payment_list)
                                else -> stringResource(id = R.string.bti)
                            },
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                }
            },
            navigationIcon = {
                FilledIconButton(
                    onClick = onBackPressed,
                    modifier = Modifier.padding(8.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button),
                        modifier = Modifier.size(14.dp)
                    )
                }

            },
//            actions = { TODO() }
        )

    } else {
        TopAppBar(
            modifier = modifier,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.inverseOnSurface
            ),
            title = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment =  Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding(8.dp),
                            imageVector = when (contentDetail) {
                                ContentDetail.BTI -> Icons.TwoTone.Home
                                ContentDetail.FAMALY -> Icons.TwoTone.FamilyRestroom
                                ContentDetail.OSBB -> Icons.TwoTone.CorporateFare
                                ContentDetail.WATER_SERVICE -> Icons.TwoTone.Water
                                ContentDetail.WARM_SERVICE -> Icons.TwoTone.HotTub
                                ContentDetail.GARBAGE_SERVICE -> Icons.TwoTone.Commute
                                ContentDetail.PAYMENTS -> Icons.TwoTone.MonetizationOn
                                else -> Icons.TwoTone.FamilyRestroom
                            },
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.outline
                        )
                        Text(
                            text = when (contentDetail) {
                                ContentDetail.BTI -> stringResource(id = R.string.bti)
                                ContentDetail.FAMALY -> stringResource(id = R.string.list_family)
                                ContentDetail.OSBB -> baseUIState.apartment.osbb
                                ContentDetail.WATER_SERVICE -> stringResource(id = R.string.vodokanal)
                                ContentDetail.WARM_SERVICE -> stringResource(id = R.string.ytke)
                                ContentDetail.GARBAGE_SERVICE -> stringResource(id = R.string.yzhtrans)
                                ContentDetail.PAYMENTS -> stringResource(id = R.string.payment_list)
                                else -> stringResource(id = R.string.bti)
                            },
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                }
            },
//            navigationIcon = { TODO() },
//            actions = { TODO() }
        )
    }
}