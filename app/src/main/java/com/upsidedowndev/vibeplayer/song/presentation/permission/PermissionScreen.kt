package com.upsidedowndev.vibeplayer.song.presentation.permission

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.upsidedowndev.vibeplayer.util.hostgroteskFamily

@Composable
fun PermissionRoot(
    onNavigateToSongScreen: () -> Unit,
    viewModel: PermissionViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            viewModel.onAction(PermissionAction.OnPermissionResult(isGranted))
        }
    )

    ObserveAsEvents(viewModel.eventChannel) { event ->
        when (event) {
            PermissionEvent.LaunchPermissionRequest -> {
                permissionLauncher.launch(
                    if (Build.VERSION.SDK_INT >= 33) {
                        Manifest.permission.READ_MEDIA_AUDIO
                    } else Manifest.permission.READ_EXTERNAL_STORAGE
                )
            }

            PermissionEvent.NavigateToSongScreen -> {
                onNavigateToSongScreen()
            }
        }
    }

    PermissionScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PermissionScreen(
    state: PermissionState,
    onAction: (PermissionAction) -> Unit,
) {
    Scaffold(
        topBar = {},
        contentWindowInsets = WindowInsets.safeContent,
        containerColor = Color(0xFFFFFF)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
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
                color = Color(0xFFFFFF)
            )
            Spacer(
                modifier = Modifier
                    .height(4.dp)
            )
            Text(
                text = "VibePlayer needs access to your music files to build\n" +
                        "your library and play songs",
                fontFamily = hostgroteskFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = Color(0xFFFFFF),
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

    val context = LocalContext.current
    if (state.showRationaleDialog) {
        AlertDialog(
            onDismissRequest = {
                onAction(PermissionAction.OnRationaleOkClicked)
            },
            confirmButton = {
                Text(
                    text = "Try Again",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                                    context as Activity,
                                    if (Build.VERSION.SDK_INT >= 33) Manifest.permission.READ_MEDIA_AUDIO
                                    else Manifest.permission.READ_EXTERNAL_STORAGE
                                )
                            ) {
                                val intent = Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", context.packageName, null)
                                )
                                context.startActivity(intent)
                            }
                            onAction(PermissionAction.OnRationaleTryAgainClicked)
                        }
                        .padding(16.dp)
                )
            },
            dismissButton = {
                Text(
                    text = "OK",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onAction(PermissionAction.OnRationaleOkClicked)
                        }
                        .padding(16.dp)
                )
            },
            title = {
                Text(text = "Permission Required")
            },
            text = {
                Text(
                    text = "VibePlayer needs access to your music files to " +
                            "function properly. Without this permission, the " +
                            "app cannot build your music library or play " +
                            "songs."
                )
            },
            modifier = Modifier
        )
    }

}


@Preview
@Composable
private fun PermissionScreenPreview() {
    VibePlayerTheme {
        PermissionScreen(
            state = PermissionState(),
            onAction = {}
        )
    }
}
