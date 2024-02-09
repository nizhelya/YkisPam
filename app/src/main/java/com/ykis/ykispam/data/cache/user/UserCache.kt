package com.ykis.ykispam.pam.data.cache.user

import com.ykis.ykispam.firebase.entity.UserFirebase
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure

interface UserCache {
    fun getCurrentUser(): Either<Failure, UserFirebase>
}