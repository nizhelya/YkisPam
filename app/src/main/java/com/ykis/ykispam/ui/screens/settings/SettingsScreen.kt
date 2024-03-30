package com.ykis.ykispam.ui.screens.settings

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ykis.ykispam.R
import com.ykis.ykispam.core.CenteredProgressIndicator
import com.ykis.ykispam.core.composable.DialogCancelButton
import com.ykis.ykispam.core.composable.DialogConfirmButton
import com.ykis.ykispam.ui.components.SingleSelectDialog
import com.ykis.ykispam.ui.components.appbars.DefaultAppBar
import com.ykis.ykispam.ui.navigation.NavigationType
import com.ykis.ykispam.ui.theme.YkisPAMTheme
import firebase.com.protolitewrapper.BuildConfig

@Composable
fun SettingsScreenStateful(
    modifier: Modifier = Modifier,
    navigationType: NavigationType,
    viewModel: NewSettingsViewModel = hiltViewModel(),
    navigateToAuthGraph : () -> Unit,
    onDrawerClick: () -> Unit,
    ) {
    val theme by viewModel.theme.collectAsStateWithLifecycle()
    val loading by viewModel.loading.collectAsStateWithLifecycle()
    var themeLocation by remember {
        mutableIntStateOf(0)
    }
    Crossfade(targetState = loading, label = "") {
        if (it) {
            CenteredProgressIndicator()
        } else SettingsScreenStateless(
            isSwitchOn = true,
            navigationType = navigationType,
            toggleSwitch = {
            },
            theme = theme,
            themeLocation = themeLocation,
            onThemeChange = {
                themeLocation = if (theme.isNullOrEmpty()) {
                    2
                } else {
                    themes.indexOf(theme)
                }
            },
            getThemeValues =
            {
                viewModel.getThemeValue()
            },
            setThemeValues = {
                viewModel.setThemeValue(it)
            },
            revokeAccess = {
                viewModel.revokeAccess(
                    navigateToAuthGraph
                )
            },
            signOut = {
                viewModel.signOut(
                    navigateToAuthGraph
                )
            },
            onDrawerClick = {
                onDrawerClick()
            },
            photoUrl =viewModel.photoUrl,
            email = viewModel.email
        )
    }

}

