package com.ykis.ykispam.firebase.screens.sign_up.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.R.color as AppColor
import com.ykis.ykispam.R.string as AppText


@Composable
fun AgreementChekBox(
    checked :Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier,

) {
    Card(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(CardDefaults.shape),
        backgroundColor = MaterialTheme.colors.background,

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                modifier = Modifier.padding(8.dp, 0.dp),
                colors = CheckboxDefaults.colors(checkedColor = colorResource(AppColor.white)
                )
            )
                if (checked) {
                    Text(
                        text = stringResource(AppText.agreement_check_checked),
                        style = MaterialTheme.typography.body2
                    )
                }else{
                    Text(
                        text = stringResource(AppText.agreement_check),
                        style = MaterialTheme.typography.body2
                    )
                }
            }

        }
    }