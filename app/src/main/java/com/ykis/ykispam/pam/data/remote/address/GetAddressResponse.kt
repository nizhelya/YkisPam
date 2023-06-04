package com.ykis.ykispam.pam.data.remote.address

import com.ykis.ykispam.pam.data.remote.core.BaseResponse
import com.ykis.ykispam.pam.domain.address.AddressEntity

class GetAddressResponse(
    success: Int,
    message: String,
    val address: List<AddressEntity>
) : BaseResponse(success, message)