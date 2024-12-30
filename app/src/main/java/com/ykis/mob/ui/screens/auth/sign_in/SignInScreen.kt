package com.ykis.mob.ui.screens.auth.sign_in

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInPassword
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider.getCredential
import com.ykis.mob.R
import com.ykis.mob.core.composable.BasicLinkButton
import com.ykis.mob.core.composable.BasicToolbar
import com.ykis.mob.core.composable.EmailField
import com.ykis.mob.core.composable.LogoImage
import com.ykis.mob.core.composable.PasswordField
import com.ykis.mob.core.composable.RegularCardEditor
import com.ykis.mob.core.ext.card
import com.ykis.mob.core.ext.largeSpacer
import com.ykis.mob.core.ext.mediumSpacer
import com.ykis.mob.core.ext.smallSpacer
import com.ykis.mob.core.ext.textButton
import com.ykis.mob.core.snackbar.SnackbarManager
import com.ykis.mob.core.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.ykis.mob.ui.components.appbars.DefaultAppBar
import com.ykis.mob.ui.navigation.LaunchScreen
import com.ykis.mob.ui.screens.auth.sign_in.components.OneTapSignIn
import com.ykis.mob.ui.screens.auth.sign_in.components.SignInWithGoogle
import com.ykis.mob.ui.theme.YkisPAMTheme
import com.ykis.mob.R.drawable as AppIcon
import com.ykis.mob.R.string as AppText


@Composable
fun SignInScreenStateless(
    modifier: Modifier = Modifier,
    email : String,
    onEmailChange : (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onSignInClick : ()->Unit,
    onForgotPasswordClick : () ->Unit,
    onSignUpClick : () -> Unit,
    onGoogleClick : () -> Unit
) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Column(
                modifier = modifier
                    .fillMaxHeight()
                    .widthIn(max = 460.dp) ,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DefaultAppBar(
                    title = stringResource(R.string.login_details),
                    canNavigateBack = false,
                )
                Column(
                    modifier = modifier
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                        .verticalScroll(rememberScrollState())
                    ,
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LogoImage()
                    Spacer(modifier = Modifier.height(16.dp))
                    EmailField(email, onEmailChange, modifier)
                    Spacer(modifier = Modifier.height(8.dp))
                    PasswordField(password, onPasswordChange)
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            modifier = modifier
                                .clip(MaterialTheme.shapes.medium)
                                .clickable {
                                    onForgotPasswordClick()
                                }
                                .padding(4.dp),
                            text = stringResource(R.string.forget_password)
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        modifier = modifier.fillMaxWidth(),
                        onClick = {
                            onSignInClick()
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.sign_in),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Row(
                        modifier = modifier.padding(vertical = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(modifier = modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.outline))
                        Text(
                            modifier = modifier.padding(horizontal = 4.dp),
                            text = stringResource(R.string.or),
                            color = MaterialTheme.colorScheme.outline
                        )
                        Box(modifier = modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(MaterialTheme.colorScheme.outline))
                    }
                    OutlinedButton(
                        modifier = modifier.fillMaxWidth(),
                        onClick = {
                            onGoogleClick()
                        }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(R.string.sign_in_with_google),
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = modifier.widthIn(16.dp))
                            Image(
                                painter = painterResource(R.drawable.ic_google_logo),
                                contentDescription = "google logo"
                            )
                        }
                    }
                    Spacer(modifier = modifier.height(8.dp))
                    Row(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.have_no_account)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            modifier = modifier
                                .clip(MaterialTheme.shapes.small)
                                .clickable {
                                    onSignUpClick()
                                },
                            color = MaterialTheme.colorScheme.primary,
                            text = stringResource(R.string.sign_up)
                        )
                    }
                }
            }
        }

}

@Composable
fun SignInScreen(
    openScreen: (String) -> Unit,
    navigateToDestination: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel(),
) {
    val singInUiState = viewModel.singInUiState
    val keyboard = LocalSoftwareKeyboardController.current

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                try {
                    val credentials =
                        viewModel.oneTapClient.getSignInCredentialFromIntent(result.data)
                    val googleIdToken = credentials.googleIdToken
                    val googleCredentials = getCredential(googleIdToken, null)
                    viewModel.signInWithGoogle(googleCredentials)
                } catch (it: ApiException) {
                    print(it)
                    SnackbarManager.showMessage(it.toSnackbarMessage())

                }
            }
        }

    SignInScreenStateless(
        email = singInUiState.email,
        onEmailChange = viewModel::onEmailChange,
        password = singInUiState.password,
        onPasswordChange = viewModel::onPasswordChange,
        onSignInClick = {
            keyboard?.hide()
            viewModel.onSignInClick(openScreen)
        },
        onForgotPasswordClick = {
            viewModel.onForgotPasswordClick()
        },
        onSignUpClick = {
            viewModel.onSignUpClick(openScreen)
        },
        onGoogleClick = {
            viewModel.oneTapSignIn()
        }
    )

    fun launch(signInResult: BeginSignInResult) {
        val intent = IntentSenderRequest.Builder(signInResult.pendingIntent.intentSender).build()
        launcher.launch(intent)
    }

    OneTapSignIn(
        launch = {
            launch(it)
        }
    )

    SignInWithGoogle(
        navigateToHomeScreen = { signedIn ->
            if (signedIn) {
                navigateToDestination(LaunchScreen.route)
            }
        }
    )

}

@Preview(showBackground = true, device = "spec:parent=pixel_5,orientation=landscape")
@Composable
private fun SignInScreenPreview() {
    YkisPAMTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            SignInScreenStateless(
                email = "",
                onEmailChange = {},
                password = "",
                onSignInClick = {},
                onForgotPasswordClick = {},
                onSignUpClick = {},
                onPasswordChange = {},
                onGoogleClick = {},
            )
        }
    }
}