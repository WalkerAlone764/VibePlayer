package com.upsidedowndev.vibeplayer.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.upsidedowndev.vibeplayer.song.presentation.permission.PermissionRoot
import com.upsidedowndev.vibeplayer.song.presentation.scan_result.ScanResultRoot

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier,
    startDestination: Routes = Routes.Permission
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
        entryProvider = { key ->
            when(key) {
                Routes.Permission -> {
                    NavEntry(key) {
                        PermissionRoot(
                            onPermissionGranted = {
                                backStack.remove(Routes.Permission)
                                backStack.add(Routes.ScanResult)
                            }
                        )
                    }
                }

                Routes.ScanResult -> {
                    NavEntry(key) {
                        ScanResultRoot()
                    }
                }

                else -> throw Exception("NavKey is invalid")
            }
        }
    )
}