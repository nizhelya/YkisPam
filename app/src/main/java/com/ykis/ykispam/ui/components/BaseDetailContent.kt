package com.ykis.ykispam.ui.components

import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DetailPanel(
    modifier: Modifier = Modifier,
//    contentDetail: ContentDetail,
//    onBackPressed: () -> Unit,
    showDetail: Boolean,
//    onActionButtonClick: () -> Unit = {},
//    barTitle : String? =  null ,
    detailContent: @Composable () -> Unit
) {
    androidx.compose.animation.AnimatedVisibility(
        visible = showDetail,
        enter = slideInVertically(
            tween(
                durationMillis = 550,
                easing = EaseOutCubic
            ),
            initialOffsetY = {
                it
            },
        ) + fadeIn(
            tween(
                durationMillis = 400
            )
        ),
        exit = slideOutVertically(
            targetOffsetY = { it }
        )
                + fadeOut()
    ) {
        Card(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(
                containerColor =
                MaterialTheme.colorScheme.surfaceContainer
            )
        ) {
            Column {
//                DefaultAppBar(
//                    onBackClick = onBackPressed,
//                    canNavigateBack = true,
//                    title =
//                    if(barTitle!=null){
//                        barTitle
//                    }else when (contentDetail) {
//                        ContentDetail.BTI -> stringResource(id = R.string.bti)
//                        ContentDetail.FAMILY -> stringResource(id = R.string.list_family)
//                        ContentDetail.OSBB -> stringResource(id = R.string.vneski)
//                        ContentDetail.WATER_SERVICE -> stringResource(id = R.string.vodokanal)
//                        ContentDetail.WARM_SERVICE -> stringResource(id = R.string.ytke)
//                        ContentDetail.GARBAGE_SERVICE -> stringResource(id = R.string.yzhtrans)
//                        ContentDetail.PAYMENT_LIST -> stringResource(id = R.string.payment_list)
//                        ContentDetail.WATER_METER -> stringResource(id = R.string.water_meter_label)
//                        ContentDetail.HEAT_METER -> stringResource(id = R.string.heat_meter_label)
//                        ContentDetail.WATER_READINGS -> stringResource(id = R.string.reading_history)
//                        ContentDetail.HEAT_READINGS -> stringResource(id = R.string.reading_history)
//                    }
//                ) {
//                    onActionButtonClick()
//                }
                detailContent()
            }
        }
    }
}