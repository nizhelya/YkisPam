package com.ykis.ykispam.ui.screens.service.payment

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ykis.ykispam.R
import com.ykis.ykispam.domain.payment.PaymentEntity
import com.ykis.ykispam.ui.components.EmptyListState

@Composable
fun PaymentList(
    paymentList : List<PaymentEntity>,
    osbb:String
) {
    if (paymentList.isEmpty()) {
        EmptyListState(
            title = stringResource(id = R.string.no_payment),
            subtitle = stringResource(id = R.string.no_payment_year)
        )
    } else LazyColumn {
        items(items = paymentList) { payment ->
            PaymentListItem(
                item = payment,
                osbb = osbb
            )
        }
    }
}