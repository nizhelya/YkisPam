package com.ykis.ykispam.pam.domain.address

import com.squareup.moshi.Json

data class AddressEntity(
    @Json(name = "raion_id")
    val blockId: Int = 0,
    @Json(name = "raion")
    val block: String = "Unknown",
    @Json(name = "street_id")
    val streetId: Int = 0,
    val street: String = "Unknown",
    @Json(name = "house_id")
    val houseId: Int = 0,
    val house: String = "Unknown",
    @Json(name = "address_id")
    val flatId: Int = 0,
    @Json(name = "address")
    val flat: String = "Unknown"
)