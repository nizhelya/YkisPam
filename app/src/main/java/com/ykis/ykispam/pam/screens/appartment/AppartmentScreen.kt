package com.ykis.ykispam.pam.screens.appartment

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykis.ykispam.core.ext.smallSpacer
import com.ykis.ykispam.R.drawable as AppIcon
import com.ykis.ykispam.R.string as AppText

private const val LOAD_TIMEOUT = 1000L

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
@ExperimentalMaterialApi
fun AppartmentScreen(
    openScreen: (String) -> Unit,
    popUpScreen:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: AppartmentViewModel = hiltViewModel()
) {


//    CircularProgressIndicator(color = MaterialTheme.colors.surface)
    val appartments by viewModel.appartments.observeAsState(emptyList())

//

    LaunchedEffect(true) {
//        delay(LOAD_TIMEOUT)
        viewModel.initialize()
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onAddClick(openScreen) },
//                shape = MaterialTheme.shapes.large.copy(CornerSize(percent = 50)),
//                backgroundColor = MaterialTheme.colors.primary,
                backgroundColor = Color(0xFF3A4C2B),
                contentColor = MaterialTheme.colors.onPrimary,
                modifier = modifier.padding(16.dp)
            ) {
                //                Icon(Icons.Filled.Add, "Add")
                Image(
                    painter = painterResource(id = AppIcon.ic_stat_name),
                    contentDescription = stringResource(id = AppText.add_appartment)
                )
            }
        }
    ) {
//        var expanded by rememberSaveable { mutableStateOf(false) }
//        val appartments by viewModel.appartments.observeAsState(emptyList())
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            AppartmentTopBar(exitApp = { viewModel.onExitAppClick(popUpScreen) },
                openScreen = { viewModel.onSettingsClick(openScreen) }
            )

//            ActionToolbar(
//                title = AppText.list_appartment,
//                modifier = Modifier.toolbarActions(),
//                endActionIcon = AppIcon.ic_settings,
//                isFullScreen = true,
//                endAction = { viewModel.onSettingsClick(openScreen) }
//            )
            Spacer(modifier = Modifier.smallSpacer())
            if (appartments.isEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(PaddingValues(20.dp))
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = AppText.add_appartment_info),
                            modifier = Modifier.padding(PaddingValues(8.dp)),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h5
                        )

                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = AppText.no_data_subtitle),
                            modifier = Modifier.padding(PaddingValues(top = 20.dp)),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h6
                        )
                    }
                }

            } else {
                LazyColumn(
                    modifier = modifier.padding(vertical = 8.dp)

                ) {
                    items(
                        items = appartments,
                        key = { it.addressId },
                        itemContent = {
                            AppartmentListItem(
                                appartment = it,
                                popUpScreean = {},
                                onAppartmentChange = {
                                    viewModel.getFlatFromCache(it.addressId, openScreen)
                                })
                        })

                }

//            }
            }
        }
    }
}



