package com.ykis.ykispam.pam.screens.bti

import com.ykis.ykispam.navigation.ADDRESS_ID_ARG
import com.ykis.ykispam.navigation.APARTMENT_SCREEN
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.pam.domain.payment.PaymentItemEntity

data class ContactUIState (
    val addressId: Int = 0,
    val email: String = "",
    val phone: String = "",
)