package com.ykis.ykispam.pam.screens.meter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MeterScreen() {
    Column(Modifier.fillMaxSize()) {
        Text(text = "It is MeterScreen", style = MaterialTheme.typography.titleLarge)
    }
}