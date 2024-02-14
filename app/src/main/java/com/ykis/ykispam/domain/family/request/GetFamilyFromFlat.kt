package com.ykis.ykispam.domain.family.request

import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.data.cache.database.AppDatabase
import com.ykis.ykispam.domain.family.FamilyEntity
import com.ykis.ykispam.domain.family.FamilyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetFamilyList @Inject constructor(
    private val repository: FamilyRepository,
    private val database: AppDatabase
) {
    operator fun invoke(params: FamilyParams): Flow<Resource<List<FamilyEntity>?>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getFamilyList(
                params
            )
            database.familyDao().insertFamily(response)
            emit(Resource.Success(response))
        }catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error!"))
        } catch (e: IOException) {
            val familyList = database.familyDao().getFamilyFromFlat(
                addressId = params.addressId
            )
            if (familyList.isNotEmpty()) {
                emit(Resource.Success(familyList))
                return@flow
            }
            emit(Resource.Error("Check your internet connection"))
        }
    }
}

data class BooleanInt(
    val int: Int,
    val needFetch: Boolean
)