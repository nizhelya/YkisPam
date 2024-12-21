package com.ykis.mob.ui.screens.bti

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ykis.mob.R
import com.ykis.mob.core.ext.isTrue
import com.ykis.mob.domain.apartment.ApartmentEntity
import com.ykis.mob.ui.BaseUIState
import com.ykis.mob.ui.components.BaseCard
import com.ykis.mob.ui.components.ColumnLabelTextWithTextAndIcon
import com.ykis.mob.ui.components.LabelTextWithCheckBox
import com.ykis.mob.ui.components.LabelTextWithText
import com.ykis.mob.ui.screens.appartment.ApartmentViewModel
import com.ykis.mob.ui.theme.YkisPAMTheme


@Composable
fun BtiPanelContent(
    modifier: Modifier = Modifier,
    baseUIState: BaseUIState,
    viewModel: ApartmentViewModel
) {
    LaunchedEffect(key1 = baseUIState.addressId) {
        viewModel.initialContactState()
    }
    val contactUiState by viewModel.contactUIState.collectAsState()
    BtiContent(
        modifier = modifier,
        baseUIState = baseUIState,
        contactUiState = contactUiState,
        onEmailChange = viewModel::onEmailChange,
        onPhoneChange = viewModel::onPhoneChange,
        onUpdateBti = {
            baseUIState.uid?.let { viewModel.onUpdateBti(it) }
        },

        )
}

@Composable
fun BtiContent(
    modifier: Modifier = Modifier,
    baseUIState: BaseUIState,
    contactUiState: ContactUIState,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onUpdateBti: () -> Unit,
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BaseCard {
            ColumnLabelTextWithTextAndIcon(
                imageVector = Icons.Default.Person,
                labelText = stringResource(id = R.string.employer_text_colon),
                valueText = baseUIState.apartment.nanim
            )
        }


        BaseCard(
            label = stringResource(
                id = R.string.compound_text
            )
        ) {
            Row{
                LabelTextWithText(
                    modifier = modifier.weight(1f),
                    labelText = stringResource(R.string.tenant_text),
                    valueText = baseUIState.apartment.tenant.toString()
                )
                LabelTextWithText(
                    modifier = modifier.weight(1f),
                    labelText = stringResource(R.string.podnan_text),
                    valueText = baseUIState.apartment.podnan.toString()
                )
            }
            Row{
                LabelTextWithText(
                    modifier = modifier.weight(1f),
                    labelText = stringResource(id = R.string.absent_text),
                    valueText = baseUIState.apartment.absent.toString()
                )

            }

        }
        BaseCard(
            label = stringResource(
                id = R.string.area_flat
            )
        ) {
            Row{
                LabelTextWithText(
                    modifier = modifier.weight(1f),
                    labelText = stringResource(R.string.area_full),
                    valueText = baseUIState.apartment.areaFull.toString()
                )
                LabelTextWithText(
                    modifier = modifier.weight(1f),
                    labelText = stringResource(R.string.area_life),
                    valueText = baseUIState.apartment.areaLife.toString()
                )
            }
            Row{
                LabelTextWithText(
                    modifier = modifier.weight(1f),
                    labelText = stringResource(id = R.string.area_extra),
                    valueText = baseUIState.apartment.areaDop.toString()
                )
                LabelTextWithText(
                    modifier = modifier.weight(1f),
                    labelText = stringResource(id = R.string.area_otopl),
                    valueText = baseUIState.apartment.areaOtopl.toString()
                )
            }

        }
        BaseCard(
            label = stringResource(id = R.string.data_bti)
        ) {
            Row{
                LabelTextWithText(
                    modifier = modifier.weight(1f),
                    labelText =  stringResource(R.string.rooms_colon),
                    valueText = baseUIState.apartment.room.toString()
                )
                LabelTextWithCheckBox(
                    modifier = modifier.weight(1f),
                    labelText = stringResource(id = R.string.private_text_colon) ,
                    checked = baseUIState.apartment.privat.isTrue()
                )
                LabelTextWithCheckBox(
                    modifier = modifier.weight(1f),
                    labelText = stringResource(id = R.string.elevator_colon) ,
                    checked = baseUIState.apartment.lift.isTrue()
                )
            }

            LabelTextWithText(
                labelText = stringResource(id = R.string.order_text),
                valueText = baseUIState.apartment.order
            )
            LabelTextWithText(
                labelText = stringResource(id = R.string.date_orde_colon),
                valueText = baseUIState.apartment.dataOrder
            )
        }
        ContactsCard(
            baseUIState = baseUIState,
            phone = contactUiState.phone,
            email = contactUiState.email,
            onEmailChange = onEmailChange,
            onPhoneChange = onPhoneChange,
            onUpdateBti = onUpdateBti
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BtiContentPreview() {
    YkisPAMTheme {
        BtiContent(
            modifier = Modifier,
            baseUIState = BaseUIState(apartment = ApartmentEntity()),
            contactUiState = ContactUIState(
                addressId = 6314,
                address = "Гр.Десанту 21 кв.71",
                email = "nizelskiy.sergey@gmail.com"
            ),
            onEmailChange = { },
            onPhoneChange = { },
            onUpdateBti = {},
        )
    }
}