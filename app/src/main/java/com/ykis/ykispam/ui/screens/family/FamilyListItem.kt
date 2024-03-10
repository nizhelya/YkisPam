package com.ykis.ykispam.ui.screens.family

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.R
import com.ykis.ykispam.domain.family.FamilyEntity
import com.ykis.ykispam.ui.components.BaseCard
import com.ykis.ykispam.ui.components.LabelTextWithText
import com.ykis.ykispam.ui.theme.YkisPAMTheme

@Composable
fun FamilyListItem(
    modifier: Modifier = Modifier,
    person: FamilyEntity,

    ) {
    BaseCard {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
//                AsyncImage(
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data("photoUrl")
//                        .crossfade(true)
//                        .build(),
//                    contentDescription = null,
//                    error = painterResource(R.drawable.ic_account_circle),
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .clip(CircleShape)
//                        .width(48.dp)
//                        .height(48.dp)
//                )
//                Column(
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(horizontal = 12.dp, vertical = 4.dp),
//                    verticalArrangement = Arrangement.Center
//                ) {
                    Icon(
                        modifier = modifier.size(48.dp),
                        imageVector = Icons.Default.AccountCircle ,
                        contentDescription = null
                    )
                    Text(
                        modifier = modifier.padding(start = 8.dp),
                        text = "${person.surname} ${person.fistname} ${person.lastname}",
                        style = MaterialTheme.typography.titleMedium
                    )

//                    Text(
//                        text = person.lastname,
//                        style = MaterialTheme.typography.bodyLarge,
//                    )
//                }
            }

            /*Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.status),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = person.rodstvo,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }*/
//            if(person.rodstvo != """"""){
                LabelTextWithText(
                    labelText = stringResource(R.string.status_colon),
                    valueText = person.rodstvo
                )
//            }
            /*Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.born_text),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = person.born,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }*/
        LabelTextWithText(
            labelText = stringResource(R.string.born_text),
            valueText = person.born
        )/*
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.doc_text),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = person.document,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }*/
        LabelTextWithText(
            labelText = stringResource(R.string.doc_text),
            valueText = person.document
        )
           /* Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.inn_text),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = person.inn,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }*/
        LabelTextWithText(
            labelText = stringResource(R.string.inn_text),
            valueText = person.inn
        )
    }
}

@Preview
@Composable
private fun PreviewFamilyListItem() {
    YkisPAMTheme {
        FamilyListItem(
            person = FamilyEntity()
        )
    }
}