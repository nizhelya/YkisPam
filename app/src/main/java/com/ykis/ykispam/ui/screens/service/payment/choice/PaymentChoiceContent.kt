package com.ykis.ykispam.ui.screens.service.payment.choice

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.domain.payment.request.InsertPaymentParams
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
    modifier: Modifier = Modifier,
    baseUIState: BaseUIState,
    totalDebtState: TotalDebtState,
    viewModel: ServiceViewModel,
    navigateToWebView: (String) -> Unit
) {
    val loading by viewModel.insertPaymentLoading.collectAsStateWithLifecycle()
    val context = LocalContext.current
    fun intent(uri:String) = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
//    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/")) }

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
    val serviceList =
        assembleServiceList(totalDebtState = totalDebtState, baseUIState = baseUIState)
    var osbbField by rememberSaveable {
        mutableStateOf("0.00")
    }
    var waterField by rememberSaveable {
        mutableStateOf("0.00")
    }
    var heatField by rememberSaveable {
        mutableStateOf("0.00")
    }
    var tboField by rememberSaveable {
        mutableStateOf("0.00")
    }
    LazyColumn {
        items(serviceList) { item ->
            val currentField = when (item.contentDetail) {
                ContentDetail.OSBB -> osbbField
                ContentDetail.WATER_SERVICE -> waterField
                ContentDetail.WARM_SERVICE -> heatField
                else -> tboField
            }
            PaymentChoiceItem(
                service = item.name,
                debt = item.debt,
                onCheckedTrue = { service, debt ->
                    when (item.contentDetail) {
                        ContentDetail.OSBB -> osbbField = debt.toString()
                        ContentDetail.WATER_SERVICE -> waterField = debt.toString()
                        ContentDetail.WARM_SERVICE -> heatField = debt.toString()
                        else -> tboField = debt.toString()
                    }
                },
                onCheckedFalse = {
                    when (item.contentDetail) {
                        ContentDetail.OSBB -> osbbField = "0.00"
                        ContentDetail.WATER_SERVICE -> waterField = "0.00"
                        ContentDetail.WARM_SERVICE -> heatField = "0.00"
                        else -> tboField = "0.00"
                    }
                },
                userInput = currentField,
                onTextChange = { newText ->
                    when (item.contentDetail) {
                        ContentDetail.OSBB -> osbbField = newText
                        ContentDetail.WATER_SERVICE -> waterField = newText
                        ContentDetail.WARM_SERVICE -> heatField = newText
                        else -> tboField = newText
                    }
                }
            )
        }
        item {
            Button(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = {
                    val orderList: MutableList<OrderItem> = mutableListOf()
                    for (item in serviceList) {
                        Log.d("payment_test", "cycle started " + serviceList.size.toString())
                        when (item.contentDetail) {
                            ContentDetail.OSBB -> {
                                addToOrderList(osbbField, item.name, orderList)
                            }

                            ContentDetail.WATER_SERVICE -> {
                                addToOrderList(waterField, item.name, orderList)
                            }

                            ContentDetail.WARM_SERVICE -> {
                                addToOrderList(heatField, item.name, orderList)
                            }

                            else -> {
                                addToOrderList(tboField, item.name, orderList)
                            }
                        }
                    }

                    viewModel.insertPayment(
                        params = InsertPaymentParams(
                            uid = baseUIState.uid.toString(),
                            addressId = baseUIState.addressId,
                            kvartplata = osbbField.toDoubleOrNull() ?: 0.0,
                            rfond = 0.0,
                            teplo = heatField.toDoubleOrNull() ?: 0.0,
                            voda = waterField.toDoubleOrNull() ?: 0.0,
                            tbo = tboField.toDoubleOrNull() ?: 0.0
                        ),
                        onSuccess = {
                            //if open in WebView
//                            navigateToWebView(it)
                            //if open in Browser
                            context.startActivity(intent(it))
                        }
                    )
                    if (orderList.isNotEmpty()) {
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
//                        payment.startPaymentFrom(localContext as Activity, requestCode)
                    } else SnackbarManager.showMessage("Щоб сплатити оберіть послугу")

                }
            ) {
                AnimatedVisibility(visible = loading) {
                    CircularProgressIndicator(
                        modifier = modifier.size(
                            ButtonDefaults.IconSize
                        ),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 3.dp
                    )
                }
                AnimatedVisibility(visible = !loading) {
                    Text(
                        "Перейти до сплати"
                    )
                }
            }
        }
    }
}

fun addToOrderList(
    textField: String,
    name: String,
    orderList: MutableList<OrderItem>
) {
    val userPrice = textField.toDoubleOrNull()
    if (userPrice != null && userPrice > 0.0) {
        orderList.add(
            OrderItem(
                orderItemTitle = name,
                orderItemDescription = "",
                price = userPrice
            )
        )
    }
}

@Composable
fun WebView(uri:String){

    // Declare a string that contains a url

    // Adding a WebView inside AndroidView
    // with layout as full screen
    val formattedUri = uri.replace("*" , "/")
    Log.d("wv_test" , "formated $formattedUri")
    AndroidView(factory = {
        android.webkit.WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }, update = {
        it.loadUrl(formattedUri)
    })
}