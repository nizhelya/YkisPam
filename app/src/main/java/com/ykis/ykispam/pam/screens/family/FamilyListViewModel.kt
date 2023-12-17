package com.ykis.ykispam.pam.screens.family

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.BaseViewModel
import com.ykis.ykispam.firebase.model.service.repo.LogService
import com.ykis.ykispam.pam.data.cache.apartment.ApartmentCacheImpl
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.pam.domain.family.FamilyEntity
import com.ykis.ykispam.pam.domain.family.request.BooleanInt
import com.ykis.ykispam.pam.domain.family.request.GetFamilyFromFlat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FamilyListViewModel @Inject constructor(
    private val getFamilyFromFlat: GetFamilyFromFlat,
    private val apartmentCacheImpl: ApartmentCacheImpl,

    private val logService: LogService,
) : BaseViewModel(logService) {
    private val _apartment = MutableLiveData<ApartmentEntity>()
    val apartment: LiveData<ApartmentEntity> get() = _apartment

    private val _family = MutableLiveData<List<FamilyEntity>>()
    val family : LiveData<List<FamilyEntity>> = _family
    var contactUiState = mutableStateOf(ApartmentEntity())
        private set

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
//
//    fun navigateBack(popUpScreen: () -> Unit) {
//        launchCatching {
//            popUpScreen()
//        }
//    }

}