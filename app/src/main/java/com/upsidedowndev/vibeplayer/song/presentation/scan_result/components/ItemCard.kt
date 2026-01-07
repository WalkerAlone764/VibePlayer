package com.upsidedowndev.vibeplayer.song.presentation.scan_result.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upsidedowndev.vibeplayer.R
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.VibePlayerTheme
import com.upsidedowndev.vibeplayer.song.domain.audio.AudioMetadata
import com.upsidedowndev.vibeplayer.util.hostgroteskFamily

@Composable
fun ItemCard(
    audioFile: AudioMetadata,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        shape = RoundedCornerShape(20f),
        colors = CardDefaults.cardColors(
//            containerColor = Surface
        )
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            audioFile.thumbnail?.let { thumbnail ->
                val bitmap = BitmapFactory.decodeByteArray(thumbnail, 0, thumbnail.size)
                if (bitmap != null) {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Song thumbnail",
                        modifier = Modifier
                            .size(64.dp),
                        contentScale = ContentScale.FillBounds
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.album_default_image),
                        contentDescription = "Song thumbnail",
                        modifier = Modifier
                            .size(64.dp),
                        contentScale = ContentScale.FillBounds
                    )
                }
            } ?: Image(
                painter = painterResource(id = R.drawable.album_default_image),
                contentDescription = "Song thumbnail",
                modifier = Modifier
                    .size(64.dp),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f)
            ) {
                Text(
                    text = audioFile.title ?: "",
                    fontFamily = hostgroteskFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(
                    modifier = Modifier
                        .height(2.dp)
                )
                Text(
                    text = audioFile.artist ?: "",
                    fontFamily = hostgroteskFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Text(
                text = audioFile.formattedDuration,
                fontFamily = hostgroteskFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.secondary.copy(0.5f)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    VibePlayerTheme {
        ItemCard(
            audioFile = AudioMetadata(
                title = "Saphire",
                artist = "Ed Shreen",
                duration = 200000,
                filePath = "",
                thumbnail = null
            ),
            onClick = {}
        )
    }
}