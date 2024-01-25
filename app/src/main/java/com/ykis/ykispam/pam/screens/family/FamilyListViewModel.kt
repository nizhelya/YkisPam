package com.ykis.ykispam.pam.screens.family

import com.ykis.ykispam.BaseViewModel
import com.ykis.ykispam.firebase.model.service.repo.LogService
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.pam.domain.family.FamilyEntity
import com.ykis.ykispam.pam.domain.family.request.BooleanInt
import com.ykis.ykispam.pam.domain.family.request.GetFamilyFromFlat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FamilyListViewModel @Inject constructor(
    private val getFamilyFromFlat: GetFamilyFromFlat,
    private val logService: LogService,
) : BaseViewModel(logService) {

    private val _apartment = MutableStateFlow<ApartmentEntity>(ApartmentEntity())
    val apartment: StateFlow<ApartmentEntity> get() = _apartment.asStateFlow()

    private val _family = MutableStateFlow<List<FamilyEntity>>(emptyList())
    val family : StateFlow<List<FamilyEntity>> = _family.asStateFlow()

//    var contactUiState = mutableStateOf(ApartmentEntity())
//        private set

    fun getFamily(addressId:Int , needFetch: Boolean = false) {
        getFamilyFromFlat(BooleanInt(int = addressId , needFetch = needFetch)) { it ->
            it.either(::handleFailure) {
                handleFamily(
                    it, !needFetch ,addressId
                )
            }
        }
    }
    private fun handleFamily(families: List<FamilyEntity>, fromCache: Boolean, addressId: Int) {
        _family.value = families
        updateProgress(false)

        if (fromCache) {
            updateProgress(true)
            getFamily(addressId, true)
        }
    }

    override fun onCleared() {
        super.onCleared()
        getFamilyFromFlat.unsubscribe()
    }

}