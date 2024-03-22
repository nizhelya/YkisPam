package com.ykis.ykispam.ui.screens.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykis.ykispam.R
import com.ykis.ykispam.core.composable.ActionToolbar
import com.ykis.ykispam.ui.components.appbars.DefaultAppBar
import com.ykis.ykispam.ui.navigation.ContentDetail
import com.ykis.ykispam.ui.navigation.NavigationType

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    popUpScreen: () -> Unit,
    navigationType: NavigationType,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DefaultAppBar(
            title = stringResource(id = R.string.settings),
            navigationType = navigationType,
            canNavigateBack = true,
            onBackClick = { viewModel.onConfirmClick(popUpScreen) }
        )

        Card {
            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState()),
            ) {


                SettingsGroup(name = R.string.settings_loadin_from_server) {

                    SettingsSwitch(
                        name = R.string.loading_from_wifi,
                        icon = R.drawable.ic_loading_wifi,
                        iconDesc = R.string.loading_from_wifi,
                        // value is collected from StateFlow - updates the UI on change
                        state = viewModel.isSwitchOn.collectAsState()
                    ) {
                        // call ViewModel to toggle the value
                        viewModel.toggleSwitch()
                    }
                    SettingsSwitch(
                        name = R.string.loading_from_mobile,
                        icon = R.drawable.ic_loading_mobile,
                        iconDesc = R.string.loading_from_mobile,
                        // value is collected from StateFlow - updates the UI on change
                        state = viewModel.isSwitchOn.collectAsState()
                    ) {
                        viewModel.toggleSwitch()
                    }
                }
            }
        }

    }
}



