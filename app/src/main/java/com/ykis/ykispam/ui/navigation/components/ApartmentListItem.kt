package com.ykis.ykispam.ui.navigation.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.domain.apartment.ApartmentEntity

@Composable
fun ApartmentListItem(
    modifier: Modifier = Modifier,
    apartment : ApartmentEntity,
    onClick : (Int)->Unit = {},
    currentAddressId :Int
) {
    val borderWidth by animateDpAsState(
        targetValue = if(apartment.addressId==currentAddressId) 3.dp else (-1).dp,
        animationSpec = spring(Spring.DampingRatioNoBouncy , Spring.StiffnessLow), label = ""

    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable {
                onClick(apartment.addressId)
            }
            .border(
                width = borderWidth,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                shape = MaterialTheme.shapes.extraSmall
            )
            .padding(vertical = 8.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
    ){
        Icon(
            modifier = Modifier.padding(horizontal = 8.dp),
            imageVector = Icons.Default.Home,
            contentDescription = null
        )
        Text(
            text = apartment.address
        )
    }
}

@Preview(showBackground = true, device = "id:Nexus One")
@Composable
private fun PreviewApartmentListItem() {
    ApartmentListItem(
        apartment = ApartmentEntity(
            address = "Хіміків 6/64",
            addressId = 23
        ),
        currentAddressId = 23
    )
}