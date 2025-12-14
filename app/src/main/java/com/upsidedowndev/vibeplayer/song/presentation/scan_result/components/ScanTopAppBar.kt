@file:OptIn(ExperimentalMaterial3Api::class)

package com.upsidedowndev.vibeplayer.song.presentation.scan_result.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upsidedowndev.vibeplayer.R
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Scan
import com.upsidedowndev.vibeplayer.song.util.IconContainer
import com.upsidedowndev.vibeplayer.util.hostgroteskFamily

@Composable
fun ScanTopAppBar(
    onScanClick: () -> Unit,
    modifier: Modifier = Modifier
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
        actions = {
            IconContainer(
                onClick = onScanClick,
                modifier = Modifier
                    .padding(10.dp),
                icon = Icons.Filled.Scan,
                contentDescription = "Scan"
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.tertiary

        )
    )
}