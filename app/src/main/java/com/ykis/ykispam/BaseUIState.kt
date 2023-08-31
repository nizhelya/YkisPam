package com.ykis.ykispam

import com.ykis.ykispam.navigation.ADDRESS_ID_ARG
import com.ykis.ykispam.navigation.EXIT_SCREEN
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity

data class BaseUIState(
    val uid: String? = null,
    val displayName: String? = null,
    val email: String? = null,
    val photoUrl: String? = null,
    val apartments: List<ApartmentEntity> = emptyList(),
    val selectedApartment: Int = 0,
    val isEmailVerified: Boolean = false,
    val isUserSignedOut: Boolean = false,
    val isDetailOnlyOpen: Boolean = false,
    val selectedDestination: String= "$EXIT_SCREEN$ADDRESS_ID_ARG",
    val secretCode: String = "",
    val loading: Boolean = false,
    val error: String? = null
)