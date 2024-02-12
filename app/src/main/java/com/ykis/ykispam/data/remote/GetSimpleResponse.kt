package com.ykis.ykispam.data.remote

import com.ykis.ykispam.data.remote.core.BaseResponse

class GetSimpleResponse(addressId: Int = 0,success: Int, message: String) :
    BaseResponse(success, message , addressId)