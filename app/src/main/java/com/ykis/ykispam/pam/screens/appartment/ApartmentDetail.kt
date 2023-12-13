package com.ykis.ykispam.pam.screens.appartment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity

//
//@Composable
//fun ApartmentDetail(
//    apartment: ApartmentEntity,
//    isFullScreen: Boolean = true,
//    modifier: Modifier = Modifier.fillMaxSize(),
//    onBackPressed: () -> Unit = {}
//) {
//    LazyColumn(
//        modifier = modifier
//            .background(MaterialTheme.colorScheme.inverseOnSurface)
//            .padding(top = 16.dp)
//    ) {
//        item {
//            EmailDetailAppBar(apartment, isFullScreen) {
//                onBackPressed()
//            }
//        }
//        items(items = apartment.threads, key = { it.id }) { email ->
//            ReplyEmailThreadItem(email = email)
//        }
//    }
//}