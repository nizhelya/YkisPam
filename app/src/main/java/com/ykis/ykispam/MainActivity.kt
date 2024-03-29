package com.ykis.ykispam

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.ykis.ykispam.ui.YkisPamApp
import com.ykis.ykispam.ui.screens.settings.NewSettingsViewModel
import com.ykis.ykispam.ui.theme.YkisPAMTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var application: HiltApp

    private var pressBackExitJob: Job? = null
    private var backPressedOnce = false
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val settingsViewModel : NewSettingsViewModel = hiltViewModel()
            settingsViewModel.getThemeValue()
            YkisPAMTheme(
                appTheme = application.theme.value,
            ) {
                val windowSize = calculateWindowSizeClass(this)
                val displayFeatures = calculateDisplayFeatures(this)
                Surface (
                    color = MaterialTheme.colorScheme.primary,
                ){
                    YkisPamApp(
                        windowSize = windowSize,
                        displayFeatures = displayFeatures
                    )
                }
            }
        }
        onBackPressedDispatcher.addCallback(this) {
            pressBackExitJob?.cancel()

            if (backPressedOnce) {
                finish()
                return@addCallback
            }

            Toast.makeText(applicationContext,getText(R.string.exit_app) ,Toast.LENGTH_SHORT).show()

            backPressedOnce = true

            pressBackExitJob = lifecycleScope.launch {
                delay(2000)

                backPressedOnce = false
            }
        }
    }

}

