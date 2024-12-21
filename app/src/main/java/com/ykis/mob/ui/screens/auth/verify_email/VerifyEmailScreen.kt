package com.ykis.mob.ui.screens.auth.verify_email

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ykis.mob.core.composable.BasicToolbar
import com.ykis.mob.core.composable.LogoImage
import com.ykis.mob.core.ext.spacer
import com.ykis.mob.core.snackbar.SnackbarManager
import com.ykis.mob.ui.navigation.LaunchScreen
import com.ykis.mob.ui.screens.auth.sign_up.SignUpViewModel
import com.ykis.mob.ui.screens.auth.verify_email.components.ReloadUser
import com.ykis.mob.ui.screens.auth.verify_email.components.SendEmailVerification
import com.ykis.mob.R.string as AppText

@Composable
fun VerifyEmailScreen(
    viewModel: SignUpViewModel,
    restartApp: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(PaddingValues(4.dp, 4.dp, 4.dp, 4.dp)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        BasicToolbar(AppText.verify_email)

        Spacer(modifier = Modifier.spacer())
        LogoImage()
        Spacer(modifier = Modifier.spacer())
        Text(
            modifier = Modifier.clickable {
                viewModel.reloadUser()
            },
            text = stringResource(AppText.alredy_user),
            fontSize = 16.sp,
            textDecoration = TextDecoration.Underline
        )
        Spacer(modifier = Modifier.spacer())
        Text(
            modifier = Modifier.padding(48.dp),
            text = stringResource(AppText.span_email),
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.spacer())

        Text(
            modifier = Modifier.clickable {
                viewModel.repeatEmailVerified()
            },
            text = stringResource(AppText.repeat_email_not_verified_message),
            fontSize = 16.sp,
            textDecoration = TextDecoration.Underline
        )
    }
    SendEmailVerification(
        navigateToLaunchScreen = {
            if (viewModel.isEmailVerified) {
                restartApp(LaunchScreen.route)
            } else {
                SnackbarManager.showMessage(AppText.email_not_verified_message)

            }        },
        viewModel= viewModel
    )

    ReloadUser(
        navigateToProfileScreen = {
            if (viewModel.isEmailVerified) {
                restartApp(LaunchScreen.route)
            } else {
                SnackbarManager.showMessage(AppText.email_not_verified_message)

            }
        },
        viewModel = viewModel
    )
}