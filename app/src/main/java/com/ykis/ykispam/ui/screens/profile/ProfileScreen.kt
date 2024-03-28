package com.ykis.ykispam.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.twotone.Email
import androidx.compose.material.icons.twotone.Key
import androidx.compose.material.icons.twotone.Nat
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.ykis.ykispam.core.Constants.REVOKE_ACCESS_MESSAGE
import com.ykis.ykispam.core.Constants.SIGN_OUT
import com.ykis.ykispam.ui.YkisPamAppState
import com.ykis.ykispam.ui.components.BaseCard
import com.ykis.ykispam.ui.components.appbars.DefaultAppBar
import com.ykis.ykispam.ui.components.appbars.DeleteMyAccountCard
import com.ykis.ykispam.ui.components.appbars.SignOutCard
import com.ykis.ykispam.ui.navigation.Graph
import com.ykis.ykispam.ui.navigation.LaunchScreen
import com.ykis.ykispam.ui.navigation.NavigationType
import com.ykis.ykispam.ui.screens.profile.components.RevokeAccess
import com.ykis.ykispam.ui.screens.profile.components.SignOut
import com.ykis.ykispam.ui.theme.YkisPAMTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    appState: YkisPamAppState,
    cleanNavigateToDestination: (String) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel(),
    navigationType: NavigationType,
    onDrawerClicked: () -> Unit,
    navigateToSettings: () -> Unit
) {

    ProfileScreenStateless(
        photoUrl = viewModel.photoUrl,
        displayName = viewModel.displayName,
        email = viewModel.email,
        uid = viewModel.uid,
        providerId = viewModel.providerId,
        signOut = { viewModel.signOut() },
        revokeAccess = { viewModel.revokeAccess() },
        navigationType = navigationType,
        onDrawerClicked = onDrawerClicked,
        navigateToSettings = navigateToSettings
    )
    SignOut(
        navigateToAuthScreen = { signedOut ->
            if (signedOut) {
                cleanNavigateToDestination(Graph.AUTHENTICATION)
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
                cleanNavigateToDestination(LaunchScreen.route)
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
    onDrawerClicked : () ->Unit,
    navigationType: NavigationType,
    navigateToSettings : () ->Unit
) {
    var openMenu by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        DefaultAppBar(
            title = stringResource(id = R.string.profile),
            navigationType = navigationType,
            onDrawerClick = onDrawerClicked,
            canNavigateBack = false,
            actionButton = {
                IconButton(
                    onClick = {
//                        openMenu = !openMenu
                              navigateToSettings()
                              },
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = stringResource(id = R.string.settings),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                DropdownMenu(
                    expanded = openMenu,
                    onDismissRequest = { openMenu = !openMenu },
                    modifier = Modifier
                        .wrapContentSize(align = Alignment.TopCenter)
//                        .fillMaxWidth(0.5f)
                        .background(color = MaterialTheme.colorScheme.background)
                ) {
                    SignOutCard(signOut)
                    DeleteMyAccountCard(revokeAccess)
                }
            }
        )

        BaseCard() {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
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
                        error = painterResource(id = R.drawable.ic_valve_filled),
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
            navigationType = NavigationType.BOTTOM_NAVIGATION,
            onDrawerClicked ={},
            navigateToSettings = {}
        )
    }
}



