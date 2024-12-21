/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.ykis.mob.core.composable

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.ykis.mob.R
import com.ykis.mob.R.drawable as AppIcon
import com.ykis.mob.R.string as AppText

@Composable
fun BasicField(
    @StringRes label: Int,
    @StringRes placeholder: Int,
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        singleLine = true,
//        colors = TextFieldColors(Color(0xFFFFB945)),
        modifier = Modifier
            .widthIn(0.dp, 480.dp),
        label = {
            Text(
                text = stringResource(id = label),
//                color = Color(0xFFFFB945)
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(stringResource(id = placeholder)) }
    )
}

@Composable
fun EmailField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
//            .padding(16.dp, 4.dp, 16.dp, 4.dp)
        ,
        label = {
            Text(
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(id = R.string.email)
            )
        },
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(stringResource(AppText.email_placeholder)) },
        leadingIcon = { Icon(imageVector = Icons.Filled.AlternateEmail, contentDescription = "Email") }
    )
}
@Composable
fun PhoneField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        label = {
            Text(
                style = MaterialTheme.typography.bodyLarge,
                text = stringResource(id = R.string.phone)
            )
        },
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(stringResource(AppText.email_placeholder)) },
        leadingIcon = { Icon(imageVector = Icons.Filled.Phone, contentDescription = "Phone") }
    )
}

@Composable
fun PasswordField(value: String, onNewValue: (String) -> Unit, modifier: Modifier = Modifier) {
    PasswordField(value, AppText.password, onNewValue, modifier)
}

@Composable
fun RepeatPasswordField(
    value: String,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    PasswordField(value, AppText.repeat_password, onNewValue, modifier)
}

@Composable
private fun PasswordField(
    value: String,
    @StringRes placeholder: Int,
    onNewValue: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }

    val icon =
        if (isVisible) painterResource(AppIcon.ic_visibility_on)
        else painterResource(AppIcon.ic_visibility_off)

    val visualTransformation =
        if (isVisible) VisualTransformation.None else PasswordVisualTransformation()

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth() ,
        label = {
            Text(
                text = stringResource(id = placeholder)
            )
        },
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { Text(text = stringResource(placeholder)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock") },
        trailingIcon = {
            IconButton(onClick = { isVisible = !isVisible }) {
                Icon(painter = icon, contentDescription = "Visibility")
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = visualTransformation
    )
}

@Composable
fun LabelField(
    value: String,
    @StringRes label: Int,
    @StringRes placeholder: Int,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        singleLine = false,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 4.dp, 16.dp, 4.dp),
        readOnly = true,
        maxLines = 2,

        label = {
            Text(
                text = stringResource(id = label)
            )
        },
        value = value,
        onValueChange = {},
        placeholder = { Text(stringResource(id = placeholder)) }
    )
}

@Composable
fun NumberField(
    value: String,
    onNewValue: (String) -> Unit,
    @StringRes label: Int,
    isInteger: Boolean
) {
    OutlinedTextField(
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(),
        label = {
            Text(
                text = stringResource(id = label)
            )
        },
        value = value,
        onValueChange = { onNewValue(it) },
        placeholder = { if (isInteger) {
            Text(stringResource(id = R.string.number_int_placeholder))
        }else {
            Text(stringResource(id = R.string.number_double_placeholder))
        }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    )
}
