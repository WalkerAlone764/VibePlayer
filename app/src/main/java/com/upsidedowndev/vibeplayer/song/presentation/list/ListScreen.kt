package com.upsidedowndev.vibeplayer.song.presentation.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme.VibePlayerTheme

@Composable
fun ListRoot(
    viewModel: ListViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ListScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun ListScreen(
    state: ListState,
    onAction: (ListAction) -> Unit,
) {



}

@Preview
@Composable
private fun Preview() {
    VibePlayerTheme {
        ListScreen(
            state = ListState(),
            onAction = {}
        )
    }
}