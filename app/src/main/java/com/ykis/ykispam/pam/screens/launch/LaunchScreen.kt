package com.ykis.ykispam.pam.screens.launch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ykis.ykispam.core.composable.BasicButton
import com.ykis.ykispam.core.ext.basicButton
import com.ykis.ykispam.pam.screens.appartment.ApartmentViewModel
import kotlinx.coroutines.delay
import com.ykis.ykispam.R.string as AppText

@Composable
fun LaunchScreen(
    modifier: Modifier = Modifier,
    restartApp: ( String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: ApartmentViewModel = hiltViewModel()
) {
    val isUserSignedOut = viewModel.getAuthState().collectAsStateWithLifecycle().value

    Column(
        modifier =
        modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (viewModel.showError.value) {
            Text(text = stringResource(AppText.generic_error))
            BasicButton(AppText.try_again, Modifier.basicButton()) {
                viewModel.onAppStart(isUserSignedOut, openAndPopUp,restartApp)
            }
        } else {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }
    }
    LaunchedEffect(key1 = isUserSignedOut) {
        delay(10000L)
        viewModel.onAppStart(isUserSignedOut, openAndPopUp,restartApp)
    }
}