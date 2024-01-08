package com.ykis.ykispam.pam.screens.appartment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykis.ykispam.R
import com.ykis.ykispam.core.composable.BasicField
import com.ykis.ykispam.core.composable.BasicImageButton
import com.ykis.ykispam.pam.screens.appbars.AddAppBar
import com.ykis.ykispam.theme.YkisPAMTheme
import com.ykis.ykispam.R.string as AppText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddApartmentScreen(
    popUpScreen: () -> Unit,
    restartApp: (String) -> Unit,
    onBackPressed: () -> Unit = {},
    viewModel: ApartmentViewModel = hiltViewModel()
) {
    val secretKeyUiState = viewModel.secretKeyUiState

    AddApartmentScreenContent(
        secretKeyUiState = secretKeyUiState,
        onSecretCodeChange = viewModel::onSecretCodeChange,
        addApartment = { viewModel.addApartment(restartApp) },
        onBackPressed = { viewModel.navigateBack(popUpScreen) },
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterial3Api
@Composable
fun AddApartmentScreenContent(
    modifier: Modifier = Modifier,
    isSelectable: Boolean = false,
    isSelected: Boolean = false,
    secretKeyUiState: SecretKeyUiState,
    onSecretCodeChange: (String) -> Unit,
    addApartment: (String) -> Unit,
    onBackPressed: () -> Unit = {},
) {
    val keyboard = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AddAppBar(
            modifier = Modifier,
            stringResource(id = R.string.add_flat_secret_сode),
            stringResource(id = R.string.add_appartment),
            onBackPressed = onBackPressed
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
                                secretKeyUiState.secretCode,
                                onSecretCodeChange,
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
//                            onAddApartmentClick()
                            addApartment(secretKeyUiState.secretCode)
                        }

                    }
                }
            }
        }

    }
}


@Preview(showBackground = true)
@ExperimentalMaterial3Api
@Composable
fun AddApartmentScreenPreview() {
    YkisPAMTheme {
        AddApartmentScreenContent(
            secretKeyUiState = SecretKeyUiState(secretCode = "4554545454545"),
            onSecretCodeChange = {},
            addApartment = { },
            onBackPressed = { }
        )
    }
}




