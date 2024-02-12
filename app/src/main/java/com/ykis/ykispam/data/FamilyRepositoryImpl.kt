package com.ykis.ykispam.data

import com.ykis.ykispam.data.cache.family.FamilyCache
import com.ykis.ykispam.data.cache.user.UserCache
import com.ykis.ykispam.domain.family.FamilyEntity
import com.ykis.ykispam.domain.family.FamilyRepository
import com.ykis.ykispam.domain.family.request.BooleanInt
import com.ykis.ykispam.domain.type.Either
import com.ykis.ykispam.domain.type.Failure
import com.ykis.ykispam.domain.type.flatMap
import com.ykis.ykispam.domain.type.map
import com.ykis.ykispam.domain.type.onNext
import com.ykis.ykispam.data.remote.family.FamilyRemote
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
            .map { it ->
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