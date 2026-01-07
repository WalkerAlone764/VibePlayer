
package com.upsidedowndev.vibeplayer.song.presentation.scan_result

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.upsidedowndev.vibeplayer.R
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.VibePlayerTheme
import com.upsidedowndev.vibeplayer.song.presentation.scan_result.components.ItemCard
import com.upsidedowndev.vibeplayer.song.presentation.scan_result.components.ScanTopAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScanResultRoot(
    navigateToPlayer: (path: String) -> Unit,
    viewModel: ScanResultViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ScanResultScreen(
        state = state,
        onAction = { action ->
            when(action) {
                is ScanResultAction.OnSelectSong -> {
                    navigateToPlayer(action.songUrl)
                }
            }
        }
    )
}

@Composable
fun ScanResultScreen(
    state: ScanResultState,
    onAction: (ScanResultAction) -> Unit,
) {

    Scaffold(
        topBar = {
            ScanTopAppBar(
            onScanClick = {}
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {

            val infiniteTransition = rememberInfiniteTransition()
            val animateRadar by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        2000,
                        0,
                        LinearEasing
                    ),
                )
            )
            AnimatedVisibility(
                modifier = Modifier
                    .fillMaxSize(),
                visible = state.isScanning
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.radar),
                        contentDescription = null,
                        modifier = Modifier
                            .graphicsLayer {
                                rotationZ = animateRadar
                            }

                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Scanning your device for music...",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (!state.isScanning) {
                Content(
                    state = state,
                    onAction = onAction
                )
            }
        }

    }
}

@Composable
private fun Content(
    state: ScanResultState,
    onAction: (ScanResultAction) -> Unit,
    modifier: Modifier = Modifier
) {

    
    LazyColumn(
        modifier = modifier
    ) {
        items(state.audios, key = { audio -> audio.filePath }) { audio ->
            ItemCard(
                audioFile = audio,
                onClick = {
                    onAction(ScanResultAction.OnSelectSong(audio.filePath))
                }
            )
        }
    }

}

@Preview
@Composable
private fun Preview() {
    VibePlayerTheme {
        ScanResultScreen(
            state = ScanResultState(),
            onAction = {}
        )
    }
}