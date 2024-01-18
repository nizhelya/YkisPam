package com.ykis.ykispam.pam.screens.service

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ServiceListScreen() {
    Column(Modifier.fillMaxSize()) {
        Text(text = "It is ServiceListScreen", style = MaterialTheme.typography.titleLarge)
    }
}