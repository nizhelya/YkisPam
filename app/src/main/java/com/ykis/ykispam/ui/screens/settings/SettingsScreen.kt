package com.ykis.ykispam.ui.screens.settings

//com.heregoesyourpackagename.BuildConfig.VERSION_CODE
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ykis.ykispam.R
import com.ykis.ykispam.data.cache.preferences.UserPreferences
import com.ykis.ykispam.ui.components.appbars.DefaultAppBar
import com.ykis.ykispam.ui.theme.YkisPAMTheme
import firebase.com.protolitewrapper.BuildConfig
import kotlinx.coroutines.launch

@Composable
fun SettingsScreenStateful(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel(),
    popUpScreen: () -> Unit,

) {
    val isSwitchOn = viewModel.isSwitchOn.collectAsStateWithLifecycle()
    SettingsScreenStateless(
        popUpScreen = {popUpScreen()},
        isSwitchOn = isSwitchOn.value,
        toggleSwitch = {
            viewModel.toggleSwitch()
        },
//        isDarkTheme = isDarkTheme,
//        onThemeChange = onThemeChange
    )
}

@Composable
fun SettingsScreenStateless(
    modifier: Modifier = Modifier,
    popUpScreen: () -> Unit,
    isSwitchOn : Boolean,
    toggleSwitch : () ->Unit,
    viewModel: NewSettingsViewModel = hiltViewModel()
//    isDarkTheme : Boolean ,
//    onThemeChange : () -> Unit,
) {
    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }
//    val settingsUiState by viewModel.settingsState.collectAsStateWithLifecycle()
    val theme by viewModel.theme.collectAsStateWithLifecycle()
    var themeLocation by remember {
        mutableIntStateOf(0)
    }
    LaunchedEffect(key1 =  theme) {
        viewModel.getThemeValue()
//        viewModel.handleScreenEvents(SettingsScreenEvent.ThemeChanged)
        themeLocation = if (theme.isNullOrEmpty()) {
            3
        } else {
            themes.indexOf(theme)
        }
    }
    val scope = rememberCoroutineScope()

    val dataStore = UserPreferences(LocalContext.current , isSystemInDarkTheme())

    val isDarkMode by dataStore.getIsDarkMode.collectAsStateWithLifecycle(initialValue = isSystemInDarkTheme())
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DefaultAppBar(
            title = stringResource(id = R.string.settings),
            canNavigateBack = true,
            onBackClick = { popUpScreen() }
        )

            Column(
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
            ) {


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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Icon(
                        imageVector = Icons.Default.DarkMode ,
                        contentDescription = null
                    )
                    Text(
                        modifier = modifier.weight(1f),
                        text = "Темний режим"
                    )
                    IconButton(onClick = {
                        showDialog = true
                    }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = null)

                    }
//                    Switch(
//                        checked = isDarkMode!!,
//                        onCheckedChange = {
//                            scope.launch {
//                                dataStore.saveIsDarkMode(!isDarkMode!!)
//                            }
//                        }
//                    )
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
                onClick = { /*TODO*/ },
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
                onClick = { /*TODO*/ },
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
    if(showDialog){
        SingleSelectDialog(modifier = modifier,
            title = "Оберіть режим",
            optionsList = themes,
            defaultSelected = themeLocation,
            submitButtonText = stringResource(id = R.string.ok),
            dismissButtonText = stringResource(id = R.string.cancel),
            onSubmitButtonClick = { id ->
                scope.launch {
                    viewModel.setThemeValue(themes[id])
                    showDialog = false
//                    viewModel.handleScreenEvents(SettingsScreenEvent.SetNewTheme(themes[id]))
//                    viewModel.handleScreenEvents(SettingsScreenEvent.ThemeChanged)
                }
            },
            onDismissRequest = { value ->
                scope.launch {
                    showDialog = false
//                    viewModel.handleScreenEvents(SettingsScreenEvent.OpenThemeDialog(value))
                }
            })
    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewSettingsScreen() {
    YkisPAMTheme {
        SettingsScreenStateless(
            popUpScreen = { /*TODO*/ },
            toggleSwitch = {},
            isSwitchOn = true
        )
    }
}

@Composable
fun SingleSelectDialog(
    modifier: Modifier,
    title: String,
    optionsList: List<String>,
    defaultSelected: Int,
    submitButtonText: String,
    dismissButtonText: String,
    onSubmitButtonClick: (Int) -> Unit,
    onDismissRequest: (Boolean) -> Unit
) {

    var selectedOption by remember {
        mutableIntStateOf(defaultSelected)
    }
    var themeLocation by remember {
        mutableIntStateOf(0)
    }

    Dialog(onDismissRequest = { onDismissRequest(false) }) {
        Surface(
            modifier = modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp)
        ) {
            Column(modifier = modifier.padding(16.dp)) {

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
                )

                LazyColumn {
                    items(optionsList) {
                        RadioButtonForDialog(
                            modifier = modifier, it, optionsList[selectedOption]
                        ) { selectedValue ->
                            selectedOption = optionsList.indexOf(selectedValue)
                        }
                    }
                }

                Spacer(modifier = modifier.height(10.dp))

                Row(
                    modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    Button(
                        modifier = modifier
                            .height(48.dp)
                            .wrapContentWidth(), onClick = {
                            onSubmitButtonClick.invoke(selectedOption)
                            onDismissRequest.invoke(false)
                        }, shape = MaterialTheme.shapes.extraLarge
                    ) {
                        Text(text = submitButtonText, color = Color.White)
                    }

                    Button(
                        modifier = modifier
                            .height(48.dp)
                            .wrapContentWidth(),
                        colors = ButtonDefaults.elevatedButtonColors(),
                        onClick = {
                            onDismissRequest.invoke(false)
                        },
                        shape = MaterialTheme.shapes.extraLarge
                    ) {
                        Text(text = dismissButtonText, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun RadioButtonForDialog(
    modifier: Modifier, text: String, selectedValue: String, onClickListener: (String) -> Unit
) {
    Row(
        modifier
            .fillMaxWidth()
            .selectable(selected = (text == selectedValue), onClick = {
                onClickListener(text)
            }), verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = (text == selectedValue), onClick = {
            onClickListener(text)
        }
        )
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            modifier = modifier.padding(start = 2.dp)
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
