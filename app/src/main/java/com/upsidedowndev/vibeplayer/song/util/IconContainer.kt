package com.upsidedowndev.vibeplayer.song.util

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Hover
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.SecondaryText

@Composable
fun IconContainer(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String? = null,
    backgroundColor: Color = Hover.copy(1f),
    iconTint: Color = SecondaryText,
    iconSize: Dp = 16.dp
) {
    Box(
        modifier = modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconTint,
            modifier = Modifier
                .size(iconSize)
        )
    }
}

