package com.ykis.ykispam.domain.service.request

import android.util.Log
import com.google.android.play.integrity.internal.i
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.data.cache.service.ServiceCache
import com.ykis.ykispam.data.cache.user.UserCache
import com.ykis.ykispam.data.remote.service.ServiceRemote
import com.ykis.ykispam.domain.interactor.UseCase
import com.ykis.ykispam.domain.service.ServiceEntity
import com.ykis.ykispam.domain.service.ServiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

//class GetFlatService @Inject constructor(
//    private val serviceRepository: ServiceRepositoryImpl
//) : UseCase<List<ServiceEntity>, ServiceParams>() {
//
//    override suspend fun run(params: ServiceParams) = serviceRepository.getFlatService(params)
//}


class ImproveGetFlatServices @Inject constructor(
    private val repository: ServiceRepository,
    private val serviceCache: ServiceCache,
    private val serviceRemote: ServiceRemote,
    private val userCache: UserCache
    ){
    operator fun invoke () : Flow<Resource<List<ServiceEntity>?>> = flow{
        try{
            emit(Resource.Loading())
//            val response = serviceRemote.getFlatService(params)
            val response = repository.newGetFlatService(ServiceParams(
                uid = "aaa",
                addressId = 8456,
                houseId = 14,
                year="2024",
                service=2,
                total = 1,
                needFetch = true
            ))
            val serviceList = response
//            val i = serviceRemote.getFlatServices(
//                uid = params.uid,
//                addressId = params.addressId,
//                houseId = params.houseId,
//                year = params.year,
//                service = params.service,
//                total = params.total
//            )
            emit(Resource.Success(response))
        }catch (e:Exception){
            Log.d("domain_test",e.toString())
        }
    }
}
//data class ServiceParams(
//    val uid:String,
//    val addressId:Int,
//    val houseId:Int ,
//    val service:Byte,
//    val total:Byte,
//    val year : String ,
//    var needFetch:Boolean
//)