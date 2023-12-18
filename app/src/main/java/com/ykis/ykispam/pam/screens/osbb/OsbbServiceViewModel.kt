package com.ykis.ykispam.pam.screens.osbb

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ykis.ykispam.BaseViewModel
import com.ykis.ykispam.firebase.model.service.repo.LogService
import com.ykis.ykispam.pam.data.cache.apartment.ApartmentCacheImpl
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.pam.domain.family.FamilyEntity
import com.ykis.ykispam.pam.domain.service.ServiceEntity
import com.ykis.ykispam.pam.domain.service.request.ServiceParams
import com.ykis.ykispam.pam.domain.service.request.getFlatService
import com.ykis.ykispam.pam.domain.service.request.getTotalDebtService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OsbbServiceViewModel @Inject constructor(
    private val getFlatServiceUseCase: getFlatService,
    private val getTotalDebtServiceUseCase : getTotalDebtService,
    private val logService: LogService,
) : BaseViewModel(logService) {
    private val _apartment = MutableLiveData<ApartmentEntity>()

    var contactUiState = mutableStateOf(ApartmentEntity())
        private set

    private val _servicesFlat = MutableLiveData<List<ServiceEntity>>()
    val servicesFlat : LiveData<List<ServiceEntity>> get() = _servicesFlat


    private val _serviceDetail = MutableLiveData<List<ServiceEntity>>()
    val serviceDetail : LiveData<List<ServiceEntity>> get() = _serviceDetail

    private val _totalDebt = MutableLiveData<ServiceEntity?>()
    val totalDebt : LiveData<ServiceEntity?> get() = _totalDebt

    private val _totalPay = MutableLiveData<Double>(0.0)
    val totalPay : LiveData<Double> get() = _totalPay

    var currentService :Byte = 0
    var currentServiceTitle :String = ""


    fun getFlatService(addressId: Int , houseId: Int , service:Byte ,total:Byte ,qty:Byte , needFetch:Boolean = false) {
        getFlatServiceUseCase(
            ServiceParams(
            addressId = addressId ,
            houseId = houseId ,
            service = service,
            total = total,
            qty = qty,
            needFetch = needFetch)
        ) { it ->
            it.either(::handleFailure) {
                handleService(
                    it, addressId , houseId , service , total , qty , !needFetch
                )
            }
        }
    }
    private fun handleService(services: List<ServiceEntity>,
                              addressId: Int,
                              houseId: Int,
                              service:Byte,
                              total:Byte,
                              qty:Byte,
                              fromCache: Boolean,
    ) {
        _servicesFlat.value = services
        updateProgress(false)

        if (fromCache) {
            updateProgress(true)
            getFlatService(addressId, houseId , service,total, qty, true)
        }
    }
    fun getDetailService(addressId: Int , houseId: Int , service:Byte ,total:Byte ,qty:Byte , needFetch:Boolean = false) {
        getFlatServiceUseCase(
            ServiceParams(
            addressId = addressId ,
            houseId = houseId ,
            service = service,
            total = total,
            qty = qty,
            needFetch = needFetch)
        ) { it ->
            it.either(::handleFailure) {
                handleDetailService(
                    it,addressId , houseId , service , total , qty , !needFetch
                )
            }
        }
    }
    private fun handleDetailService(services: List<ServiceEntity>,
                                    addressId: Int,
                                    houseId: Int,
                                    service:Byte,
                                    total:Byte,
                                    qty:Byte,
                                    fromCache: Boolean,
    ) {
        _serviceDetail.value = services
        updateProgress(false)

        if (fromCache) {
            updateProgress(true)
            getDetailService(addressId, houseId , service,total, qty, true)
        }
    }
    fun getTotalService(addressId: Int){
        getTotalDebtServiceUseCase(addressId) { it ->
            it.either(::handleFailure) {
                handle(
                    it,_totalDebt
                )
            }
        }
    }
    fun plusTotalPay(double: Double){
        _totalPay.value = _totalPay.value!!.plus(double)
    }
    fun minusTotalPay(double: Double){
        _totalPay.value = _totalPay.value!!.minus(double)
    }
    private fun handle(address: ServiceEntity?, liveData : MutableLiveData<ServiceEntity?> ){
        liveData.value = address
    }
    fun clearTotal(){
        _totalDebt.value = null
    }
    fun clearService(){
        _serviceDetail.value = listOf()
    }



}