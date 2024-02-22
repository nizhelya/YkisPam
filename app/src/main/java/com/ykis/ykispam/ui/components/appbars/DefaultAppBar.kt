package com.ykis.ykispam.ui.components.appbars

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ykis.ykispam.R
import com.ykis.ykispam.ui.navigation.NavigationType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultAppBar(
    modifier: Modifier = Modifier,
    title : String,
    onBackClick : () -> Unit,
    onDrawerClick : () -> Unit,
    canNavigateBack : Boolean,
    navigationType: NavigationType
) {
    TopAppBar(
        modifier = modifier.background(color = MaterialTheme.colorScheme.surfaceContainer),
        title = {
            Text(text = title)
        },
        navigationIcon = {
            if(!canNavigateBack && navigationType == NavigationType.BOTTOM_NAVIGATION) {
                IconButton(
                    onClick = onDrawerClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = stringResource(id = R.string.driver_menu),
                    )
                }
            }else if(canNavigateBack) {
                IconButton(
                    onClick = onBackClick,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button),
                    )
                }
            }
        }
    )
}