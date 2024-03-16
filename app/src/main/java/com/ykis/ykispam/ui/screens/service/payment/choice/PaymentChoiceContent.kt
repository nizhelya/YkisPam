package com.ykis.ykispam.ui.screens.service.payment.choice

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.domain.service.request.ServiceParams
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.navigation.ContentDetail
import com.ykis.ykispam.ui.screens.service.ServiceViewModel
import com.ykis.ykispam.ui.screens.service.list.TotalDebtState
import com.ykis.ykispam.ui.screens.service.list.assembleServiceList
import ua.com.xpay.xpaylib.XPayLibPayment
import ua.com.xpay.xpaylib.model.OrderItem


@Composable
fun PaymentChoiceStateful(
    modifier : Modifier = Modifier,
    baseUIState: BaseUIState,
    totalDebtState: TotalDebtState,
    viewModel: ServiceViewModel
) {
    LaunchedEffect(key1 = baseUIState.addressId) {
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
    val serviceList = assembleServiceList(totalDebtState = totalDebtState, baseUIState =baseUIState )
    var textField1 by rememberSaveable {
        mutableStateOf("0.00")
    }
    var textField2 by rememberSaveable {
        mutableStateOf("0.00")
    }
    var textField3 by rememberSaveable {
        mutableStateOf("0.00")
    }
    var textField4 by rememberSaveable {
        mutableStateOf("0.00")
    }
    val localContext = LocalContext.current

    LazyColumn {
        items(serviceList) { item ->
            val currentField = when(item.contentDetail){
                ContentDetail.OSBB -> textField1
                ContentDetail.WATER_SERVICE ->textField2
                ContentDetail.WARM_SERVICE ->textField3
                else ->textField4
            }
            PaymentChoiceItem(
                service = item.name,
                debt = item.debt,
                onCheckedTrue = { service, debt ->
                    when(item.contentDetail){
                        ContentDetail.OSBB ->         textField1  = debt.toString()
                        ContentDetail.WATER_SERVICE ->textField2 =  debt.toString()
                        ContentDetail.WARM_SERVICE -> textField3 =  debt.toString()
                        else ->                       textField4 =  debt.toString()
                    }
                },
                onCheckedFalse = {
                    when(item.contentDetail){
                        ContentDetail.OSBB ->         textField1  = "0.00"
                        ContentDetail.WATER_SERVICE ->textField2 =  "0.00"
                        ContentDetail.WARM_SERVICE -> textField3 =  "0.00"
                        else ->                       textField4 =  "0.00"
                    }
                },
                userInput = currentField,
                onTextChange = { newText->
                    when(item.contentDetail){
                        ContentDetail.OSBB ->         textField1  = newText
                        ContentDetail.WATER_SERVICE ->textField2 = newText
                        ContentDetail.WARM_SERVICE -> textField3 = newText
                        else ->                       textField4 = newText
                    }
                }
            )
        }
        item {
            Button(
                modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
                onClick = {
                    val orderList :MutableList<OrderItem> = mutableListOf()
                    for(item in serviceList){
                        Log.d("payment_test" ,"cycle started " + serviceList.size.toString())
                        when(item.contentDetail){
                            ContentDetail.OSBB ->         {addToOrderList(textField1 , item.name , orderList)}
                            ContentDetail.WATER_SERVICE ->{addToOrderList(textField2 , item.name , orderList)}
                            ContentDetail.WARM_SERVICE -> {addToOrderList(textField3 , item.name , orderList)}
                            else ->                       {addToOrderList(textField4 , item.name , orderList)}
                        }
                    }
                    if(orderList.isNotEmpty()){
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
                            this.amount = orderList.sumOf { it.price }
                            this.purpose = "purpose"
                            this.order = "example order"
                            this.site = "example site"
                            this.showOrderDetails = true
                            this.orderItemList = orderList
                        }
                        val requestCode = 123
                        payment.startPaymentFrom(localContext as Activity, requestCode)
                    }else SnackbarManager.showMessage("Щоб сплатити оберіть послугу")

                }
            ) {
                Text(
                    "Перейти до сплати"
                )
            }
        }
    }
}

fun addToOrderList(
    textField : String,
    name : String,
    orderList : MutableList<OrderItem>
){
    val userPrice = textField.toDoubleOrNull()
    if(userPrice != null && userPrice > 0.0){
        orderList.add(OrderItem(orderItemTitle = name , orderItemDescription = "" , price = userPrice))
    }
}