package com.ykis.ykispam.pam.data.cache.user

import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import com.ykis.ykispam.pam.domain.user.UserEntity
import javax.inject.Inject

class UserCacheImpl @Inject constructor() : UserCache {
    override fun getCurrentUser(): Either<Failure, UserEntity> {
        return Either.Right(UserEntity(7259, "Petya", "example@gmail.com", "url", "543ger245"))
    }
}