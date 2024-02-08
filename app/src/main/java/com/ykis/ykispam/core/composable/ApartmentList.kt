package com.ykis.ykispam.core.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity

@Composable
fun ApartmentList(
    modifier : Modifier = Modifier,
    currentAddressId:Int,
    apartmentList: List<ApartmentEntity>,
    onClick : (Int) ->Unit
) {
    LazyColumn(
        modifier = modifier.background(color = MaterialTheme.colorScheme.surfaceContainerHighest)
    ) {
        item{
            Spacer(modifier = Modifier.height(4.dp))
        }
        items(
            items = apartmentList,
            key = {
                it.addressId
            }
        ){
            apartment->
                ApartmentListItem(
                    apartment = apartment,
                    onClick = onClick,
                    currentAddressId = currentAddressId
                )
        }
        item{
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}