@Composable
fun SettingsScreenStateless(
    modifier: Modifier = Modifier,
    navigationType: NavigationType,
    isSwitchOn: Boolean,
    toggleSwitch: () -> Unit,
    theme: String?,
    themeLocation: Int,
    onThemeChange: () -> Unit,
    getThemeValues: () -> Unit,
    setThemeValues: (String) -> Unit,
    revokeAccess: () -> Unit,
    signOut: () -> Unit,
    onDrawerClick : () -> Unit,
    photoUrl : String,
    email : String
) {
    var showChangeThemeDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var showLogOutDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var showDeleteAccountDialog by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = theme) {
        getThemeValues()
        onThemeChange()
    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DefaultAppBar(
            title = stringResource(id = R.string.settings),
            navigationType = navigationType,
            canNavigateBack = false,
            onDrawerClick = {
                onDrawerClick()
            }
        )

        Column(
            modifier = modifier
                .padding(horizontal = 8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = modifier.fillMaxWidth()
            ){
                Row(
                    modifier = modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(photoUrl)
                            .build(),
                        contentDescription = null,
                        error = painterResource(id = R.drawable.ic_account_circle),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .width(48.dp)
                            .height(48.dp)
                    )
                    Text(
                        text = email,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }


            Card {
                SettingsGroup(name = R.string.settings_loadin_from_server) {

                    SettingsSwitch(
                        name = R.string.loading_from_wifi,
                        icon = R.drawable.ic_loading_wifi,
                        iconDesc = R.string.loading_from_wifi,
                        // value is collected from StateFlow - updates the UI on change
                        state = isSwitchOn
                    ) {
                        // call ViewModel to toggle the value
                        toggleSwitch()
                    }
                    SettingsSwitch(
                        name = R.string.loading_from_mobile,
                        icon = R.drawable.ic_loading_mobile,
                        iconDesc = R.string.loading_from_mobile,
                        // value is collected from StateFlow - updates the UI on change
                        state = isSwitchOn
                    ) {
                        toggleSwitch()
                    }
                }
            }
            Card{
                    Row(
                        modifier = modifier
                            .clip(
                                shape = CardDefaults.shape
                            )
                            .clickable {
                                showChangeThemeDialog = true
                            }
                            .padding(
                                vertical = 16.dp,
                                horizontal = 8.dp
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DarkMode,
                            contentDescription = null
                        )
                        Text(
                            modifier = modifier.weight(1f),
                            text = "Режим теми"
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            contentDescription = null
                        )
                    }
            }
            Column(
                modifier = modifier.weight(1f),
                verticalArrangement = Arrangement.Bottom
            ) {
                OutlinedButton(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = {
                        showLogOutDialog = true
                    },
                ) {
                    Icon(
                        modifier = modifier.padding(end = 8.dp),
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = null
                    )
                    Text(
                        "Вийти з акаунту"
                    )
                }
                TextButton(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = { showDeleteAccountDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.error,
                        containerColor = Color.Transparent
                    )
                ) {
                    Icon(
                        modifier = modifier.padding(end = 8.dp),
                        imageVector = Icons.Default.DeleteForever,
                        contentDescription = null
                    )
                    Text(
                        "Видалити акаунт",
                    )
                }
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp),
                    text = "Версія " + BuildConfig.VERSION_NAME,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }

    if (showDeleteAccountDialog) {
        AlertDialog(
            title = { Text(stringResource(R.string.delete_account_title)) },
            text = { Text(stringResource(R.string.delete_account_description)) },
            dismissButton = {
                DialogCancelButton(R.string.cancel) {
                    showDeleteAccountDialog = false
                }
            },
            confirmButton = {
                DialogConfirmButton(R.string.delete_my_account) {
//                    deleteMyAccount()
                    revokeAccess()
                    showDeleteAccountDialog = false
                }
            },
            onDismissRequest = { showDeleteAccountDialog = false }
        )
    }
    if (showLogOutDialog) {
        AlertDialog(
            title = { Text(stringResource(R.string.sign_out_title)) },
            text = { Text(stringResource(R.string.sign_out_description)) },
            dismissButton = { DialogCancelButton(R.string.cancel) { showLogOutDialog = false } },
            confirmButton = {
                DialogConfirmButton(R.string.sign_out) {
//                    signOut()
                    signOut()
                    showLogOutDialog = false
                }
            },
            onDismissRequest = { showLogOutDialog = false }
        )
    }
    if (showChangeThemeDialog) {
        SingleSelectDialog(modifier = modifier,
            title = "Оберіть режим",
            optionsList = themes,
            defaultSelected = themeLocation,
            submitButtonText = stringResource(id = R.string.save),
            dismissButtonText = stringResource(id = R.string.cancel),
            onSubmitButtonClick = { id ->
                setThemeValues(themes[id])
                showChangeThemeDialog = false
            },
            onDismissRequest = {
                showChangeThemeDialog = false
            })
    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewSettingsScreen() {
    YkisPAMTheme {
        SettingsScreenStateless(
            toggleSwitch = {},
            navigationType = NavigationType.BOTTOM_NAVIGATION,
            isSwitchOn = true,
            onThemeChange = {},
            themeLocation = 2,
            theme = "Light Mode",
            modifier = Modifier,
            getThemeValues = {},
            setThemeValues = {
            },
            signOut = {},
            revokeAccess = {},
            onDrawerClick = {},
            photoUrl = "https://i.pinimg.com/originals/b7/6f/1d/b76f1d3521154c1e9b4da31ca19b17ad.jpg",
            email = "rshulik74@gmail.com"
        )
    }
}

enum class ThemeValues(val title: String) {
    LIGHT_MODE("Light Mode"),
    DARK_MODE("Dark Mode"),
    SYSTEM_DEFAULT("System Default")
}

val themes = listOf(
    ThemeValues.LIGHT_MODE.title,
    ThemeValues.DARK_MODE.title,
    ThemeValues.SYSTEM_DEFAULT.title
)
