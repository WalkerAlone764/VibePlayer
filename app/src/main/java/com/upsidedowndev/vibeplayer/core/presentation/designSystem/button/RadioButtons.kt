package com.upsidedowndev.vibeplayer.core.presentation.designSystem.button

import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.DisabledText
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Hover
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Primary
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.SecondaryText

@Composable
fun VibeRadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true
) {
    RadioButton(
        selected = selected,
        onClick = onClick,
        enabled = isEnabled,
        colors = RadioButtonDefaults.colors(
            selectedColor = if (isEnabled) Primary
            else Hover,
            unselectedColor = if (isEnabled) SecondaryText
            else DisabledText
        ),
        modifier = modifier
    )
}

@Preview
@Composable
private fun VibeRadioButtonPreview() {
    val selected = remember { mutableStateOf(false) }
    VibeRadioButton(
        selected = selected.value,
        onClick = { selected.value = !selected.value },
        isEnabled = true
    )
}