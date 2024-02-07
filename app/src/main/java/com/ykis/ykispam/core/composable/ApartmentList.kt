package com.ykis.ykispam.core.composable

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity

@Composable
fun ApartmentList(
    apartmentList: List<ApartmentEntity>,
    onClick : (String) ->Unit
) {
    LazyColumn() {
        items(
            items = apartmentList,
            key = {
                it.addressId
            }
        ){
            apartment->
                ApartmentListItem(
                    apartment = apartment,
                    onClick = onClick
                )
        }
    }
}
