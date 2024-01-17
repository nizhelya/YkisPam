package com.ykis.ykispam

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.adaptive.calculateDisplayFeatures
import com.ykis.ykispam.pam.screens.appartment.ApartmentViewModel
import com.ykis.ykispam.theme.YkisPAMTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: ApartmentViewModel by viewModels()

    private var pressBackExitJob: Job? = null
    private var backPressedOnce = false

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            YkisPAMTheme {
                val windowSize = calculateWindowSizeClass(this)
                val displayFeatures = calculateDisplayFeatures(this)
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                YkisPamApp(
                    windowSize = windowSize,
                    displayFeatures = displayFeatures,
                    baseUIState = uiState,
                    getApartments = {
                        viewModel.initialize()
                    },
                    closeDetailScreen = {
                        viewModel.closeDetailScreen()
                    },
                    setApartment = { addressId ->
                        viewModel.setApartment(addressId)
                    },
                    navigateToDetail = { contentDetail, pane ->
                        viewModel.setSelectedDetail(contentDetail, pane)
                    },
                )
            }
        }
        onBackPressedDispatcher.addCallback(this) {
            pressBackExitJob?.cancel()

            if (backPressedOnce) {
                finish()
                return@addCallback
            }

            Toast.makeText(applicationContext, "Please press back again to exit." ,Toast.LENGTH_SHORT).show()

            backPressedOnce = true

            pressBackExitJob = lifecycleScope.launch {
                delay(1000)

                backPressedOnce = false
            }
        }
    }

}

