package com.upsidedowndev.vibeplayer.song.presentation.song.scanMusic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.button.VibeButton
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Surface
import com.upsidedowndev.vibeplayer.song.presentation.component.loader.LoaderRadar
import com.upsidedowndev.vibeplayer.song.presentation.component.topBar.InnerTopBar
import com.upsidedowndev.vibeplayer.song.presentation.song.component.ScanFilter
import com.upsidedowndev.vibeplayer.song.presentation.song.models.ScanFilterState
import com.upsidedowndev.vibeplayer.song.presentation.song.models.SelectedFilter

@Composable
fun ScanMusicScreen(
    modifier: Modifier = Modifier,
    onScanClick: (ScanFilterState) -> Unit,
    onBackClick: () -> Unit,
) {
    var durationSelected by remember { mutableStateOf(SelectedFilter.NONE) }
    var sizeSelected by remember { mutableStateOf(SelectedFilter.NONE) }

    Scaffold(
        modifier = modifier,
        topBar = {
            InnerTopBar(
                onBackClick = onBackClick,
                title = "Scan Music"
            )
        },
        bottomBar = {},
        contentWindowInsets = WindowInsets.safeContent,
        containerColor = Surface
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LoaderRadar(
                size = 120.dp
            )
            Spacer(
                modifier = Modifier
                    .height(24.dp)
            )
            ScanFilter(
                selected = durationSelected,
                onClick = { durationSelected = it },
                filterTitle = "Ignore duration less than",
                optionA = "30s",
                optionB = "60s",
                modifier = Modifier
            )
            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )
            ScanFilter(
                selected = sizeSelected,
                onClick = { sizeSelected = it },
                filterTitle = "Ignore size less than",
                optionA = "100KB",
                optionB = "500KB",
                modifier = Modifier
            )
            Spacer(
                modifier = Modifier
                    .height(24.dp)
            )
            VibeButton(
                text = "Scan",
                onClick = {
                    onScanClick(
                        ScanFilterState(
                            ignoreDuration = durationSelected != SelectedFilter.NONE,
                            ignoreSize = sizeSelected != SelectedFilter.NONE
                        )
                    )
                },
                isEnabled = true,
                isPressed = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ScanMusicPreview() {
    ScanMusicScreen(
        onScanClick = {},
        onBackClick = {}
    )
}