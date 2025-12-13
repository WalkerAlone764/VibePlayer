package com.upsidedowndev.vibeplayer.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.upsidedowndev.vibeplayer.song.presentation.permission.PermissionRoot
import com.upsidedowndev.vibeplayer.song.presentation.songs.SongsRoot
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlin.collections.listOf

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.PermissionScreen::class, Route.PermissionScreen.serializer())
                    subclass(Route.SongScreen::class, Route.SongScreen.serializer())
                }
            }
        },
        Route.PermissionScreen
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
                SongsRoot()
            }
        }
    )

}