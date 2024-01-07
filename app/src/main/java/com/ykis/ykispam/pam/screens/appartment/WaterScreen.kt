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

package com.ykis.ykispam.pam.screens.appartment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykis.ykispam.R
import com.ykis.ykispam.pam.screens.appartment.viewmodels.ApartmentViewModel

@Composable
fun WaterScreen(
    popUpScreen:()->Unit,
    addressId: String,
    modifier: Modifier = Modifier,
    viewModel: ApartmentViewModel = hiltViewModel()
) {

    LaunchedEffect(viewModel) {
//        viewModel.getFlatFromCache(addressId.toInt())
    }
    val apartment by viewModel.apartment.observeAsState()
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        apartment?.let {
            it.address?.let { address ->
                Text(
                    text = address,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(start = 12.dp, top = 16.dp, end = 12.dp, bottom = 8.dp)

        ) {
            Card(
                modifier = Modifier,
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(10.dp),
                shape = RoundedCornerShape(5.dp)
            ) {

                Row(
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            top = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        )

                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(//Ф.И.О.
                            modifier = Modifier
                                .padding(
                                    start = 0.dp,
                                    top = 4.dp,
                                    end = 0.dp,
                                    bottom = 4.dp
                                )

                        ) {
                            Text(
                                text = stringResource(R.string.employer_text),
                                style = MaterialTheme.typography.titleSmall,
                                color = Color(0xFFFFB945)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(
                                    start = 8.dp,
                                    top = 0.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically,
//                                        horizontalArrangement = Arrangement.Center


                        ) {
                            apartment?.let {
                                Text(
                                    text = it.nanim,
                                    //                                            color = MaterialTheme.colors.primary,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(start = 8.dp),
                        //                                            textAlign = TextAlign.Center
                                )
                            }

                        }
                        Row(
//Площадь заголовок
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(
                                    start = 4.dp,
                                    top = 8.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically,

                            ) {
                            Text(
                                text = stringResource(R.string.area_flat),
                                style = MaterialTheme.typography.titleSmall,
                                color = Color(0xFFFFB945)

                            )
                        }
                        Row(//Площадь данные
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(
                                    start = 4.dp,
                                    top = 0.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center

                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(0.25f)
                                    .padding(start = 8.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start


                            ) {

                                Text(
                                    text = stringResource(R.string.area_full),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.primary,

                                    )
                                Text(
                                    text = apartment?.areaFull.toString(),
//                                                color = MaterialTheme.colors.primary,
                                    style = MaterialTheme.typography.bodyMedium,

                                    )
                            }
                            Column(
                                modifier = Modifier.weight(0.25f),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start

                            ) {

                                Text(
                                    text = stringResource(R.string.area_life),
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.primary,

                                    )
                                Text(
                                    text = apartment?.areaLife.toString(),
//                                                color = MaterialTheme.colors.primary,
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                            Column(
                                modifier = Modifier.weight(0.25f),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start

                            ) {

                                Text(
                                    text = stringResource(R.string.area_extra),
                                    color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.titleSmall
                                )
                                Text(
                                    text = apartment?.areaDop.toString(),
//                                                color = MaterialTheme.colors.primary,
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                            Column(
                                modifier = Modifier.weight(0.25f),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start

                            ) {

                                Text(
                                    text = stringResource(R.string.area_otopl),
                                    color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.titleSmall
                                )
                                Text(
                                    text = apartment?.areaOtopl.toString(),
//                                                color = MaterialTheme.colors.primary,
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                        }
                        Row(//Состав семьи заголовок
                            modifier = Modifier
                                .padding(
                                    start = 2.dp,
                                    top = 8.dp,
                                    end = 0.dp,
                                    bottom = 4.dp
                                )

                        ) {
                            Text(
                                text = stringResource(R.string.compound_text),
                                style = MaterialTheme.typography.titleSmall,
                                color = Color(0xFFFFB945)

                            )
                        }
                        Row(//Состав семьи данные
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(
                                    start = 4.dp,
                                    top = 0.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center

                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(0.33f)
                                    .padding(start = 8.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start

                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(1f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(
                                        text = stringResource(R.string.tenant_text),
                                        modifier = Modifier.padding(end = 4.dp),
                                        color = MaterialTheme.colorScheme.primary,
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                    Text(
                                        text = apartment?.tenant.toString(),
//                                                color = MaterialTheme.colors.primary,
                                        style = MaterialTheme.typography.bodyMedium,
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            }
                            Column(
                                modifier = Modifier.weight(0.33f),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start

                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(1f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(
                                        text = stringResource(R.string.podnan_text),
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(end = 4.dp),
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                    Text(
                                        text = apartment?.podnan.toString(),
//                                                color = MaterialTheme.colors.primary,
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                }
                            }
                            Column(
                                modifier = Modifier.weight(0.33f),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(1f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(
                                        text = stringResource(R.string.absent_text),
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(end = 4.dp),
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                    Text(
                                        text = apartment?.absent.toString(),
//                                                color = MaterialTheme.colors.primary,
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                }
                            }

                        }
                        Row(//БТИ заголовок
                            modifier = Modifier
                                .padding(
                                    start = 2.dp,
                                    top = 8.dp,
                                    end = 0.dp,
                                    bottom = 4.dp
                                )

                        ) {
                            Text(
                                text = stringResource(R.string.data_bti),
                                style = MaterialTheme.typography.titleSmall,
                                color = Color(0xFFFFB945)

                            )


                        }
                        Row(//БТИ данные 1 ряд
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(
                                    start = 4.dp,
                                    top = 0.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center

                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(0.33f)
                                    .padding(start = 8.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start

                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(1f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {

                                    Text(
                                        text = stringResource(R.string.rooms),
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(end = 4.dp),
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                    Text(
                                        text = apartment?.room.toString(),
//                                                color = MaterialTheme.colors.primary,
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                }
                            }
                            Column(
                                modifier = Modifier.weight(0.33f),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start

                            ) {
                                Row(//БТИ данные 2 ряд
                                    modifier = Modifier
                                        .fillMaxWidth(1f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(
                                        text = stringResource(R.string.private_text),
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(end = 4.dp),
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                    Text(
                                        text = if (apartment?.privat.toString() == "1") {
                                            stringResource(R.string.private_ok)
                                        } else {
                                            stringResource(R.string.private_no)
                                        },
//                                                color = MaterialTheme.colors.primary,
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                }
                            }
                            Column(
                                modifier = Modifier.weight(0.33f),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start
                            ) {
                                Row(//БТИ данные 2 ряд
                                    modifier = Modifier
                                        .fillMaxWidth(1f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(
                                        text = stringResource(R.string.elevator_text),
                                        modifier = Modifier.padding(end = 4.dp),
                                        color = MaterialTheme.colorScheme.primary,
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                    Text(
                                        text = if (apartment?.lift.toString() == "1") {
                                            stringResource(R.string.private_ok)
                                        } else {
                                            stringResource(R.string.private_no)
                                        },
//                                                color = MaterialTheme.colors.primary,
                                        style = MaterialTheme.typography.bodyMedium,
                                    )
                                }
                            }
                        }
                        Row(//БТИ данные 2 ряд
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(
                                    start = 4.dp,
                                    top = 0.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center

                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 8.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start


                            ) {
                                Row(//БТИ данные 2 ряд
                                    modifier = Modifier
                                        .fillMaxWidth(1f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {


                                    Text(
                                        text = stringResource(R.string.order_text),
                                        color = MaterialTheme.colorScheme.primary,
                                        style = MaterialTheme.typography.titleSmall,
                                        modifier = Modifier.padding(end = 4.dp)
                                    )
                                    apartment?.let {
                                        Text(
                                            text = it.order,
                                //                                                    color = MaterialTheme.colors.primary,
                                            style = MaterialTheme.typography.bodyMedium,
                                        )
                                    }
                                }

                            }

                        }
                        Row(//БТИ данные 3 ряд
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(
                                    start = 4.dp,
                                    top = 0.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center

                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 8.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start


                            ) {
                                Row(//БТИ данные 2 ряд
                                    modifier = Modifier
                                        .fillMaxWidth(1f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {


                                    Text(
                                        text = stringResource(R.string.data_order),
                                        color = MaterialTheme.colorScheme.primary,
                                        style = MaterialTheme.typography.titleSmall,
                                        modifier = Modifier.padding(end = 4.dp)
                                    )
                                    apartment?.let {
                                        Text(
                                            text = it.dataOrder,
                                //                                                    color = MaterialTheme.colors.primary,
                                            style = MaterialTheme.typography.bodyMedium,
                                        )
                                    }
                                }
                            }

                        }
                        Row(//контактные данные заголовок
                            modifier = Modifier
                                .padding(
                                    start = 2.dp,
                                    top = 8.dp,
                                    end = 0.dp,
                                    bottom = 4.dp
                                )

                        ) {
                            Text(
                                text = stringResource(R.string.contacts),
                                style = MaterialTheme.typography.titleSmall,
                                color = Color(0xFFFFB945)

                            )


                        }

                        Row(//контактные данные 1 ряд
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(
                                    start = 4.dp,
                                    top = 0.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center

                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 8.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start


                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(1f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {


                                    Text(
                                        text = stringResource(R.string.phone_text),
                                        color = MaterialTheme.colorScheme.primary,
                                        style = MaterialTheme.typography.titleSmall,
                                        modifier = Modifier.padding(end = 4.dp)
                                    )
                                    apartment?.let {
                                        Text(
                                            text = it.phone,
                                //                                                    color = MaterialTheme.colors.primary,
                                            style = MaterialTheme.typography.bodyMedium,
                                        )
                                    }
                                }

                            }

                        }
                        Row(//контактные данные 1 ряд
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(
                                    start = 4.dp,
                                    top = 0.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center

                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 8.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.Start


                            ) {

                                Row(//контактные данные 2 ряд
                                    modifier = Modifier
                                        .fillMaxWidth(1f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {


                                    Text(
                                        text = stringResource(R.string.email_colon),
                                        color = MaterialTheme.colorScheme.primary,
                                        style = MaterialTheme.typography.titleSmall,
                                        modifier = Modifier.padding(end = 4.dp)
                                    )
                                    apartment?.let {
                                        Text(
                                            text = it.email,
                                //   color = MaterialTheme.colors.primary,
                                            style = MaterialTheme.typography.bodyMedium,
                                        )
                                    }
                                }

                            }

                        }

                    }
                }
            }
        }

    }

}






