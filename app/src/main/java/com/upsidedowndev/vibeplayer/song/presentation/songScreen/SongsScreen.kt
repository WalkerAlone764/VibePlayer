package com.upsidedowndev.vibeplayer.song.presentation.songScreen

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.upsidedowndev.vibeplayer.song.presentation.component.topBar.MainTopBar
import com.upsidedowndev.vibeplayer.song.presentation.songScreen.models.ScanningState
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.button.VibeButton
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.button.VibeFabButton
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.PrimaryText
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.SecondaryText
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Surface
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.VibePlayerTheme
import com.upsidedowndev.vibeplayer.core.presentation.util.ObserveAsEvents
import com.upsidedowndev.vibeplayer.song.data.audio.AudioFileRepositoryImpl
import com.upsidedowndev.vibeplayer.song.domain.audio.AudioFileRepository
import com.upsidedowndev.vibeplayer.song.domain.model.AudioMetadata
import com.upsidedowndev.vibeplayer.song.presentation.component.loader.LoaderRadar
import com.upsidedowndev.vibeplayer.song.presentation.component.songCard.SongCard
import com.upsidedowndev.vibeplayer.util.hostgroteskFamily
import kotlinx.coroutines.launch

// Add this class to your SongsScreen.kt file or preferably in SongsViewModel.kt

class SongsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SongsViewModel::class.java)) {
            val repository: AudioFileRepository =
                AudioFileRepositoryImpl(context.applicationContext)
            @Suppress("UNCHECKED_CAST")
            return SongsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


@Composable
fun SongsRoot(
    onNavigateToScanMusicScreen: () -> Unit,
    onNavigateToPlayerScreen:() -> Unit
) {
    val context = LocalContext.current
    val viewModel: SongsViewModel = viewModel(
        factory = SongsViewModelFactory(context)
    )

    val state by viewModel.state.collectAsStateWithLifecycle()

    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(viewModel.eventChannel) { event ->
        when (event) {
            SongsEvent.ScrollToTop -> {
                coroutineScope.launch {
                    lazyListState.animateScrollToItem(0)
                }
            }

            is SongsEvent.NavigateToScanMusicScreen -> {
                onNavigateToScanMusicScreen()
            }

            is SongsEvent.OnScanComplete -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Scan complete - ${state.listOfAudioFiles.size} songs found.",
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    LaunchedEffect(lazyListState.firstVisibleItemIndex) {
        viewModel.onAction(
            SongsAction.FirstVisibleItemIndexChanged(
                lazyListState.firstVisibleItemIndex
            )
        )
    }

    SongsScreen(
        state = state,
        lazyListState = lazyListState,
        snackbarHostState = snackbarHostState,
        onAction = viewModel::onAction
    )
}

@Composable
fun SongsScreen(
    state: SongsState,
    lazyListState: LazyListState,
    snackbarHostState: SnackbarHostState,
    onAction: (SongsAction) -> Unit,
) {
    Scaffold(
        modifier = Modifier,
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            MainTopBar(
                onScanClick = {
                    onAction(SongsAction.OnScanFilerClick)
                }
            )
        },
        floatingActionButton = {
            if (state.isFabVisible) {
                VibeFabButton(
                    onClick = {
                        onAction(SongsAction.OnFabClicked)
                    }
                )
            }
        },
        contentWindowInsets = WindowInsets.safeDrawing,
        containerColor = Surface
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            when (state.scanningState) {
                ScanningState.LOADING -> {
                    LoaderRadar(
                        size = 120.dp
                    )
                    Spacer(
                        modifier = Modifier
                            .height(20.dp)
                    )
                    Text(
                        text = "Scanning your device for music...",
                        fontFamily = hostgroteskFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = SecondaryText,
                        textAlign = TextAlign.Center
                    )
                }

                ScanningState.NOT_FOUND -> {
                    Text(
                        text = "No music found",
                        fontFamily = hostgroteskFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 28.sp,
                        color = PrimaryText
                    )
                    Spacer(
                        modifier = Modifier
                            .height(4.dp)
                    )
                    Text(
                        text = "Try scanning again or check your folders.",
                        fontFamily = hostgroteskFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = SecondaryText,
                        textAlign = TextAlign.Center
                    )
                    Spacer(
                        modifier = Modifier
                            .height(20.dp)
                    )
                    VibeButton(
                        text = "Scan Again",
                        onClick = {
                            onAction(SongsAction.OnScanAgainButtonClick)
                        },
                        isEnabled = true,
                        isPressed = false
                    )
                }

                ScanningState.FOUND -> {
                    LazyColumn(
                        state = lazyListState,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                    ) {
                        items(state.listOfAudioFiles, key = {it.id}) { songCard ->
                            SongCard(
                                song = AudioMetadata(
                                    id = songCard.id,
                                    title = songCard.title,
                                    artist = songCard.artist,
                                    durationMs = songCard.durationMs,
                                    uriString = songCard.uriString,
                                    image = songCard.image
                                )
                            )
                        }
                    }
                }
            }
        }

    }
}

@Preview
@Composable
private fun Preview() {
    VibePlayerTheme {
        SongsScreen(
            state = SongsState(),
            lazyListState = rememberLazyListState(),
            snackbarHostState = remember { SnackbarHostState() },
            onAction = {}
        )
    }
}