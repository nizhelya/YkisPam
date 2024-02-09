package com.ykis.ykispam.pam.data.remote

import com.ykis.ykispam.pam.data.remote.core.BaseResponse

class GetSimpleResponse(addressId: Int = 0,success: Int, message: String) :
    BaseResponse(success, message , addressId)