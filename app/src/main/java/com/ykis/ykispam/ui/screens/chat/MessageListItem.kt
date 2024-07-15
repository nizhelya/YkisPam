
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ykis.ykispam.ui.components.UserImage
import com.ykis.ykispam.ui.screens.chat.MessageEntity
import com.ykis.ykispam.ui.screens.chat.formatTime24H
import com.ykis.ykispam.ui.theme.YkisPAMTheme

@Composable
fun MessageListItem(
    modifier: Modifier = Modifier,
    uid: String,
    messageEntity: MessageEntity
) {
    val isFromMe = remember(uid, messageEntity) { uid == messageEntity.senderUid }
    Box(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = modifier.align(if (isFromMe) Alignment.CenterEnd else Alignment.CenterStart),
            verticalAlignment = Alignment.Bottom
        ) {
            if (!isFromMe) {
                UserImage(
                    modifier = modifier
                        .size(32.dp)
                        .offset(y = 4.dp),
                    photoUrl = messageEntity.senderLogoUrl.toString()
                )
            }
            Box(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clip(
                        RoundedCornerShape(
                        topStart = 24f,
                        topEnd = 24f,
                        bottomStart = if (isFromMe) 24f else 0f,
                        bottomEnd = if (isFromMe) 0f else 24f
                    )
                    )
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(8.dp)
            ) {
                Box {
                    Column {
                        if (!isFromMe) {
                            Text(
                                text = messageEntity.senderDisplayedName,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Text(
                            modifier = modifier.padding(end = 32.dp),
                            text = messageEntity.text,
                        )
                    }
                    Text(
                        modifier = modifier.width(28.dp).align(Alignment.BottomEnd),
                        text = formatTime24H(messageEntity.timestamp),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 10.sp
                        ),
                        maxLines = 1,
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMessageListItem() {
    YkisPAMTheme {
        MessageListItem(uid = "1", messageEntity = MessageEntity(
            text = "Привіт чувак! aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
            senderDisplayedName = "Кирило Блідний"
        )
        )
    }
}