package com.ykis.ykispam.ui.screens.service.payment.choice

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.ykis.ykispam.domain.service.request.ServiceParams
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.screens.service.list.TotalDebtState
import com.ykis.ykispam.ui.screens.service.list.TotalServiceDebt
import com.ykis.ykispam.ui.screens.service.list.assembleServiceList
import com.ykis.ykispam.ui.theme.YkisPAMTheme
import ua.com.xpay.xpaylib.XPayLibPayment
import ua.com.xpay.xpaylib.model.OrderItem


@Composable
fun PaymentChoiceStateful(
    baseUIState: BaseUIState,
    totalDebtState: TotalDebtState,
    getTotalServiceDebt:(ServiceParams) -> Unit
) {
    LaunchedEffect(key1 = baseUIState.addressId) {
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
    PaymentChoiceStateless(
        serviceList = assembleServiceList(totalDebtState = totalDebtState, baseUIState =baseUIState )
    )
}

@Composable
fun PaymentChoiceStateless(
    modifier : Modifier = Modifier,
    serviceList: List<TotalServiceDebt>
) {
    val localContext = LocalContext.current
    val orderListData : MutableList<OrderItem> = mutableListOf()
//    var osbbText by remember {
//        mutableStateOf(serviceList[0].debt.toString())
//    }
    Column{
        LazyColumn {
            items(serviceList){
                item->
                PaymentChoiceItem(
                    service = item.name,
                    debt = item.debt,
                    onCheckedTrue = {
                        service , debt ->
                        orderListData.add(
                        OrderItem(
                            orderItemTitle = service,
                            orderItemDescription = "",
                            price = debt
                        )
                    )},
                    onCheckedFalse = {
                        orderListData.remove(
                            OrderItem(
                                orderItemTitle = item.name,
                                orderItemDescription = "",
                                price =item.debt
                            )
                        )
                    },
                    onTextChange = {
                        o->
                        Log.d("payment_test" , "onTextChange" + orderListData.toString())
                        orderListData.remove(
                            OrderItem(
                                orderItemTitle = item.name,
                                orderItemDescription = "",
                                price =item.debt
                            )
                        )
                        orderListData.add(
                            OrderItem(
                                orderItemTitle = item.name,
                                orderItemDescription = "",
                                price = o.toDouble()
                            )
                        )
//                        val indexToUpdate = orderListData.indexOfFirst { it.price == o.toDouble() }
//                        if(indexToUpdate != -1){
//                            Log.d("payment_test" , "found")
//                            orderListData[indexToUpdate] = orderListData[indexToUpdate].copy(
//                                price = o.toDouble()
//                            )
//                            orderListData.add( orderListData[indexToUpdate].copy(
//                                price = o.toDouble()
//                            ))
//                            orderListData.removeAt(indexToUpdate)
//                            Log.d("payment_test" , "2onTextChange" + orderListData.toString())
//                        }else Log.d("payment_test" , "not found" + o.toString())
                    }
                )
            }
        }
        Button(
            onClick = {
                Log.d("payment_test" , orderListData.toString())
                val payment: XPayLibPayment = XPayLibPayment {
                    this.partnerToken = "72a8ddb8-9145-4a41-af1a-8c48ecaa4be1"
                    this.transactionId = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxx"
                    this.googlePayGateway = "exampleGateway"
                    this.googlePayGatewayMerchantId = "exampleMerchantId"
                    this.terminalId = "111"
                    this.payeeEmail = "test@test.com"
                    this.payeePhone = "380xxxxxxxxx"
                    this.payeeUserId = "1"
                    this.payeeName = "Name"
                    this.currency = "UAH"
                    this.amount = orderListData.sumOf { it.price }
                    this.purpose = "purpose"
                    this.order = "example order"
                    this.site = "example site"
                    this.showOrderDetails = true
                    this.orderItemList = orderListData
                }
                val requestCode = 123
                payment.startPaymentFrom(localContext as Activity, requestCode)
            }
        ){
            Text(
                "Сплатити"
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewPaymentChoice() {
    YkisPAMTheme {
        PaymentChoiceStateless(
            serviceList = assembleServiceList(totalDebtState = TotalDebtState(), baseUIState = BaseUIState(osbb="ОСББ Хіміків 14") )
        )
    }
}