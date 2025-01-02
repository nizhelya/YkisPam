package com.ykis.mob.ui.screens.auth.verify_email

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ykis.mob.R
import com.ykis.mob.core.Resource
import com.ykis.mob.core.snackbar.SnackbarManager
import com.ykis.mob.ui.components.appbars.DefaultAppBar
import com.ykis.mob.ui.navigation.Graph
import com.ykis.mob.ui.screens.auth.sign_up.SignUpViewModel
import com.ykis.mob.ui.theme.YkisPAMTheme
import com.ykis.mob.R.string as AppText

@Composable
fun VerifyEmailScreenStateless(
    modifier: Modifier = Modifier,
    onReloadClick: () -> Unit,
    onRepeatEmailClick: () -> Unit,
    email: String,
    navigateBack :()->Unit,
    isLoading:Boolean
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
                title = stringResource(R.string.verify_email_title),
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
                text = stringResource(R.string.verify_email_label),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.verify_email_text, email),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.did_not_receive_email))
                Text(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .clickable { onRepeatEmailClick() }
                        .padding(4.dp)
                        ,
                    text = stringResource(R.string.send_again),
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
            AnimatedContent(
                isLoading
            ) {
                if(it) {
                    CircularProgressIndicator(
                        modifier = modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }else{
                    Text(
                        text = stringResource(R.string.next),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@Composable
fun VerifyEmailScreen(
    viewModel: SignUpViewModel,
    restartApp: (String) -> Unit,
    navController: NavController
) {
    val reloadUserResponse by viewModel.reloadUserResponse.collectAsStateWithLifecycle()
    VerifyEmailScreenStateless(
        onRepeatEmailClick = {
            viewModel.repeatEmailVerified()
        },
        onReloadClick = {
            viewModel.reloadUser{
                if (viewModel.isEmailVerified) {
                    restartApp(Graph.APARTMENT)
                } else {
                    SnackbarManager.showMessage(AppText.email_not_verified_message)
                }
            }
        },
        email = viewModel.email,
        navigateBack = {
            navController.navigateUp()
        },
        isLoading = reloadUserResponse is Resource.Loading
    )
}

@Preview
@Composable
private fun VerifyEmailScreenPreview() {
    YkisPAMTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            VerifyEmailScreenStateless(
               onReloadClick = {},
                onRepeatEmailClick = {},
                email = "rshulik74@gmail.com",
                navigateBack = {},
                isLoading = false
            )
        }
    }
}