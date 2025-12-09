package com.upsidedowndev.vibeplayer.core.presentation.designSystem.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.DisabledText
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Hover
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Primary
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Primary30
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.PrimaryText
import com.upsidedowndev.vibeplayer.util.hostgroteskFamily

@Composable
fun VibeButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isPressed: Boolean
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .graphicsLayer {
                shadowElevation = 20.dp.toPx()
                ambientShadowColor = if (isEnabled && !isPressed) Primary
                else Color.Transparent
                spotShadowColor = if (isEnabled && !isPressed) Primary
                else Color.Transparent
            },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isEnabled) {
                if (isPressed) Primary30.copy(0.6f)
                else Primary
            } else Hover,
            contentColor = if (isEnabled) PrimaryText
            else DisabledText
        )
    ) {
        Text(
            text = text,
            fontFamily = hostgroteskFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(12.dp, 6.dp)
        )
    }
}

@Preview
@Composable
private fun VibeButtonPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        VibeButton(
            onClick = {},
            isEnabled = true,
            isPressed = false,
            text = "Button"
        )
    }
}
