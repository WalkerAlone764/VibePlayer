package com.upsidedowndev.vibeplayer.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.upsidedowndev.vibeplayer.song.presentation.permission.PermissionRoot
import com.upsidedowndev.vibeplayer.song.presentation.scanMusic.ScanMusicScreen
import com.upsidedowndev.vibeplayer.song.presentation.songScreen.SongsRoot

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    startDestination: Route = Route.PermissionScreen
) {
    val backStack = rememberNavBackStack(
        startDestination
    )

    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Route.PermissionScreen> {
                PermissionRoot(
                    onNavigateToSongScreen = {
                        backStack.remove(Route.PermissionScreen)
                        backStack.add(Route.SongScreen)
                    }
                )
            }
            entry<Route.SongScreen> {
                SongsRoot(
                    onNavigateToScanMusicScreen = {
                        backStack.add(Route.ScanMusicScreen)
                    },
                    onNavigateToPlayerScreen = {
                        backStack.add(Route.PlayerScreen)
                    }
                )
            }
            entry<Route.ScanMusicScreen> {
                ScanMusicScreen(
                    onScanClick = {

                    },
                    onBackClick = {
                        backStack.remove(Route.ScanMusicScreen)
                    }
                )
            }
        }
    )
}