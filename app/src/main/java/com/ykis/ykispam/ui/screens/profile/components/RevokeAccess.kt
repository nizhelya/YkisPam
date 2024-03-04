package com.ykis.ykispam.ui.screens.profile.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.ykis.ykispam.core.ProgressBar
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.ui.screens.profile.ProfileViewModel

@Composable
fun RevokeAccess(
    viewModel: ProfileViewModel,
    navigateToAuthScreen: (accessRevoked: Boolean) -> Unit,
    showSnackBar: () -> Unit
) {
    when(val revokeAccessResponse = viewModel.revokeAccessResponse.collectAsState().value) {
        is Resource.Loading -> ProgressBar()
        is Resource.Success -> revokeAccessResponse.data?.let { accessRevoked ->
            LaunchedEffect(accessRevoked) {
                navigateToAuthScreen(accessRevoked)
            }
        }
        is Resource.Error -> LaunchedEffect(Unit) {
            print(revokeAccessResponse.message)
            showSnackBar()
        }
    }
}