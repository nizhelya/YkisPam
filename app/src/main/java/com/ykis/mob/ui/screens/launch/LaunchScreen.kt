package com.ykis.mob.ui.screens.launch

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ykis.mob.core.composable.BasicButton
import com.ykis.mob.core.ext.basicButton
import com.ykis.mob.ui.screens.appartment.ApartmentViewModel
import com.ykis.mob.R.string as AppText

@Composable
fun LaunchScreen(
    modifier: Modifier = Modifier,
    restartApp: ( String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: ApartmentViewModel,
) {
    val isUserSignedOut by viewModel.getAuthState().collectAsStateWithLifecycle()
    val showError = viewModel.showError.collectAsState()
    LaunchedEffect(key1 = isUserSignedOut) {
        Log.d("init_values" , "isUserSignedOut: $isUserSignedOut")
        viewModel.onAppStart(isUserSignedOut, openAndPopUp,restartApp)
    }
    Column(
        modifier =
        modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showError.value) {
            Text(text = stringResource(AppText.generic_error))
            BasicButton(AppText.try_again, Modifier.basicButton()) {
                viewModel.onAppStart(isUserSignedOut, openAndPopUp,restartApp)
            }
        }
    }
}