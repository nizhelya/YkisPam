package com.ykis.ykispam.pam.screens.appartment.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.R


@Composable
fun EmptyDetail(
    modifier: Modifier = Modifier,
    useDarkTheme: Boolean = isSystemInDarkTheme(),

    ) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(128.dp),
            painter = painterResource(id = R.drawable.ic_visibility_off),
            contentDescription = null
        )


    }
}