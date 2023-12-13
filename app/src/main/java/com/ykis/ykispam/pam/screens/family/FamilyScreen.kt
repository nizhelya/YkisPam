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

package com.ykis.ykispam.pam.screens.family

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.pam.domain.family.FamilyEntity
import com.ykis.ykispam.pam.screens.bti.BackUpTopBar
import com.ykis.ykispam.theme.YkisPAMTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FamilyScreen(
    modifier: Modifier = Modifier,
    popUpScreen: () -> Unit,
    openScreen: (String) -> Unit,
    viewModel: FamilyListViewModel = hiltViewModel(),
    addressId: String,
) {

    LaunchedEffect(viewModel) {
        viewModel.getFamily(addressId.toInt())
    }
    LaunchedEffect(viewModel) {
        viewModel.getBtiFromCache(addressId.toInt())
    }
    val contactUiState by viewModel.contactUiState
    val family by viewModel.family.observeAsState(emptyList())

    FamilyScreenContent(
        contactUiState = contactUiState,
        family = family,
    navigateBack = { viewModel.navigateBack(popUpScreen) }
//            onEmailChange = viewModel::onEmailChange,
//            onPhoneChange = viewModel::onPhoneChange,
//            onUpdateBti = {viewModel.onUpdateBti(openScreen)},
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterial3Api
@Composable
fun FamilyScreenContent(
    modifier: Modifier = Modifier,
    contactUiState: ApartmentEntity,
    family: List<FamilyEntity>,
    navigateBack: () -> Unit,
//    onEmailChange: (String) -> Unit,
//    onPhoneChange: (String) -> Unit,
//    onUpdateBti: () -> Unit,
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
        BackUpTopBar(contactUiState.address, navigateBack = { navigateBack() })
        /*
        Card(
            modifier = Modifier
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onTertiary
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
                        text = stringResource(id = R.string.employer_text),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f),

                        )


                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(8.dp),
                        imageVector = Icons.TwoTone.Person,
                        contentDescription = "Info",
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = apartment.nanim,
                        style = MaterialTheme.typography.titleMedium,
//                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(start = 8.dp, top = 4.dp)
                            .weight(1f),
                    )

                }
            }
        }
        Card(
            modifier = Modifier
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onTertiary
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
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f),

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
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary,

                                )
                            Text(
                                text = apartment.tenant.toString(),
                                style = MaterialTheme.typography.titleMedium,

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
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary,

                                )
                            Text(
                                text = apartment.podnan.toString(),
                                style = MaterialTheme.typography.titleMedium,

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
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary,

                                )
                            Text(
                                text = apartment.absent.toString(),
                                style = MaterialTheme.typography.titleMedium,

                                )
                        }
                    }


                }
            }
        }
        Card(
            modifier = Modifier
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onTertiary
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
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f),

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
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary,

                                )
                            Text(
                                text = apartment.areaFull.toString(),
                                style = MaterialTheme.typography.titleMedium,

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
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary,

                                )
                            Text(
                                text = apartment.areaLife.toString(),
                                style = MaterialTheme.typography.titleMedium,

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
                                text = stringResource(R.string.area_extra),
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary,

                                )
                            Text(
                                text = apartment.areaDop.toString(),
                                style = MaterialTheme.typography.titleMedium,

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
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary,

                                )
                            Text(
                                text = apartment.areaOtopl.toString(),
                                style = MaterialTheme.typography.titleMedium,

                                )
                        }
                    }

                }
            }
        }
        Card(
            modifier = Modifier
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onTertiary
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
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f),

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
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary,

                                )
                            Text(
                                text = apartment.room.toString(),
                                style = MaterialTheme.typography.titleMedium,

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
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary,

                                )
                            Text(
                                text = if (apartment.privat.toString() == "1") {
                                    stringResource(R.string.private_ok)
                                } else {
                                    stringResource(R.string.private_no)
                                },
                                style = MaterialTheme.typography.titleMedium,
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
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary,

                                )
                            Text(
                                text = if (apartment.lift.toString() == "1") {
                                    stringResource(R.string.private_ok)
                                } else {
                                    stringResource(R.string.private_no)
                                },
                                style = MaterialTheme.typography.titleMedium,
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
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary,

                                )
                            Text(
                                text = apartment.order.toString(),
                                style = MaterialTheme.typography.titleMedium,
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
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary,

                                )
                            Text(
                                text = apartment.dataOrder.toString(),
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
                    }

                }
            }
        }
        Card(
            modifier = Modifier
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onTertiary
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
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
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
                        Row(//площадь
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

//                            Text(
//                                text = apartment.phone.toString(),
//                                style = MaterialTheme.typography.titleMedium,
//                                )
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
                                    start = 0.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            EmailField(contactUiState.email, onEmailChange, modifier)

//                            Text(
//                                text = apartment.email.toString(),
//                                style = MaterialTheme.typography.titleMedium,
//                            )
                        }
                    }
                }
            }
        }
        */
    }
}

@Preview(showBackground = true)
@ExperimentalMaterial3Api
@Composable
fun FamilyScreenPreview() {
    YkisPAMTheme {
        FamilyScreenContent(
            contactUiState = ApartmentEntity(email = "example@email.com", phone = "+380931111111"),
            family = listOf(FamilyEntity()),
            navigateBack = { },
//            onEmailChange = {},
//            onPhoneChange = {},
//            onUpdateBti = {}
        )
    }
}






