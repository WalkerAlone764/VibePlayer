package com.upsidedowndev.vibeplayer.song.presentation.player.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CustomSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    trackColor: Color = MaterialTheme.colorScheme.surfaceContainerHighest,
    progressColor: Color = Color.White,
    strokeWidth: Dp = 10.dp
) {
    var sliderWidth by remember { mutableStateOf(0f) }

    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    val newValue = (change.position.x / sliderWidth).coerceIn(0f, 1f)
                    onValueChange(newValue)
                }
            }
            .onSizeChanged {
                sliderWidth = it.width.toFloat()
            }
    ) {
        val yCenter = size.height / 2
        val barStart = 0f
        val barEnd = size.width

        // Track
        drawLine(
            color = trackColor,
            start = Offset(barStart, yCenter),
            end = Offset(barEnd, yCenter),
            strokeWidth = strokeWidth.toPx(),
            cap = StrokeCap.Round
        )

        // Progress
        val progressEnd = value * barEnd
        drawLine(
            color = progressColor,
            start = Offset(barStart, yCenter),
            end = Offset(progressEnd, yCenter),
            strokeWidth = strokeWidth.toPx(),
            cap = StrokeCap.Round
        )

        // Thumb
//        drawCircle(
//            color = thumbColor,
//            radius = (strokeWidth * 1.5f).toPx(),
//            center = Offset(progressEnd, yCenter)
//        )
    }
}

@Preview
@Composable
private fun CustomSliderPreview() {
    var sliderValue by remember { mutableStateOf(0.5f) }
    CustomSlider(
        value = sliderValue,
        onValueChange = { sliderValue = it },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(16.dp)
    )
}