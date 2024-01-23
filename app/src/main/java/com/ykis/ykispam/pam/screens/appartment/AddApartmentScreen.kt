package com.ykis.ykispam.pam.screens.appartment

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.R
import com.ykis.ykispam.YkisPamAppState
import com.ykis.ykispam.core.composable.BasicField
import com.ykis.ykispam.core.composable.BasicImageButton
import com.ykis.ykispam.pam.screens.appbars.AddAppBar
import com.ykis.ykispam.R.string as AppText


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AddApartmentScreen(
//    popUpScreen: () -> Unit,
//    restartApp: (String) -> Unit,
//    onBackPressed: () -> Unit = {},
//    viewModel: ApartmentViewModel = hiltViewModel()
//) {
//    Log.d("state_test","AddApartmentScreen:${viewModel._uiState.collectAsState().value.addressId}")
//    AddApartmentScreenContent(
//        viewModel=viewModel,
//        onSecretCodeChange = viewModel::onSecretCodeChange,
//        addApartment = { viewModel.addApartment(restartApp) },
//        onBackPressed = { viewModel.navigateBack(popUpScreen) },
//    )
//}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddApartmentScreenContent(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    appState: YkisPamAppState,
    viewModel: ApartmentViewModel

) {
    Log.d("viewModel_test" , "AddApartmentScreenContent:$viewModel")
    val secretCode by viewModel.secretCode.collectAsState()
    val keyboard = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .padding(4.dp)
            .widthIn(0.dp, 460.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AddAppBar(
            modifier = Modifier,
            stringResource(id = R.string.add_flat_secret_сode),
            stringResource(id = R.string.add_appartment),
            onBackPressed = {viewModel.navigateBack{ appState.popUp() }}
        )
        Card(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(PaddingValues(8.dp)),

                        ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                        ) {

                            Text(
                                text = stringResource(id = R.string.tooltip_code),
                                modifier = Modifier.padding(4.dp),
                                textAlign = TextAlign.Left,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(PaddingValues(8.dp)),

                        ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 4.dp, end = 8.dp)
                        ) {
                            BasicField(
                                AppText.secret_сode,
                                AppText.secret_сode,
                                secretCode,
                                onNewValue = { viewModel.onSecretCodeChange(it)},
                                modifier = Modifier.padding(4.dp),
                            )
                        }
                        BasicImageButton(
                            AppText.add,
                            R.drawable.ic_stat_name,
                            modifier = Modifier
                        )
                        {
                            keyboard?.hide()
                            viewModel.addApartment { route -> appState.clearAndNavigate(route) }
                        }

                    }
                }
            }
        }

    }
}



