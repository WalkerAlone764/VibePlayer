package com.upsidedowndev.vibeplayer.song.presentation.permission

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.upsidedowndev.vibeplayer.R
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.button.VibeButton
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.VibePlayerTheme
import com.upsidedowndev.vibeplayer.core.presentation.util.ObserveAsEvents
import com.upsidedowndev.vibeplayer.song.presentation.permission.use_cases.ReadMediaAudioPermissionTextProvider
import com.upsidedowndev.vibeplayer.song.presentation.vibePlayer.openAppSettings
import com.upsidedowndev.vibeplayer.util.hostgroteskFamily

@Composable
fun PermissionRoot(
    onPermissionGranted: () -> Unit,
    viewModel: PermissionViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            viewModel.onAction(PermissionAction.OnPermissionResult(isGranted))
        }
    )

    val activityResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        }

    val context = LocalContext.current
    val activity = LocalActivity.current

    ObserveAsEvents(viewModel.event) { event ->
        when (event) {
            PermissionEvent.LaunchPermissionRequest -> {
                permissionLauncher.launch(
                    if (Build.VERSION.SDK_INT >= 33) {
                        Manifest.permission.READ_MEDIA_AUDIO
                    } else Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }

            PermissionEvent.OpenSettings -> {
                Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", context.packageName, null)
                ).also {
//                    context.startActivity(it)
                    activityResult.launch(it)
                }
            }

            PermissionEvent.OnPermissionGranted -> onPermissionGranted()
        }
    }

    state.permissionDialogQueue
        .reversed()
        .forEach { permission ->
            PermissionDialog(
                permissionTextProvider = ReadMediaAudioPermissionTextProvider(),
                isPermanentlyDeclined = !ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    permission
                ),
                onDismiss = { viewModel.onAction(PermissionAction.DismissPermissionDialog) },
                onOkClick = { viewModel.onAction(PermissionAction.OnRationaleOkClicked) },
                onGoToAppSettingsClick = { activity?.openAppSettings() }
            )
        }

    PermissionScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun PermissionScreen(
    state: PermissionState,
    onAction: (PermissionAction) -> Unit,
) {

    Scaffold(
        modifier = Modifier,
        contentWindowInsets = WindowInsets.safeDrawing,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(
                    horizontal = 28.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
            Text(
                text = "VibePlayer",
                fontFamily = hostgroteskFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 28.sp,
            )
            Spacer(
                modifier = Modifier
                    .height(4.dp)
            )
            Text(
                text = "VibePlayer needs access to your music files to build your library and play songs",
                fontFamily = hostgroteskFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )
            VibeButton(
                text = "Allow Access",
                onClick = {
                    onAction(PermissionAction.GrantPermissionClicked)
                },
                isEnabled = true,
                isPressed = false
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    VibePlayerTheme {
        PermissionScreen(
            state = PermissionState(),
            onAction = {}
        )
    }
}