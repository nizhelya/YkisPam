package com.ykis.ykispam.domain.payment.request

import com.ykis.ykispam.R
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.data.cache.database.AppDatabase
import com.ykis.ykispam.domain.payment.PaymentEntity
import com.ykis.ykispam.domain.payment.PaymentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPaymentList  @Inject constructor(
    private val repository: PaymentRepository,
    private val database: AppDatabase
) {
    operator fun invoke(addressId:Int , year:String, uid:String): Flow<Resource<List<PaymentEntity>?>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getPaymentList(
                addressId, year, uid
            )
            val paymentList = database.paymentDao().getPaymentFromFlat(addressId)
            if(paymentList.isNotEmpty()){
                emit(Resource.Success(paymentList))
            }
            if(response.success==1){
                emit(Resource.Success(response.payments))
                database.paymentDao().insertPayment(response.payments)
            }
        }catch (e: HttpException) {
            SnackbarManager.showMessage(e.message())
            emit(Resource.Error())
        } catch (e: IOException) {
            val paymentList = database.paymentDao().getPaymentFromFlat(addressId)
            if(paymentList.isNotEmpty()){
                emit(Resource.Success(paymentList))
            }
            SnackbarManager.showMessage(R.string.error_network)
            emit(Resource.Error())
        }
    }
}