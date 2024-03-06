package com.ykis.ykispam.ui.screens.service.payment

import com.ykis.ykispam.domain.payment.PaymentEntity

data class PaymentState(
    val paymentList : List<PaymentEntity> = emptyList(),
    val isLoading:Boolean = true
)