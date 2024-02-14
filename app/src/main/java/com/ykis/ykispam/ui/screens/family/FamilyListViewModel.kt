package com.ykis.ykispam.ui.screens.family

import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.ui.BaseViewModel
import com.ykis.ykispam.firebase.service.repo.LogService
import com.ykis.ykispam.domain.family.FamilyEntity
import com.ykis.ykispam.domain.family.request.FamilyParams
import com.ykis.ykispam.domain.family.request.GetFamilyList
import com.ykis.ykispam.ui.screens.service.ServiceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FamilyListViewModel @Inject constructor(
    private val getFamilyListUseCase: GetFamilyList,
    private val logService: LogService,
) : BaseViewModel(logService) {

    private val _state = MutableStateFlow<FamilyState>(FamilyState())
    val state : StateFlow<FamilyState> = _state.asStateFlow()

    fun getFamilyList(uid:String, addressId:Int) {
        this.getFamilyListUseCase(FamilyParams(uid , addressId)).onEach {
                result->
            when(result){
                is Resource.Success -> {
                    this._state.value = FamilyState(familyList = result.data ?: emptyList() , isLoading = false)
                }
                is Resource.Error -> {
                    this._state.value = FamilyState(error = result.message ?: "Unexpected error!")
                }
                is Resource.Loading -> {
                    this._state.value = FamilyState(isLoading = true)
                }
            }
        }.launchIn(this.viewModelScope)
    }
}