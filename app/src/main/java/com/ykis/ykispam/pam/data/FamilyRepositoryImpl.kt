package com.ykis.ykispam.pam.data

import com.ykis.ykispam.pam.data.cache.family.FamilyCache
import com.ykis.ykispam.pam.data.cache.user.UserCache
import com.ykis.ykispam.pam.domain.family.FamilyEntity
import com.ykis.ykispam.pam.domain.family.FamilyRepository
import com.ykis.ykispam.pam.domain.family.request.BooleanInt
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import com.ykis.ykispam.pam.domain.type.flatMap
import com.ykis.ykispam.pam.domain.type.map
import com.ykis.ykispam.pam.domain.type.onNext
import com.ykis.ykispam.pam.data.remote.family.FamilyRemote
import javax.inject.Inject

class FamilyRepositoryImpl @Inject constructor(
    private val familyCache: FamilyCache,
    private val familyRemote: FamilyRemote,
    private val userCache: UserCache
) : FamilyRepository {
    override fun getFamilyFromFlat(params: BooleanInt): Either<Failure, List<FamilyEntity>> {
        return userCache.getCurrentUser()
            .flatMap {
                return@flatMap if (params.needFetch) {
                    familyRemote.getFamilyFromFlat(params.int, it.uid)
                } else {
                    Either.Right(
                        familyCache.getFamilyFromFlat(params.int)
                    )
                }
            }
            .map {
                it.sortedBy {
                    it.lastname
                }
            }
            .onNext {
                it.map {
                    familyCache.addFamilyByUser(listOf(it))
                }
            }
    }

}