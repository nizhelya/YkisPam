package com.ykis.ykispam.pam.screens.appartment

import android.annotation.SuppressLint
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.window.layout.DisplayFeature
import com.ykis.ykispam.BaseUIState
import com.ykis.ykispam.R
import com.ykis.ykispam.core.composable.BasicField
import com.ykis.ykispam.core.composable.BasicImageButton
import com.ykis.ykispam.core.composable.BasicToolbar
import com.ykis.ykispam.core.ext.smallSpacer
import com.ykis.ykispam.navigation.ContentType
import com.ykis.ykispam.navigation.NavigationType
import com.ykis.ykispam.pam.domain.family.FamilyEntity
import com.ykis.ykispam.pam.screens.appartment.ApartmentViewModel
import com.ykis.ykispam.pam.screens.family.FamilyList
import com.ykis.ykispam.pam.screens.family.FamilyScreenContent
import com.ykis.ykispam.theme.YkisPAMTheme
import kotlinx.coroutines.launch
import com.ykis.ykispam.R.string as AppText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddApartmentScreen(
    popUpScreen: () -> Unit,
    restartApp: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ApartmentViewModel = hiltViewModel()
) {
    val secretKeyUiState = viewModel.secretKeyUiState

    AddApartmentScreenContent(
        secretKeyUiState = secretKeyUiState,
        onSecretCodeChange = viewModel::onSecretCodeChange,
        onAddApartmentClick = { viewModel.onAddApartmentClick(restartApp) },
        navigateBack = { viewModel.navigateBack(popUpScreen) }
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
    onAddApartmentClick: () -> Unit,
    navigateBack: () -> Unit,
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
        DetailTopAppBar(
            modifier,
            stringResource(id = R.string.add_flat_secret_сode),
            stringResource(id = R.string.add_appartment),
            navigateBack = { navigateBack() })


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
                        modifier = Modifier.padding(PaddingValues(8.dp))
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
                                keyboardType = KeyboardType.Number
                            )
                        }
                        BasicImageButton(
                            AppText.add,
                            R.drawable.ic_stat_name,
                            modifier = Modifier
                        )
                        {
                            keyboard?.hide()
                            onAddApartmentClick()
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
            isSelectable = false,
            isSelected = false,
            modifier = Modifier,
            secretKeyUiState = SecretKeyUiState(secretCode = "4554545454545"),
            onSecretCodeChange = {},
            onAddApartmentClick = { },
            navigateBack = { }
        )
    }
}


