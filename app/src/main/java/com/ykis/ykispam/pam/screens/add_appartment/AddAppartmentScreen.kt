package com.ykis.ykispam.pam.screens.add_appartment

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykis.ykispam.R
import com.ykis.ykispam.core.composable.BasicField
import com.ykis.ykispam.core.composable.BasicImageButton
import com.ykis.ykispam.core.composable.BasicToolbar
import com.ykis.ykispam.core.ext.smallSpacer
import com.ykis.ykispam.pam.screens.appartment.AppartmentViewModel
import com.ykis.ykispam.R.string as AppText


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddAppartmentScreen(
    popUpScreen: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AppartmentViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState

    val keyboard = LocalSoftwareKeyboardController.current
    Column(
        modifier = Modifier
            .padding(PaddingValues(0.dp))
            .fillMaxWidth()
            .fillMaxHeight()

    ) {
        BasicToolbar(AppText.add_appartment, isFullScreen = true)
        Spacer(modifier = Modifier.smallSpacer())

        Card(
            modifier = Modifier.padding(PaddingValues(8.dp)),
            backgroundColor = MaterialTheme.colors.background,
            elevation = 20.dp,
            shape = RoundedCornerShape(10.dp)
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
                            text = stringResource(id = R.string.add_flat_secret_сode),
                            modifier = Modifier.padding(4.dp),
                            textAlign = TextAlign.Left,
                            style = MaterialTheme.typography.h6
                        )
                        Text(
                            text = stringResource(id = R.string.tooltip_code),
                            modifier = Modifier.padding(4.dp),
                            textAlign = TextAlign.Left,
                            style = MaterialTheme.typography.body2
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
                            uiState.secretCode,
                            viewModel::onSecretCodeChange,
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
                        viewModel.onAddAppartmentClick(popUpScreen)
                    }

                }
            }
        }

    }

}

