package com.ykis.ykispam.ui.screens.service.list

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class TotalServiceDebt(
    val name : String,
    val icon : ImageVector,
    val color : Color,
    val debt : Float
)