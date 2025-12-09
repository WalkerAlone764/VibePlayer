package com.upsidedowndev.vibeplayer.song.presentation.component.songCard

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upsidedowndev.vibeplayer.R
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.PrimaryText
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.SecondaryText
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Surface
import com.upsidedowndev.vibeplayer.util.hostgroteskFamily

data class Song(
    val songId: Int,
    val songImage: Int,
    val songTitle: String,
    val songArtist: String,
    val songDuration: String
)

@Composable
fun SongCard(
    song: Song,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        shape = RoundedCornerShape(20f),
        colors = CardDefaults.cardColors(
            containerColor = Surface
        )
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(song.songImage),
                contentDescription = null,
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
                    text = song.songTitle,
                    fontFamily = hostgroteskFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = PrimaryText
                )
                Spacer(
                    modifier = Modifier
                        .height(2.dp)
                )
                Text(
                    text = song.songArtist,
                    fontFamily = hostgroteskFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    color = SecondaryText
                )
            }
            Text(
                text = song.songDuration,
                fontFamily = hostgroteskFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = SecondaryText
            )
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = SecondaryText.copy(0.5f)
        )
    }
}

@Preview
@Composable
private fun SongCardPreview() {
    SongCard(
        song = Song(
            songId = 1,
            songImage = R.drawable.song_img,
            songTitle = "505",
            songArtist = "Arctic Monkeys",
            songDuration = "4:14"
        )
    )
}