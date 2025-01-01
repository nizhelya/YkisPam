package com.ykis.mob.ui.screens.auth.verify_email

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ykis.mob.R
import com.ykis.mob.core.snackbar.SnackbarManager
import com.ykis.mob.ui.components.appbars.DefaultAppBar
import com.ykis.mob.ui.navigation.LaunchScreen
import com.ykis.mob.ui.screens.auth.sign_up.SignUpViewModel
import com.ykis.mob.ui.screens.auth.verify_email.components.ReloadUser
import com.ykis.mob.ui.screens.auth.verify_email.components.SendEmailVerification
import com.ykis.mob.ui.theme.YkisPAMTheme
import com.ykis.mob.R.string as AppText

@Composable
fun VerifyEmailScreenStateless(
    modifier: Modifier = Modifier,
    onReloadClick: () -> Unit,
    onRepeatEmailClick: () -> Unit,
    email: String,
    navigateBack :()->Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .widthIn(max = 460.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DefaultAppBar(
                canNavigateBack = true,
                title = "Підтвердження пошти",
                onBackClick = { navigateBack()}
            )
        }

        Column(
            modifier = modifier
                .padding(horizontal = 8.dp, vertical = 64.dp)
                .verticalScroll(rememberScrollState())
                .align(Alignment.TopCenter)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            Image(
                modifier = modifier.size(196.dp),
                painter = painterResource(R.drawable.ic_email_letter),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Ми відправили вам лист на пошту",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Перейдіть за посиланням , яким ми відправили на пошту ${email}. Потім нажміть на кнопку далі.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Не отримали лист?")
                Text(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .clickable { onRepeatEmailClick() }
                        .padding(4.dp)
                        ,
                    text = "Надіслати ще раз",
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            onClick = { onReloadClick() }
        ) {
            Text(text = "Далі")
        }
    }
}

@Composable
fun VerifyEmailScreen(
    viewModel: SignUpViewModel,
    restartApp: (String) -> Unit,
    navController: NavController
) {
    VerifyEmailScreenStateless(
        onRepeatEmailClick = {
            viewModel.repeatEmailVerified()
        },
        onReloadClick = {
            viewModel.reloadUser()
        },
        email = viewModel.email,
        navigateBack = {
            navController.navigateUp()
        }
    )
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

@Preview
@Composable
private fun VerifyEmailScreenPreview() {
    YkisPAMTheme {
        Box(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
        ) {
            VerifyEmailScreenStateless(
               onReloadClick = {},
                onRepeatEmailClick = {},
                email = "rshulik74@gmail.com",
                navigateBack = {}
            )
        }
    }
}