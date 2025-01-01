package com.ykis.mob.ui.screens.auth.sign_up.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ykis.mob.R.string as AppText


@Composable
fun AgreementChekBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier,

    ) {
//    Card(
//        modifier = Modifier
//            .widthIn(0.dp, 480.dp)
//            .padding(4.dp),
//    ) {
        Row(
            modifier = modifier.padding(4.dp).clip(MaterialTheme.shapes.small).clickable {
                onCheckedChange(!checked)
            },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                 checked = checked,
                onCheckedChange = onCheckedChange,
            )
            Text(
                text = if (checked) {
                    stringResource(AppText.agreement_check_checked)
                } else {
                    stringResource(AppText.agreement_check)
                },
                style = MaterialTheme.typography.bodyLarge,
//                modifier = Modifier.padding(start = 2.dp, end = 16.dp),
            )

        }

//    }
}