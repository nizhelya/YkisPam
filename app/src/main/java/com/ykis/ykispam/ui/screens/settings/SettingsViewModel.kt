package com.ykis.ykispam.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ykis.ykispam.HiltApp
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.core.snackbar.SnackbarManager
import com.ykis.ykispam.data.cache.preferences.AppSettingsRepository
import com.ykis.ykispam.domain.ClearDatabase
import com.ykis.ykispam.firebase.service.repo.ConfigurationService
import com.ykis.ykispam.firebase.service.repo.FirebaseService
import com.ykis.ykispam.firebase.service.repo.LogService
import com.ykis.ykispam.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.text.DecimalFormatSymbols
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val firebaseService: FirebaseService,
    private val configurationService: ConfigurationService,
    logService: LogService
) : BaseViewModel(logService) {

    private val _isSwitchOn: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var isSwitchOn = _isSwitchOn.asStateFlow()

    private val _textPreference: MutableStateFlow<String> = MutableStateFlow("")
    var textPreference = _textPreference.asStateFlow()

    private val _intPreference: MutableStateFlow<Int> = MutableStateFlow(0)
    var intPreference = _intPreference.asStateFlow()


    fun toggleSwitch() {
        _isSwitchOn.value = _isSwitchOn.value.not()
// здесь место для работы с постоянной памятью - переключатель
    }

    fun saveText(finalText: String) {
        _textPreference.value = finalText
        // place to store text
    }

    // просто проверяем, не пусто ли оно - но проверить можно что угодно
    fun checkTextInput(text: String) = text.isNotEmpty()
    private val separatorChar = DecimalFormatSymbols.getInstance(Locale.ENGLISH).decimalSeparator

    // фильтрация только чисел и десятичного разделителя
    fun filterNumbers(text: String): String = text.filter { it.isDigit() || it == separatorChar }

    // кто-то все еще может поставить в текстовое поле больше десятичных знаков
    // мы всегда должны пытаться преобразовать текст в число
    fun checkNumber(text: String): Boolean {
        val value = text.toDoubleOrNull() ?: return false
        return value < 0
    }

    // сохраняем номер/показываем ошибку, если что-то пойдет не так
    fun saveNumber(text: String) {
        val value = text.toDoubleOrNull()
            ?: 0 // значение по умолчанию / каким-то образом обработать ошибку - показать тост или что-то в этом роде

    }
//
//    val options = mutableStateOf<List<String>>(listOf())
//
//    val wifi by mutableStateOf(configurationService.isWiFiCheckConfig)
//    val mpbile by mutableStateOf(configurationService.isMobileCheckConfig)
//
////    fun loadTaskOptions() {
////        val hasEditOption = configurationService.isShowTaskEditButtonConfig
////        options.value = TaskActionOption.getOptions(hasEditOption)
////    }
//
//
//
//    init {
//        launchCatching { configurationService.fetchConfiguration() }
//    }
//
//
//
//
//    var uiState = mutableStateOf(SettingsUiState())
//        private set
//
//    private val email
//        get() = uiState.value.wifi
//    private val password
//        get() = uiState.value.mobile
//
//
//    fun onWiFiChange(newValue: Boolean) {
//        uiState.value = uiState.value.copy(wifi  = newValue)
//    }
//    fun onMobileChange(newValue: Boolean) {
//        uiState.value = uiState.value.copy(mobile  = newValue)
//    }
//


//    fun signUpWithEmailAndPassword() {
//        if (!email.isValidEmail()) {
//            SnackbarManager.showMessage(R.string.email_error)
//            return
//        }
//
//        if (!password.isValidPassword()) {
//            SnackbarManager.showMessage(R.string.password_error)
//            return
//        }
//
//        if (!password.passwordMatches(uiState.value.repeatPassword)) {
//            SnackbarManager.showMessage(R.string.password_match_error)
//            return
//        }
//
//        launchCatching {
//            signUpResponse = Response.Loading
//            signUpResponse = firebaseService.firebaseSignUpWithEmailAndPassword(email, password)
//        }
//    }

    //
//
    fun onConfirmClick(popUpScreen: () -> Unit) {
        launchCatching {
//            signUpResponse = Response.Loading
//            signUpResponse = firebaseService.firebaseSignUpWithEmailAndPassword(email, password)
        }
        popUpScreen()
    }
//
//    fun showVerifyEmailMessage() {
//        SnackbarManager.showMessage(R.string.verify_email_message)
//
//    }
}

@HiltViewModel
class NewSettingsViewModel @Inject constructor(
    private val dataStore: AppSettingsRepository,
    private val application: HiltApp,
    private val clearDatabase: ClearDatabase,
    private val firebaseService: FirebaseService,
) : ViewModel() {

    val displayName get() = firebaseService.displayName
    val photoUrl get() = firebaseService.photoUrl
    val email get() = firebaseService.email

    private val _theme = MutableStateFlow<String?>(null)
    val theme = _theme.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val providerId get() = firebaseService.getProvider(viewModelScope)

    fun signOut(onSuccess : () -> Unit) {
        firebaseService.signOut().onEach {
            result ->
            when (result) {
                is Resource.Success -> {
                    _loading.value = false
                    onSuccess()
                }
                is Resource.Error -> {
                    _loading.value = false
                    SnackbarManager.showMessage(result.resourceMessage)
                }
                is Resource.Loading -> {
                    _loading.value = true
                }
            }
        }.launchIn(viewModelScope)
        this.clearDatabase().launchIn(this.viewModelScope)
    }

    fun revokeAccess(onSuccess: () -> Unit) {
        if(providerId == "password"){
            firebaseService.revokeAccessEmail().onEach {
                    result ->
                when(result){
                    is Resource.Success -> {
                        _loading.value = false
                        onSuccess()
                    }
                    is Resource.Error -> {
                        _loading.value = false
                        SnackbarManager.showMessage(result.resourceMessage)
                    }
                    is Resource.Loading -> {
                        _loading.value = true
                    }
                }
            }.launchIn(viewModelScope)
        }else if(providerId == "google.com") {
            firebaseService.revokeAccess().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _loading.value = false
                        onSuccess()
                    }

                    is Resource.Error -> {
                        _loading.value = false
                        SnackbarManager.showMessage(result.resourceMessage)
                    }

                    is Resource.Loading -> {
                        _loading.value = true
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
    fun setThemeValue(value: String) {
        viewModelScope.launch {
            dataStore.putThemeStrings(key = "theme", value = value)
            getThemeValue()
        }
    }
     fun getThemeValue() {
            viewModelScope.launch {
                val theme = dataStore.getThemeStrings(key = "theme").first()
                theme?.let { value ->
                    _theme.value = value
                    application.theme.value = value
                }
            }
        }
    }
