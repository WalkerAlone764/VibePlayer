package com.upsidedowndev.vibeplayer.app

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.upsidedowndev.vibeplayer.app.navigation.NavigationRoot
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.VibePlayerTheme
import com.upsidedowndev.vibeplayer.song.presentation.permission.PermissionRoot
import com.upsidedowndev.vibeplayer.song.presentation.player.MusicPlayerRoot

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(
                scrim = Color.TRANSPARENT,
            ),
            navigationBarStyle = SystemBarStyle.dark(
                scrim = Color.TRANSPARENT,
            )
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.window.isNavigationBarContrastEnforced = false
        }
        setContent {
            VibePlayerTheme {
                NavigationRoot()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VibePlayerTheme {
        Greeting("Android")
    }
}
