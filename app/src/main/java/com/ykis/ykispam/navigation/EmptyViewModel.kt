package com.ykis.ykispam.navigation


import com.ykis.ykispam.BaseViewModel
import com.ykis.ykispam.firebase.service.repo.LogService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class EmptyViewModel @Inject constructor(
    private val logService: LogService,
) : BaseViewModel(logService) {


//    fun navigateBack(popUpScreen: () -> Unit) {
//        launchCatching {
//            popUpScreen()
//        }
//    }

}