package com.ykis.ykispam.domain.firebase

import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.data.cache.database.AppDatabase
import com.ykis.ykispam.data.remote.GetSimpleResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SendNotificationToUser  @Inject constructor(
    private val repository: FirebaseRepository,
)  {
    operator fun invoke(params: SendNotificationArguments): Flow<Resource<GetSimpleResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.sendNotificationToUser(params)
            emit(Resource.Success(response))
        }catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Unexpected error!"))
        }
    }
}