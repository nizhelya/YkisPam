package com.ykis.ykispam.domain.payment


data class PaymentItemEntity(
    val year: Int = 0,
    val paymentsList: List<PaymentEntity>,
    var isExpandable: Boolean = false
)