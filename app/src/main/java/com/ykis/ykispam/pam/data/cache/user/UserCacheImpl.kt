package com.ykis.ykispam.pam.data.cache.user

import com.ykis.ykispam.firebase.model.entity.UserFirebase
import com.ykis.ykispam.firebase.model.service.repo.FirebaseService
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import javax.inject.Inject

class UserCacheImpl @Inject constructor(private val firebaseService: FirebaseService) : UserCache {
    override fun getCurrentUser(): Either<Failure, UserFirebase> {

        if (firebaseService.uid.isEmpty()) {
            return Either.Left(Failure.NoSavedAccountsError)
        } else {
            return Either.Right(UserFirebase(
                firebaseService.uid,
                true,
                firebaseService.providerId,
                firebaseService.displayName,
                firebaseService.email,
                firebaseService.photoUrl,
            ))

        }

    }

    }
