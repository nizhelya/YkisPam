package com.ykis.ykispam.data.remote.address

import com.ykis.ykispam.data.remote.core.BaseResponse
import com.ykis.ykispam.domain.address.AddressEntity

class GetAddressResponse(
    success: Int,
    message: String,
    val address: List<AddressEntity>
) : BaseResponse(success, message)