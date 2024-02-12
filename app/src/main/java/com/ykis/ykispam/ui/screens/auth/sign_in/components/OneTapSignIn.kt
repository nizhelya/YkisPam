package com.ykis.ykispam.ui.screens.auth.sign_in.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.ykis.ykispam.core.ProgressBar
import com.ykis.ykispam.core.Response
import com.ykis.ykispam.ui.screens.auth.sign_in.SignInViewModel

@Composable
fun OneTapSignIn(
    viewModel: SignInViewModel = hiltViewModel(),
    launch: (result: BeginSignInResult) -> Unit
) {
    when (val oneTapSignInResponse = viewModel.oneTapSignInResponse) {
        is Response.Loading -> ProgressBar()
        is Response.Success -> oneTapSignInResponse.data?.let {
            LaunchedEffect(it) {
                launch(it)
            }
        }

        is Response.Failure -> LaunchedEffect(Unit) {
            print(oneTapSignInResponse.e)
        }
    }
}