package com.ykis.ykispam.data.cache.user

import com.ykis.ykispam.firebase.entity.UserFirebase
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure

interface UserCache {
    fun getCurrentUser(): Either<Failure, UserFirebase>
}