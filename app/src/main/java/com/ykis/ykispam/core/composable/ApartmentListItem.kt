package com.ykis.ykispam.core.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity

@Composable
fun ApartmentListItem(
    modifier: Modifier = Modifier,
    apartment : ApartmentEntity,
    onClick : (Int)->Unit = {}
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth()
            .padding(start=4.dp)
            .clickable {
                       onClick(apartment.addressId)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = null
        )
        Text(
            text = apartment.address
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewApartmentListItem() {
    ApartmentListItem(apartment = ApartmentEntity(address = "Хіміків 6/64"))
}