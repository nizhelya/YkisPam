package com.ykis.ykispam.ui.screens.service.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Commute
import androidx.compose.material.icons.filled.CorporateFare
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.HotTub
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material.icons.filled.Water
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.R
import com.ykis.ykispam.core.ext.formatMoney
import com.ykis.ykispam.domain.payment.PaymentEntity
import com.ykis.ykispam.ui.components.BaseCard
import com.ykis.ykispam.ui.components.LabelTextWithText
import com.ykis.ykispam.ui.theme.YkisPAMTheme

@Composable
fun PaymentListItem(
    modifier: Modifier = Modifier,
    item :PaymentEntity,
    osbb : String
) {
    BaseCard {
        Row{
            ColumnLabelTextWithTextAndIcon(
                modifier = modifier.weight(1f),
                labelText = stringResource(id = R.string.date_colon),
                valueText = item.data,
                imageVector = Icons.Default.DateRange
            )
            ColumnLabelTextWithTextAndIcon(
                labelText = stringResource(id = R.string.point_of_sale),
                valueText = item.kassa,
                imageVector = Icons.Default.PointOfSale
            )
        }
        HorizontalDivider()
        if(item.remont !=0.0 || item.kvartplata!=0.0){
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            )
            {
                Icon(
                    imageVector = Icons.Default.CorporateFare,
                    contentDescription = null
                )
                Text(
                    text = osbb,
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Row(
                modifier = modifier.height(
                    IntrinsicSize.Max
                )
            ){
                VerticalDivider(
                    thickness = 2.dp,
                )
                Column {
                    if(item.kvartplata != 0.0){
                        LabelTextWithText(
                            labelText = stringResource(id = R.string.kvartplata_colon),
                            valueText = item.kvartplata.formatMoney(),
                            modifier = modifier.padding(start = 8.dp)
                        )
                    }
                    if(item.remont != 0.0){
                        LabelTextWithText(
                            labelText = stringResource(id = R.string.rfond_colon),
                            valueText = item.remont.formatMoney() ,
                            modifier = modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
            HorizontalDivider()
        }
        if(item.voda != 0.0){
            ColumnLabelTextWithTextAndIcon(
                labelText = stringResource(id = R.string.vodokanal_colon),
                valueText = item.voda.formatMoney(),
                imageVector = Icons.Default.Water
            )
            HorizontalDivider()
        }
        if(item.otoplenie != 0.0){
            ColumnLabelTextWithTextAndIcon(
                labelText = stringResource(id = R.string.ytke_colon),
                valueText = item.otoplenie.formatMoney(),
                imageVector = Icons.Default.HotTub
            )
            HorizontalDivider()
        }
        if(item.tbo != 0.0){
            ColumnLabelTextWithTextAndIcon(
                labelText = stringResource(id = R.string.yzhtrans_colon),
                valueText = item.tbo.formatMoney(),
                imageVector = Icons.Default.Commute
            )
            HorizontalDivider()
        }
        LabelTextWithText(
            labelText = stringResource(id = R.string.summary),
            valueText = item.summa.formatMoney() + stringResource(id = R.string.uah)
        )
    }
}

@Composable
fun ColumnLabelTextWithTextAndIcon(
    modifier: Modifier = Modifier,
    labelText: String = "",
    valueText:String ="",
    imageVector: ImageVector? = null
) { 
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ){
            if(imageVector!=null){
                Icon(
                    imageVector = imageVector ,
                    contentDescription = null
                )
            }
            Text(
                text = labelText,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Text(
            modifier = Modifier,
            text = valueText
        )
    }
}

@Preview
@Composable
private fun PreviewPaymentListItem() {
    YkisPAMTheme {
        PaymentListItem(
            item = PaymentEntity(
                voda = 146.35,
                otoplenie = 124.88,
                tbo = 64.00,
                remont = 46.2,
                kvartplata = 322.60
            ),
            osbb = "Кондомінімум 16"
        )
    }
}