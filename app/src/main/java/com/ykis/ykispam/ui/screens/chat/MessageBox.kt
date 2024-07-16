package com.ykis.ykispam.ui.screens.chat

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
internal fun ComposeMessageBox(
    onSent: () -> Unit,
    onImageSent : (Uri) -> Unit,
    text: String,
    onTextChanged: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface)
    val openDocumentContract = remember {
        ActivityResultContracts.OpenDocument()
    }
    val openDocumentLauncher = rememberLauncherForActivityResult(openDocumentContract) { uri ->
        uri?.let {
            Log.d("uri_test", it.toString())
            onImageSent(it)
        }
    }

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = {
            openDocumentLauncher.launch(arrayOf("image/*"))
        }) {
            Icon(
                imageVector = Icons.Default.AttachFile,
                contentDescription = null
            )
        }
        BasicTextField(
            value = text,
            onValueChange = { onTextChanged(it) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Default
            ),
            modifier = Modifier.weight(1f),
            textStyle = textStyle,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(size = 16.dp)
                        )
                        .padding(8.dp)
                ) {
                    if (text.isEmpty()) {
                        Text(
                            text = "Повідомлення",
                            style = textStyle,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                        )
                    }
                    innerTextField()
                }
            }
        )
        IconButton(
            onClick = {
                onSent()
                keyboardController?.hide()
            },
            enabled = text.isNotBlank()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Відправити"
            )
        }
    }
}