package com.upsidedowndev.vibeplayer.core.presentation.designSystem.button

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upsidedowndev.vibeplayer.util.hostgroteskFamily

@Composable
fun FilterButton(
    ignoreText: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
//            .border(
//                width = 1.dp,
//                color = if (selected) Primary30.copy(0.7f)
//                else SecondaryText.copy(0.7f)
//                ,
//                shape = CircleShape
//            )
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            VibeRadioButton(
                selected = selected,
                onClick = onClick,
                isEnabled = true
            )
            Text(
                text = ignoreText,
                fontFamily = hostgroteskFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
//                color = PrimaryText
            )
        }

    }
}

@Preview
@Composable
private fun FilterButtonPreview() {
    val selected = remember { mutableStateOf(false) }
    FilterButton(
        ignoreText = "30s",
        selected = selected.value,
        onClick = { selected.value = !selected.value }
    )
}