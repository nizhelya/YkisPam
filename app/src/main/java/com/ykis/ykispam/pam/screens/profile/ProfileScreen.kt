package com.ykis.ykispam.pam.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Email
import androidx.compose.material.icons.twotone.Key
import androidx.compose.material.icons.twotone.Nat
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ykis.ykispam.R
import com.ykis.ykispam.YkisPamAppState
import com.ykis.ykispam.core.Constants.REVOKE_ACCESS_MESSAGE
import com.ykis.ykispam.core.Constants.SIGN_OUT
import com.ykis.ykispam.navigation.LAUNCH_SCREEN
import com.ykis.ykispam.pam.screens.profile.components.ProfileTopBar
import com.ykis.ykispam.pam.screens.profile.components.RevokeAccess
import com.ykis.ykispam.pam.screens.profile.components.SignOut
import com.ykis.ykispam.theme.YkisPAMTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    appState: YkisPamAppState,
    popUpScreen:() -> Unit,
    navigateToDestination: (String) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    ProfileScreenStateless(
        photoUrl = viewModel.photoUrl,
        displayName = viewModel.displayName,
        email = viewModel.email,
        uid = viewModel.uid,
        providerId = viewModel.providerId,
        signOut = { viewModel.signOut() },
        revokeAccess = { viewModel.revokeAccess() },
        navigateBack = { viewModel.navigateBack(popUpScreen) }

    )
    SignOut(
        navigateToAuthScreen = { signedOut ->
            if (signedOut) {
                navigateToDestination(LAUNCH_SCREEN)
            }
        },
        viewModel = viewModel
    )
    fun showSnackBar() = appState.coroutineScope.launch {
        val result = appState.snackbarHostState.showSnackbar(
            message = REVOKE_ACCESS_MESSAGE,
            actionLabel = SIGN_OUT
        )
        if (result == SnackbarResult.ActionPerformed) {
            viewModel.signOut()
        }
    }

    RevokeAccess(
        navigateToAuthScreen = { accessRevoked ->
            if (accessRevoked) {
                navigateToDestination(LAUNCH_SCREEN)
            }
        },
        showSnackBar = {
            showSnackBar()
        },
        viewModel = viewModel
    )
}

@ExperimentalMaterial3Api
@Composable
fun ProfileScreenStateless(
    photoUrl: String,
    displayName: String,
    email: String,
    uid: String,
    providerId: String,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    signOut: () -> Unit,
    revokeAccess: () -> Unit,
    navigateBack: () -> Unit,

    ) {

    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        ProfileTopBar(
            modifier = Modifier,
            signOut = { signOut() },
            revokeAccess = { revokeAccess() },
            navigateBack = { navigateBack() }
        )

        Card(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(photoUrl)
                            .build(),
                        contentDescription = null,
                        error = painterResource(R.drawable.ic_account_circle),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .width(48.dp)
                            .height(48.dp)
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = displayName,
                            style = MaterialTheme.typography.bodyLarge
                        )


                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 4.dp, bottom = 4.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.TwoTone.Email,
                                contentDescription = stringResource(id = R.string.email),
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = stringResource(id = R.string.email_colon),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = 4.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = email,
                                style = MaterialTheme.typography.bodyLarge

                            )

                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 4.dp, bottom = 4.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.TwoTone.Key,
                                contentDescription = stringResource(id = R.string.uid_provider),
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = stringResource(id = R.string.uid_provider),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = 4.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = uid,
                                style = MaterialTheme.typography.bodyLarge

                            )

                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 4.dp, bottom = 4.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.TwoTone.Nat,
                                contentDescription = stringResource(id = R.string.provider),
                                tint = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = stringResource(id = R.string.provider),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(all = 4.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = providerId,
                                style = MaterialTheme.typography.bodyLarge

                            )

                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {


                }

            }
        }
    }

}

@Preview(showBackground = true)
@ExperimentalMaterial3Api
@Composable
fun ProfileScreenPreview() {
    YkisPAMTheme {
        ProfileScreenStateless(
            photoUrl = "",
            displayName = "Sergey Nizhelskiy",
            email = "nizhelskiy.sergey@gmail.com",
            uid = "e8aStXd8xONf3ngKnZDAsFNOG6n2",
            providerId = "Firebase",
            signOut = { },
            revokeAccess = { },
            navigateBack = { }
        )
    }
}



