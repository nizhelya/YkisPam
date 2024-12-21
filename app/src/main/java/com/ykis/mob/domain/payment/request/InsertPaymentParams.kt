package com.ykis.mob.domain.payment.request

data class InsertPaymentParams(
   val uid: String,
   val addressId: Int,
   val kvartplata: Double,
   val rfond: Double,
   val teplo: Double,
   val voda: Double,
   val tbo: Double,
)