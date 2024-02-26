package com.ykis.ykispam.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ykis.ykispam.R

@Composable
fun EmptyListState(modifier: Modifier = Modifier,
                   useDarkTheme: Boolean = isSystemInDarkTheme(),
                   title : String,
                   subtitle : String = ""
) {
    val paintRes = if(useDarkTheme){
        R.drawable.ic_empty_box_dark
    }else{
        R.drawable.ic_empty_box_light
    }
    Column(modifier = modifier
        .fillMaxSize()
        .padding(bottom = 48.dp) , horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Image(painter = painterResource(id = paintRes), contentDescription = null)
        Text(text = title, style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp
        )
        )
        Text(text = subtitle)
    }
}