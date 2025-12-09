@file:OptIn(ExperimentalMaterial3Api::class)

package com.upsidedowndev.vibeplayer.song.presentation.component.topBar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upsidedowndev.vibeplayer.R
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Accent
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Scan
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Surface
import com.upsidedowndev.vibeplayer.song.util.IconContainer
import com.upsidedowndev.vibeplayer.util.hostgroteskFamily

@Composable
fun MainTopBar(
    modifier: Modifier = Modifier,
    onScanClick: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    tint = Accent,
                    modifier = Modifier
                        .size(24.dp)
                )
                Spacer(
                    modifier = Modifier
                        .width(4.dp)
                )
                Text(
                    text = "VibePlayer",
                    fontFamily = hostgroteskFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                )
            }
        },
        navigationIcon = {

        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Surface,
            titleContentColor = Accent
        ),
        actions = {
            IconContainer(
                onClick = onScanClick,
                modifier = Modifier
                    .padding(10.dp),
                icon = Icons.Filled.Scan,
                contentDescription = "Scan"
            )
        }
    )
}

@Preview
@Composable
private fun MainTopBarPreview() {
    MainTopBar()
}