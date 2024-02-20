package com.ykis.ykispam.ui.screens.appartment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ykis.ykispam.R
import com.ykis.ykispam.core.composable.BasicField
import com.ykis.ykispam.core.composable.BasicImageButton
import com.ykis.ykispam.ui.navigation.NavigationType
import com.ykis.ykispam.ui.navigation.navigateToApartment
import com.ykis.ykispam.ui.screens.appbars.AddAppBar
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

@Composable
fun AddApartmentScreenContent(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    viewModel: ApartmentViewModel = hiltViewModel(),
    navController : NavHostController,
    canNavigateBack : Boolean,
    onDrawerClicked : () -> Unit,
    navigationType: NavigationType
) {
    
    val secretCode by viewModel.secretCode.collectAsState()
    val keyboard = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AddAppBar(
            modifier = Modifier,
            stringResource(id = R.string.add_flat_secret_сode),
            stringResource(id = R.string.add_appartment),
            onBackPressed = {
                navController.popBackStack()
                viewModel.onSecretCodeChange("")
                            },
            canNavigateBack = canNavigateBack,
            onDrawerClicked = onDrawerClicked,
            navigationType = navigationType
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
                            modifier = Modifier,
                            enabled = secretCode.isNotEmpty()
                        )
                        {
                            keyboard?.hide()
                            viewModel.addApartment {
                                addressId->
                                navController.navigateToApartment(addressId)
                            }
                        }
                    }
                }
            }
        }

    }
}



