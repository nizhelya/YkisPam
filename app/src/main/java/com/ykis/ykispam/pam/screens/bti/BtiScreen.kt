/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ykis.ykispam.pam.screens.bti

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykis.ykispam.R
import com.ykis.ykispam.core.composable.EmailField
import com.ykis.ykispam.core.composable.ImageButton
import com.ykis.ykispam.core.composable.PhoneField
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.pam.screens.appartment.appbars.AddAppBar
import com.ykis.ykispam.pam.screens.appartment.viewmodels.BtiViewModel
import com.ykis.ykispam.theme.YkisPAMTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BtiScreen(
    popUpScreen: () -> Unit ={},
    openScreen: (String) -> Unit = {_ -> },
    viewModel: BtiViewModel = hiltViewModel(),
    addressId: String,
    address: String

) {
    LaunchedEffect(viewModel) {
        viewModel.getBtiFromCache(addressId.toInt())
    }
    val apartment by viewModel.apartment.observeAsState()
    val contactUiState by viewModel.contactUiState

    apartment?.let {
        BtiScreenContent(
            contactUiState = contactUiState,
            apartment = it,
            address = address,
            onBackPressed = { viewModel.navigateBack(popUpScreen) },
            onEmailChange = viewModel::onEmailChange,
            onPhoneChange = viewModel::onPhoneChange,
            onUpdateBti = { viewModel.onUpdateBti() },

        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterial3Api
@Composable
fun BtiScreenContent(
    modifier: Modifier = Modifier,
    contactUiState: ContactUIState,
    apartment: ApartmentEntity,
    address: String,
    onBackPressed: () -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onUpdateBti: () -> Unit,
) {


    Column(
        modifier = Modifier
            .padding(4.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val keyboard = LocalSoftwareKeyboardController.current
        AddAppBar(
            modifier,
            stringResource(id = R.string.bti),
            address,
            onBackPressed = { onBackPressed() })

        Card(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, bottom = 4.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.employer_text),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline

                    )


                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 4.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(end = 4.dp),

                        imageVector = Icons.TwoTone.Person,
                        contentDescription = "Info",
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = apartment.nanim,
                        style = MaterialTheme.typography.bodyLarge

                    )

                }
            }
        }

        Card(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.compound_text),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier
                            .padding(4.dp)

                    )

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(0.5f),
                    ) {
                        Row(//площадь
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                    ),
                                text = stringResource(R.string.tenant_text),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                            Text(
                                text = apartment.tenant.toString(),
                                style = MaterialTheme.typography.bodyLarge,

                                )
                        }
                    }
                    Column(
                        modifier = Modifier.weight(0.5f)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                    ),
                                text = stringResource(R.string.podnan_text),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                            Text(
                                text = apartment.podnan.toString(),
                                style = MaterialTheme.typography.bodyLarge,

                                )
                        }
                    }

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(0.5f),
                    ) {
                        Row(//площадь
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                    ),
                                text = stringResource(R.string.absent_text),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                            Text(
                                text = apartment.absent.toString(),
                                style = MaterialTheme.typography.bodyLarge,

                                )
                        }
                    }


                }
            }
        }

        Card(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.area_flat),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier
                            .padding(4.dp)

                    )

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(0.5f),
                    ) {
                        Row(//площадь
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                    ),
                                text = stringResource(R.string.area_full),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                            Text(
                                text = apartment.areaFull.toString(),
                                style = MaterialTheme.typography.bodyLarge,

                                )
                        }
                    }
                    Column(
                        modifier = Modifier.weight(0.5f)
                    ) {
                        Row(//Ф.И.О.
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                    ),
                                text = stringResource(R.string.area_life),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                            Text(
                                text = apartment.areaLife.toString(),
                                style = MaterialTheme.typography.bodyLarge,

                                )
                        }
                    }

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(0.5f),
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                    ),
                                text = stringResource(R.string.area_extra),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                            Text(
                                text = apartment.areaDop.toString(),
                                style = MaterialTheme.typography.bodyLarge,

                                )
                        }
                    }
                    Column(
                        modifier = Modifier.weight(0.5f)
                    ) {
                        Row(//Ф.И.О.
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                    ),
                                text = stringResource(R.string.area_otopl),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                            Text(
                                text = apartment.areaOtopl.toString(),
                                style = MaterialTheme.typography.bodyLarge,

                                )
                        }
                    }

                }
            }
        }
        Card(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.data_bti),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier
                            .padding(4.dp)

                    )

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(0.33f),
                    ) {
                        Row(//площадь
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                    ),
                                text = stringResource(R.string.rooms),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                            Text(
                                text = apartment.room.toString(),
                                style = MaterialTheme.typography.bodyLarge,

                                )
                        }
                    }
                    Column(
                        modifier = Modifier.weight(0.33f)
                    ) {
                        Row(//Ф.И.О.
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                    ),
                                text = stringResource(R.string.private_text),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                            Text(
                                text = if (apartment.privat.toString() == "1") {
                                    stringResource(R.string.private_ok)
                                } else {
                                    stringResource(R.string.private_no)
                                },
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.weight(0.33f)
                    ) {
                        Row(//Ф.И.О.
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                    ),
                                text = stringResource(R.string.elevator_text),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                            Text(
                                text = if (apartment.lift.toString() == "1") {
                                    stringResource(R.string.private_ok)
                                } else {
                                    stringResource(R.string.private_no)
                                },
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                    ) {
                        Row(//площадь
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                    ),
                                text = stringResource(R.string.order_text),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                            Text(
                                text = apartment.order,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                    ) {
                        Row(//площадь
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                    ),
                                text = stringResource(R.string.data_order),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = apartment.dataOrder,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }

                }
            }
        }
        Card(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.contacts),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier
                            .padding(4.dp)
                            .weight(1f),

                        )
                    ImageButton(
                        R.drawable.ic_check,
                        modifier = Modifier
                    )
                    {
                        keyboard?.hide()
                        onUpdateBti()
                    }

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(
                                    start = 0.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            PhoneField(contactUiState.phone, onPhoneChange, modifier)

                        }
                    }


                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(
                                    start = 0.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            EmailField(contactUiState.email, onEmailChange, modifier)

                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@ExperimentalMaterial3Api
@Composable
fun BtiScreenPreview() {
    YkisPAMTheme {
        BtiScreenContent(
            contactUiState = ContactUIState(email = "example@email.com", phone = "+380931111111"),
            apartment = ApartmentEntity(),
            address = "Гр.Десанту 21 кв.71",
            onBackPressed = { },
            onEmailChange = {},
            onPhoneChange = {},
            onUpdateBti = {},

        )
    }
}






