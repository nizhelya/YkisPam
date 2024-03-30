package com.ykis.ykispam.ui.screens.auth.sign_in.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykis.ykispam.core.CenteredProgressIndicator
import com.ykis.ykispam.core.Resource
import com.ykis.ykispam.ui.screens.auth.sign_in.SignInViewModel


@Composable
fun SignInWithGoogle(
    viewModel: SignInViewModel = hiltViewModel(),
    navigateToHomeScreen: (signedIn: Boolean) -> Unit
) {
    when (val signInWithGoogleResponse = viewModel.signInWithGoogleResponse) {
        is Resource.Loading -> CenteredProgressIndicator()
        is Resource.Success -> signInWithGoogleResponse.data?.let { signedIn ->
            LaunchedEffect(signedIn) {
                navigateToHomeScreen(signedIn)
            }
        }

        is Resource.Error -> LaunchedEffect(Unit) {
            print(signInWithGoogleResponse.message)
        }

    }
}



