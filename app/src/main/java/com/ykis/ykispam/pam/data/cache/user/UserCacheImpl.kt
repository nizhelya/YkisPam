package com.ykis.ykispam.pam.data.cache.user

import com.ykis.ykispam.firebase.entity.UserFirebase
import com.ykis.ykispam.firebase.service.repo.FirebaseService
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
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
