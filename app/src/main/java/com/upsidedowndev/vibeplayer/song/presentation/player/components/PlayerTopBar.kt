@file:OptIn(ExperimentalMaterial3Api::class)

package com.upsidedowndev.vibeplayer.song.presentation.player.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Arrow_Left
import com.upsidedowndev.vibeplayer.song.util.IconContainer

@Composable
fun PlayerTopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = { },
        navigationIcon = {
            IconContainer(
                onClick = onBackClick,
                modifier = Modifier
                    .padding(10.dp),
                icon = Icons.Filled.Arrow_Left,
                contentDescription = "Scan"
            )
        }
    )
}

@Preview
@Composable
private fun PlayerTopBarPreview() {
    PlayerTopBar()
}