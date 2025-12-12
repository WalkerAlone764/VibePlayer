@file:OptIn(ExperimentalMaterial3Api::class)

package com.upsidedowndev.vibeplayer.song.presentation.component.topBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.Arrow_Left
import com.upsidedowndev.vibeplayer.song.util.IconContainer
import com.upsidedowndev.vibeplayer.util.hostgroteskFamily

@Composable
fun InnerTopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    title: String = "Vibe Player"
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    fontFamily = hostgroteskFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    modifier = Modifier
                )
            }
        },
        navigationIcon = {
            IconContainer(
                onClick = onBackClick,
                modifier = Modifier
                    .padding(10.dp),
                icon = Icons.Filled.Arrow_Left,
                contentDescription = "Scan"
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
//            containerColor = Surface,
//            titleContentColor = PrimaryText
        ),
        actions = {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .size(44.dp)
            ) {}
        }
    )
}

@Preview
@Composable
private fun InnerTopBarPreview() {
    InnerTopBar()
}