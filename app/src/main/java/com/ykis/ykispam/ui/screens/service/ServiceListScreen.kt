package com.ykis.ykispam.ui.screens.service

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Commute
import androidx.compose.material.icons.filled.CorporateFare
import androidx.compose.material.icons.filled.GasMeter
import androidx.compose.material.icons.filled.HotTub
import androidx.compose.material.icons.filled.Water
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ykis.ykispam.R
import com.ykis.ykispam.domain.service.request.ServiceParams
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.components.AnimatedCircle
import com.ykis.ykispam.ui.components.appbars.DefaultAppBar
import com.ykis.ykispam.ui.navigation.NavigationType
import com.ykis.ykispam.ui.theme.YkisPAMTheme
import com.ykis.ykispam.ui.theme.extendedColor
import java.text.DecimalFormat

@Composable
fun ServiceListScreen(
    viewModel : ServiceViewModel = hiltViewModel(),
    baseUIState: BaseUIState,
    navigationType: NavigationType,
    onDrawerClick : () -> Unit
) {
    val totalDebtState by viewModel.totalDebtState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.getTotalServiceDebt(
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
                icon = Icons.Default.CorporateFare
            ),
            TotalServiceDebt(
                name = stringResource( R.string.vodokanal),
                color = MaterialTheme.colorScheme.extendedColor.sectorColor1.color,
                debt = totalDebtState.totalDebt.dolg1!!.toFloat(),
                icon = Icons.Default.Water
            ),
            TotalServiceDebt(
                name = stringResource(id =  R.string.ytke),
                color = MaterialTheme.colorScheme.extendedColor.sectorColor2.color,
                debt = totalDebtState.totalDebt.dolg2!!.toFloat(),
                icon = Icons.Default.HotTub
            ),
            TotalServiceDebt(
                name = stringResource(id =R.string.yzhtrans),
                color = MaterialTheme.colorScheme.extendedColor.sectorColor3.color,
                debt = totalDebtState.totalDebt.dolg3!!.toFloat(),
                icon = Icons.Default.Commute
            ),
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        DefaultAppBar(
            title = stringResource(id = R.string.accrued),
            onBackClick = { /*TODO*/ },
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
                exit = fadeOut(tween(delayMillis = 300)),
                enter = fadeIn(tween(delayMillis = 300))
            ) {
                CircularProgressIndicator()
            }
            androidx.compose.animation.AnimatedVisibility(
                visible = !totalDebtState.isLoading,
                exit = fadeOut(tween(delayMillis = 300)),
                enter = fadeIn(tween(delayMillis = 300))
            ) {
                StatementBody(
                    modifier = Modifier.fillMaxSize(),
                    items = totalServiceDebtList,
                    debts = { totalServiceDebtList -> totalServiceDebtList.debt },
                    colors = { totalServiceDebtList -> totalServiceDebtList.color },
                    total = totalDebtState.totalDebt.dolg!!.toFloat(),
                    circleLabel = stringResource(R.string.summary),
                    rows = {
                        BaseRow(
                            color = it.color,
                            title = it.name,
                            debt = it.debt,
                            icon = it.icon
                        )
                    },
                )
            }
        }
    }
}
fun <E> List<E>.extractProportions(selector: (E) -> Float): List<Float> {
    val total = this.sumOf { selector(it).toDouble() }
    return this.map { (selector(it) / total).toFloat() }
}
@Composable
private fun ServiceIndicator(color: Color, modifier: Modifier = Modifier) {
    Spacer(
        modifier
            .size(6.dp, 36.dp)
            .background(color = color)
    )
}
@Composable
private fun BaseRow(
    modifier: Modifier = Modifier,
    color: Color,
    title: String,
    debt: Float,
    icon : ImageVector
) {
    val formattedDebt = formatDebt(debt)
//    val formattedDebt = String.format(Locale.GERMANY , "%.2f", debt)
    Box(modifier = modifier.clickable {

    }){
        Row(
            modifier = modifier
                .padding(start = 12.dp ,end = 8.dp)
                .height(68.dp)
            ,
            // TODO: узнать что такео clearAndSetSemantics {  }
//            .clearAndSetSemantics {
//                contentDescription =
//                    "$title account ending in ${subtitle.takeLast(4)}, current balance $dollarSign$formattedAmount"
//            },
            verticalAlignment = Alignment.CenterVertically
        ) {
            val typography = MaterialTheme.typography
            ServiceIndicator(
                color = color,
                modifier = Modifier
            )
            Icon(
                modifier = modifier.padding(horizontal = 8.dp),
                imageVector = icon,
                contentDescription = null
            )
            Column(Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = typography.labelLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

            }
            Spacer(modifier = modifier.width(4.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formattedDebt,
                    style = typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Text(
                    text = stringResource(R.string.uah),
                    style = typography.bodyLarge,
                    modifier = Modifier.align(Alignment.CenterVertically),
                )
            }
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(24.dp)
            )
        }
    }

    HorizontalDivider(color = MaterialTheme.colorScheme.background , thickness = 1.dp)
}
@Composable
fun <T> StatementBody(
    modifier: Modifier = Modifier,
    items: List<T>,
    colors: (T) -> Color,
    debts: (T) -> Float,
    total: Float,
    circleLabel: String,
    rows: @Composable (T) -> Unit
) {
    BoxWithConstraints {
        val height = maxHeight
        val modifierBox1 : Modifier = if (height > 600.dp)
            Modifier.height(height - 272.dp)
        else
            Modifier.height(300.dp)

        Column(
            modifier = modifier.verticalScroll(rememberScrollState()),
        ) {
            Box(
                modifierBox1
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                val accountsProportion = items.extractProportions { debts(it) }
                val circleColors = items.map { colors(it) }
                AnimatedCircle(
                    accountsProportion,
                    circleColors,
                    Modifier
                        .align(Alignment.Center)
                        .fillMaxSize(1f)
                )
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Text(
                        text = circleLabel,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = total.toString() + stringResource(id = R.string.uah),
                        style = MaterialTheme.typography.headlineLarge  ,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
//            Spacer(Modifier.height(10.dp))
            Card {
                Column{
                    items.forEach { item ->
                        rows(item)
                    }
                }
            }
        }
    }
}
fun formatDebt(debt: Float): String {
    return DebtDecimalFormat.format(debt)
}

private val DebtDecimalFormat = DecimalFormat("#,###.00")
@Preview(showBackground = true)
@Composable
private fun PreviewRow() {
    YkisPAMTheme {
        Card {
            Column(modifier = Modifier.padding(12.dp)) {
                BaseRow(
                    color = Color.Blue,
                    title = stringResource(id = R.string.yzhtrans),
                    debt = 564.00f,
                    icon = Icons.Default.GasMeter
                )
                BaseRow(
                    color = Color.Blue,
                    title = stringResource(id = R.string.yzhtrans),
                    debt = 564.00f,
                    icon = Icons.Default.GasMeter
                )
                BaseRow(
                    color = Color.Blue,
                    title = stringResource(id = R.string.yzhtrans),
                    debt = 564.00f,
                    icon = Icons.Default.GasMeter
                )
            }
        }

    }
}