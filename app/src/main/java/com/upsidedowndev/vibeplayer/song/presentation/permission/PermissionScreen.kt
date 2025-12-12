package com.upsidedowndev.vibeplayer.song.presentation.permission

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upsidedowndev.vibeplayer.R
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.button.VibeButton
import com.upsidedowndev.vibeplayer.util.hostgroteskFamily

@Composable
fun PermissionScreen(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets.safeDrawing,
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
            Text(
                text = "VibePlayer",
                fontFamily = hostgroteskFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 28.sp,
//                color = PrimaryText
            )
            Spacer(
                modifier = Modifier
                    .height(4.dp)
            )
            Text(
                text = "VibePlayer needs access to your music files to build\n" +
                        "your library and play songs",
                fontFamily = hostgroteskFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
//                color = SecondaryText,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
            VibeButton(
                text = "Allow Access",
                onClick = onClick,
                isEnabled = true,
                isPressed = false
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun PermissionScreenPreview() {
    PermissionScreen(
        onClick = {}
    )
}