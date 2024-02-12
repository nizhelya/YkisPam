package com.ykis.ykispam.ui.navigation.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.ui.theme.extendedColor

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
            .background(color = MaterialTheme.colorScheme.surfaceContainer)
            .border(
                width = borderWidth,
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.extraSmall
            )
            .height(36.dp)
            .fillMaxWidth()
            .clickable {
                onClick(apartment.addressId)
            },
        verticalAlignment = Alignment.CenterVertically,
    ){
        Icon(
            modifier = Modifier.padding(start = 16.dp, end = 8.dp),
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
    ApartmentListItem(
        apartment = ApartmentEntity(
            address = "Хіміків 6/64",
            addressId = 23
        ),
        currentAddressId = 23
    )
}