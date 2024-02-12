package com.ykis.ykispam.data.cache.user

import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import com.ykis.ykispam.firebase.entity.UserFirebase
import com.ykis.ykispam.firebase.service.repo.FirebaseService
import javax.inject.Inject

class UserCacheImpl @Inject constructor(private val firebaseService: FirebaseService) : UserCache {
    override fun getCurrentUser(): Either<Failure, UserFirebase> {

        return if (!firebaseService.hasUser) {
            Either.Left(Failure.NoSavedAccountsError)
        } else {
            Either.Right(
                UserFirebase(
                firebaseService.uid,
                true,
                firebaseService.providerId,
                firebaseService.displayName,
                firebaseService.email,
                firebaseService.photoUrl,
            )
            )

        }

    }

    }
