
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ykis.ykispam.ui.components.UserImage
import com.ykis.ykispam.ui.screens.chat.MessageEntity
import com.ykis.ykispam.ui.screens.chat.formatTime24H
import com.ykis.ykispam.ui.theme.YkisPAMTheme
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessageListItem(
    modifier: Modifier = Modifier,
    uid: String,
    messageEntity: MessageEntity,
    onLongClick : () -> Unit,
    onClick : () -> Unit
) {
    val isFromMe = remember(uid, messageEntity) { uid == messageEntity.senderUid }
    val shape = remember(isFromMe){
        RoundedCornerShape(
            topStart = 24f,
            topEnd = 24f,
            bottomStart = if (isFromMe) 24f else 0f,
            bottomEnd = if (isFromMe) 0f else 24f
        )
    }

    Box(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = modifier.align(if (isFromMe) Alignment.CenterEnd else Alignment.CenterStart),
            verticalAlignment = Alignment.Bottom
        ) {
            if(isFromMe){
                Spacer(modifier =modifier.width(48.dp))
            }
            if (!isFromMe) {
                UserImage(
                    modifier = modifier
                        .size(32.dp)
                        .offset(y = 4.dp),
                    photoUrl = messageEntity.senderLogoUrl.toString()
                )
            }
            Box(
                modifier = Modifier.animateContentSize()
                    .padding(start = 4.dp)
                    .clip(
                        shape
                    )
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .padding(2.dp)
                    .combinedClickable(
                        onLongClick = {
                            onLongClick()
                        },
                        onClick = {
                            if(messageEntity.imageUrl!=null){
                                onClick()
                            }
                        }
                    )
            ) {
                Box {
                    if(messageEntity.imageUrl!=null){
                        Column {
                            AsyncImage(
                                modifier = modifier.clip(shape).sizeIn(minWidth = 144.dp , minHeight = 24.dp),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .dispatcher(Dispatchers.IO)
                                    .memoryCacheKey(messageEntity.imageUrl)
                                    .diskCacheKey(messageEntity.imageUrl)
                                    .data(messageEntity.imageUrl)
                                    .diskCachePolicy(CachePolicy.ENABLED)
                                    .memoryCachePolicy(CachePolicy.ENABLED)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                            )
                            if(messageEntity.text.isNotBlank()){
                                Text(
                                    modifier = modifier.padding(start = 4.dp ,end = 32.dp),
                                    text = messageEntity.text,
                                )
                            }
                        }
                    }
                    Column(
                        modifier = modifier.padding(6.dp)
                    ) {
                        if (!isFromMe) {
                            Text(
                                text = messageEntity.senderDisplayedName,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        if(messageEntity.senderAddress.isNotBlank()){
                            Text(
                                modifier = modifier
                                    .clip(RoundedCornerShape(32.dp))
                                    .background(
                                        if (messageEntity.imageUrl != null) {
                                            MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                                        } else Color.Transparent
                                    )
                                    .padding(
                                        vertical = 2.dp,
                                        horizontal = if (messageEntity.imageUrl != null) 4.dp else 0.dp
                                    ),
                                text = messageEntity.senderAddress,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        if(messageEntity.imageUrl==null) {
                            Text(
                                modifier = modifier.padding(end = 32.dp),
                                text = messageEntity.text,
                            )
                        }
                    }
                    Text(
                        modifier = modifier
                            .width(32.dp)
                            .clip(RoundedCornerShape(32.dp))
                            .align(Alignment.BottomEnd)
                            .background(
                                if (messageEntity.imageUrl != null  && messageEntity.text.isBlank()) {
                                    MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                                } else Color.Transparent
                            )
                            .padding(vertical = 2.dp),
                        text = formatTime24H(messageEntity.timestamp),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = 10.sp
                        ),
                        maxLines = 1,
                        textAlign = TextAlign.Center
                    )
                }
            }
            if(!isFromMe){
                Spacer(modifier =modifier.width(48.dp))
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
            senderDisplayedName = "Кирило Блідний",
            senderAddress = "Миру 28/1"
        ),
            onLongClick = {},
            onClick = {}
        )
    }
}