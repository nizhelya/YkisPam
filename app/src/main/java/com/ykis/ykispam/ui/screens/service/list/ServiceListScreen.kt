package com.ykis.ykispam.ui.screens.service.list

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Commute
import androidx.compose.material.icons.filled.CorporateFare
import androidx.compose.material.icons.filled.GasMeter
import androidx.compose.material.icons.filled.HotTub
import androidx.compose.material.icons.filled.Water
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ykis.ykispam.R
import com.ykis.ykispam.domain.service.request.ServiceParams
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.components.appbars.DefaultAppBar
import com.ykis.ykispam.ui.navigation.ContentDetail
import com.ykis.ykispam.ui.navigation.NavigationType
import com.ykis.ykispam.ui.theme.YkisPAMTheme
import com.ykis.ykispam.ui.theme.extendedColor

@Composable
fun ServiceListScreen(
    totalDebtState: TotalDebtState,
    baseUIState: BaseUIState,
    navigationType: NavigationType,
    onDrawerClick : () -> Unit,
    getTotalServiceDebt: (ServiceParams) -> Unit,
    onServiceClick :(ContentDetail)->Unit
) {
    LaunchedEffect(key1 = true) {
        getTotalServiceDebt(
            ServiceParams(
                uid = baseUIState.uid!!,
                addressId = baseUIState.addressId,
                houseId = baseUIState.houseId,
                service = 0,
                total = 1,
                year = "2023"
            )
        )
    }
    val totalServiceDebtList : List<TotalServiceDebt> = listOf(
            TotalServiceDebt(
                name = baseUIState.osbb,
                color = MaterialTheme.colorScheme.extendedColor.sectorColor4.color,
                debt = totalDebtState.totalDebt.dolg4!!.toFloat(),
                icon = Icons.Default.CorporateFare,
                contentDetail = ContentDetail.OSBB
            ),
            TotalServiceDebt(
                name = stringResource( R.string.vodokanal),
                color = MaterialTheme.colorScheme.extendedColor.sectorColor1.color,
                debt = totalDebtState.totalDebt.dolg1!!.toFloat(),
                icon = Icons.Default.Water,
                contentDetail = ContentDetail.WATER_SERVICE

            ),
            TotalServiceDebt(
                name = stringResource(id =  R.string.ytke),
                color = MaterialTheme.colorScheme.extendedColor.sectorColor2.color,
                debt = totalDebtState.totalDebt.dolg2!!.toFloat(),
                icon = Icons.Default.HotTub,
                contentDetail = ContentDetail.WARM_SERVICE
            ),
            TotalServiceDebt(
                name = stringResource(id =R.string.yzhtrans),
                color = MaterialTheme.colorScheme.extendedColor.sectorColor3.color,
                debt = totalDebtState.totalDebt.dolg3!!.toFloat(),
                icon = Icons.Default.Commute,
                contentDetail = ContentDetail.GARBAGE_SERVICE
            ),
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DefaultAppBar(
            title = stringResource(id = R.string.accrued),
            onBackClick ={},
            onDrawerClick = onDrawerClick,
            canNavigateBack = false,
            navigationType = navigationType
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.animation.AnimatedVisibility(
                visible = totalDebtState.isLoading,
                exit = fadeOut(tween(delayMillis = 500)),
                enter = fadeIn(tween(delayMillis = 500))
            ) {
                CircularProgressIndicator()
            }
            androidx.compose.animation.AnimatedVisibility(
                visible = !totalDebtState.isLoading,
                exit = fadeOut(tween(delayMillis = 500)),
                enter = fadeIn(tween(delayMillis = 500))
            ) {
                ServiceListStateless(
                    modifier = Modifier.fillMaxSize(),
                    items = totalServiceDebtList,
                    debts = { totalServiceDebtList -> totalServiceDebtList.debt },
                    colors = { totalServiceDebtList -> totalServiceDebtList.color },
                    total = totalDebtState.totalDebt.dolg!!.toFloat(),
                    circleLabel = stringResource(R.string.summary),
                    rows = {
                        ServiceRow(
                            color = it.color,
                            title = it.name,
                            debt = it.debt,
                            icon = it.icon,
                            onClick = {
                                onServiceClick(it.contentDetail)
                            }
                        )
                    },
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
private fun PreviewRow() {
    YkisPAMTheme {
        Card {
            Column {
                ServiceRow(
                    color = Color.Blue,
                    title = stringResource(id = R.string.yzhtrans),
                    debt = 564.00f,
                    icon = Icons.Default.GasMeter,
                    onClick = {}
                )
                ServiceRow(
                    color = Color.Blue,
                    title = stringResource(id = R.string.yzhtrans),
                    debt = 564.00f,
                    icon = Icons.Default.GasMeter,
                    onClick = {}
                )
                ServiceRow(
                    color = Color.Blue,
                    title = stringResource(id = R.string.yzhtrans),
                    debt = 564.00f,
                    icon = Icons.Default.GasMeter,
                    onClick = {}
                )
            }
        }

    }
}