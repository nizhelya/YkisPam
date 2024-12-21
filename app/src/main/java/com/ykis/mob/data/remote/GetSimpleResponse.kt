package com.ykis.mob.data.remote

import com.ykis.mob.data.remote.core.BaseResponse

class GetSimpleResponse(addressId: Int = 0,success: Int, message: String) :
    BaseResponse(success, message , addressId)