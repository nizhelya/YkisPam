package com.ykis.ykispam.ui

import com.ykis.ykispam.ui.navigation.ADDRESS_ID_ARG
import com.ykis.ykispam.ui.navigation.APARTMENT_SCREEN
import com.ykis.ykispam.ui.navigation.ContentDetail
import com.ykis.ykispam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.domain.payment.PaymentItemEntity

data class BaseUIState(
    val uid: String? = null,
    val displayName: String? = null,

    val email: String? = null,
    val photoUrl: String? = null,
    var apartment: ApartmentEntity = ApartmentEntity(),
    val selectedContentDetail: ContentDetail? = null,
    val apartments: List<ApartmentEntity> = emptyList(),
    val paymentItems: List<PaymentItemEntity> = emptyList(),
    val osmdId: Int = 0,
    val houseId: Int = 0,
    val addressId: Int = 0,
    val address: String? = null,
    val osbb: String? = null,
    val addressNumber: String? = null,
    val isDetailOnlyOpen: Boolean = false,
    val selectedDestination: String = "$APARTMENT_SCREEN$ADDRESS_ID_ARG",
    val isLoading: Boolean = false,
    val error: String? = null
)