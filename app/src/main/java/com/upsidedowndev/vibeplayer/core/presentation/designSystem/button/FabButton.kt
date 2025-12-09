package com.upsidedowndev.vibeplayer.core.presentation.designSystem.button

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Arrow_Up
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Primary
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Primary30
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.PrimaryText

@Composable
fun VibeFabButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPressed: Boolean = false
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
            .graphicsLayer{
                shadowElevation = 10.dp.toPx()
                ambientShadowColor = if (!isPressed) Primary
                else Color.Transparent
                spotShadowColor = if (!isPressed) Primary
                else Color.Transparent
            },
        containerColor = if (isPressed) Primary30.copy(0.6f)
        else Primary,
        contentColor = PrimaryText,
        shape = CircleShape
    ) {
        Icon(
            imageVector = Icons.Filled.Arrow_Up,
            contentDescription = "Scroll to top"
        )
    }
}

@Preview
@Composable
private fun VibeFabButtonPreview() {
    VibeFabButton(
        onClick = {},
        isPressed = false
    )
}