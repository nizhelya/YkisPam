package com.ykis.mob.ui.screens.auth.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ykis.mob.core.composable.BasicLinkButton
import com.ykis.mob.core.composable.EmailField
import com.ykis.mob.core.composable.LogoImage
import com.ykis.mob.core.composable.PasswordField
import com.ykis.mob.core.composable.RegularCardEditor
import com.ykis.mob.core.composable.RepeatPasswordField
import com.ykis.mob.core.ext.card
import com.ykis.mob.core.ext.spacer
import com.ykis.mob.core.ext.textButton
import com.ykis.mob.core.snackbar.SnackbarManager
import com.ykis.mob.ui.screens.auth.sign_up.components.AgreementChekBox
import com.ykis.mob.ui.screens.auth.sign_up.components.SignUp
import com.ykis.mob.ui.screens.auth.sign_up.components.SignUpTopBar
import com.ykis.mob.R.drawable as AppIcon
import com.ykis.mob.R.string as AppText

@Composable
fun SignUpScreen(
    openScreen: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel
) {
    Row( modifier = modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center)
    {
        Column(
            modifier = modifier
                .widthIn(0.dp, 460.dp)
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val signUpUiState by viewModel.signUpUiState
            val keyboard = LocalSoftwareKeyboardController.current
            var checkedState by rememberSaveable { mutableStateOf(false) }
            SignUpTopBar(navigateBack = { viewModel.navigateBack(openScreen) })
            Spacer(modifier = Modifier.spacer())
            LogoImage()
            Spacer(modifier = Modifier.spacer())
            if (!checkedState) {
                Text(
                    text = viewModel.agreementTitle.collectAsState().value.ifEmpty {
                        stringResource(id = AppText.agreement_title)
                    },
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(4.dp, 0.dp),
                    style = MaterialTheme.typography.titleSmall
                )
                Card(
                    modifier = Modifier.padding(PaddingValues(8.dp)),
                    elevation = CardDefaults.cardElevation(20.dp),
                    shape = RoundedCornerShape(20.dp),
                )
                {
                    Text(
                        text = viewModel.agreementText.collectAsState().value.ifEmpty {
                            stringResource(id = AppText.agreement_text)
                        },
                        modifier = Modifier.padding(PaddingValues(8.dp)),
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.bodyMedium

                    )
                }
            }
            AgreementChekBox(
                checked = checkedState,
                onCheckedChange = { checkedState = it },
                modifier = Modifier
            )
            if (checkedState) {
                Column(
                    modifier = Modifier
                        .padding(PaddingValues(8.dp))
                        .widthIn(0.dp, 480.dp),
                )
                {
                    EmailField(signUpUiState.email, viewModel::onEmailChange, modifier)
                    PasswordField(signUpUiState.password, viewModel::onPasswordChange)
                    RepeatPasswordField(
                        signUpUiState.repeatPassword,
                        viewModel::onRepeatPasswordChange,
                        modifier
                    )

                    RegularCardEditor(
                        AppText.create_account_sign,
                        AppIcon.ic_create_account,
                        "",
                        Modifier.card()
                    ) {
                        keyboard?.hide()
                        viewModel.signUpWithEmailAndPassword()
                    }
                }


                BasicLinkButton(AppText.alredy_user, Modifier.textButton()) {
                    viewModel.navigateBack(openScreen)
                }

            }
        }
    }
    SignUp(
        sendEmailVerification = {
            viewModel.sendEmailVerification(openScreen)
        },
        showVerifyEmailMessage = {
            SnackbarManager.showMessage(AppText.verify_email_message)
        },
        showErrorMessage = { errorMessage ->
            SnackbarManager.showMessage(errorMessage)
        },
        viewModel = viewModel
    )

//    SendEmailVerification()
}
