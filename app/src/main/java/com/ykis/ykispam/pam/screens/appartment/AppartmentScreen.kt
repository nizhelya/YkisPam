package com.ykis.ykispam.pam.screens.appartment

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykis.ykispam.R
import com.ykis.ykispam.core.ext.onSuccess
import com.ykis.ykispam.core.ext.onFailure


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AppartmentScreen(
    openScreen:  (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AppartmentViewModel = hiltViewModel()
) {
    viewModel.apply {

    }
//
//    var shouldShowOnboarding by remember { mutableStateOf(false) }
//
//    val appartments = viewModel.appartments.observeAsState()
//
//    // API call
//    LaunchedEffect(key1 = Unit) {
//        viewModel.getAppartmentsByUser(false)
//    }
//    Scaffold(
//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = {  },
//                backgroundColor = MaterialTheme.colors.primary,
//                contentColor = MaterialTheme.colors.onPrimary,
//                modifier = modifier.padding(16.dp)
//            ) {
//                Icon(Icons.Filled.Add, "Add")
//            }
//        }
//    )
//    {
//
//        // UI
//        LazyColumn(modifier = modifier) {
//            items(appartments) {
//                // List item composable
////                BookListItem(book = it)
//            }
//        }
//        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
//            if (shouldShowOnboarding) {
//                Text(
//                    text = stringResource(R.string.agreement_check_checked),
//                    style = MaterialTheme.typography.body2
//                )
//            } else {
//                AppartmentList(appartments,openScreen)
//            }        }
//    }

}



