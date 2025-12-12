package com.upsidedowndev.vibeplayer.song.presentation.vibePlayer

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.VibePlayerTheme
import com.upsidedowndev.vibeplayer.core.presentation.util.ObserveAsEvents
import com.upsidedowndev.vibeplayer.song.presentation.permission.PermissionDialog
import com.upsidedowndev.vibeplayer.song.presentation.permission.use_cases.ReadMediaAudioPermissionTextProvider

@Composable
fun VibePlayerRoot(
    viewModel: VibePlayerViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            viewModel.onAction(VibePlayerAction.OnPermissionResult(isGranted))
        }
    )

    ObserveAsEvents(viewModel.eventChannel) { event ->
        when (event) {
            VibePlayerEvent.LaunchPermissionRequest -> {
                permissionLauncher.launch(
                    if (Build.VERSION.SDK_INT >= 33) {
                        Manifest.permission.READ_MEDIA_AUDIO
                    } else Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }

            VibePlayerEvent.OpenSettings -> {
                Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", context.packageName, null)
                ).also { context.startActivity(it) }
            }
        }
    }

    VibePlayerScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun VibePlayerScreen(
    state: VibePlayerState,
    onAction: (VibePlayerAction) -> Unit,
) {
    val activity = LocalContext.current as Activity

    state.permissionDialogQueue
        .reversed()
        .forEach { permission ->
            PermissionDialog(
                permissionTextProvider = ReadMediaAudioPermissionTextProvider(),
                isPermanentlyDeclined = !ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    permission
                ),
                onDismiss = { onAction(VibePlayerAction.DismissPermissionDialog) },
                onOkClick = { onAction(VibePlayerAction.OnRationaleOkClicked) },
                onGoToAppSettingsClick = { activity.openAppSettings() }
            )
        }
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}


@Preview
@Composable
private fun Preview() {
    VibePlayerTheme {
        VibePlayerScreen(
            state = VibePlayerState(),
            onAction = {}
        )
    }
}
