package com.ykis.ykispam.pam.screens.appartment

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ykis.ykispam.pam.domain.appartment.AppartmentEntity


@Composable
fun AppartmentDetail(appartment: AppartmentEntity) {
    var expanded by remember { mutableStateOf(false) }
//
//    Row(
//        modifier = Modifier
//            .padding(12.dp)
//            .animateContentSize(
//                animationSpec = spring(
//                    dampingRatio = Spring.DampingRatioMediumBouncy,
//                    stiffness = Spring.StiffnessLow
//                )
//            )
//    ) {
//        Column(
//            modifier = Modifier
//                .weight(1f)
//                .padding(12.dp)
//        ) {
//            Text(text = "Hello, ")
//            Text(
//                text = name, style = MaterialTheme.typography.body1.copy(
//                    fontWeight = FontWeight.ExtraBold
//                )
//            )
//            if (expanded) {
//                Text(
//                    text = ("Composem ipsum color sit lazy, " +
//                            "padding theme elit, sed do bouncy. ").repeat(4),
//                )
//            }
//        }
//        IconButton(onClick = { expanded = !expanded }) {
//            Icon(
//                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
//                contentDescription = if (expanded) {
//                    stringResource(R.string.show_less)
//                } else {
//                    stringResource(R.string.show_more)
//                }
//            )
//        }
//    }
}