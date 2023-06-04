package com.ykis.ykispam.firebase.screens.profile.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykis.ykispam.core.ProgressBar
import com.ykis.ykispam.core.Response
import com.ykis.ykispam.firebase.screens.profile.ProfileViewModel

@Composable
fun RevokeAccess(
    viewModel: ProfileViewModel = hiltViewModel(),
    restartApp: () -> Unit,
    showRevokedMessage: () -> Unit
) {


    when (val revokeAccessResponse = viewModel.revokeAccessResponse) {
        is Response.Loading -> ProgressBar()
        is Response.Success -> {
            val isAccessRevoked = revokeAccessResponse.data
            LaunchedEffect(isAccessRevoked) {
                if (isAccessRevoked == true) {
                    restartApp()
                    showRevokedMessage()
                }
            }
        }

        is Response.Failure -> revokeAccessResponse.apply {
            LaunchedEffect(e) {
                print(e)

            }
        }
    }
}