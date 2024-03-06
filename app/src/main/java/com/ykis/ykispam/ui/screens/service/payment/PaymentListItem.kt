package com.ykis.ykispam.ui.screens.service.payment

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ykis.ykispam.R
import com.ykis.ykispam.domain.payment.PaymentEntity
import com.ykis.ykispam.ui.components.BaseCard
import com.ykis.ykispam.ui.components.LabelTextWithText
import com.ykis.ykispam.ui.theme.YkisPAMTheme

@Composable
fun PaymentListItem(item :PaymentEntity) {
    BaseCard {
        Row{
            LabelTextWithText(
                labelText = stringResource(id = R.string.date_colon),
                valueText = item.data
            )
            Text(
                text = item.kassa
            )
        }
        LabelTextWithText(
            labelText = stringResource(id = R.string.kvartplata_colon),
            valueText = item.kvartplata.toString()
        )
        LabelTextWithText(
            labelText = stringResource(id = R.string.rfond_colon),
            valueText = item.remont.toString()
        )
        LabelTextWithText(
            labelText = stringResource(id = R.string.vodokanal_colon),
            valueText = item.voda.toString()
        )
        LabelTextWithText(
            labelText = stringResource(id = R.string.ytke_colon),
            valueText = item.otoplenie.toString()
        )
        LabelTextWithText(
            labelText = stringResource(id = R.string.yzhtrans),
            valueText = item.tbo.toString()
        )
        LabelTextWithText(
            labelText = stringResource(id = R.string.summary),
            valueText = item.summa.toString()
        )
    }
}


@Preview
@Composable
private fun PreviewPaymentListItem() {
    YkisPAMTheme {
        PaymentListItem(
            PaymentEntity()
        )
    }
}