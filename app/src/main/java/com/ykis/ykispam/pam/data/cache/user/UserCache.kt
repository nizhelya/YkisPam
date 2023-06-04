package com.ykis.ykispam.pam.data.cache.user

import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import com.ykis.ykispam.pam.domain.user.UserEntity

interface UserCache {
    fun getCurrentUser(): Either<Failure, UserEntity>
}