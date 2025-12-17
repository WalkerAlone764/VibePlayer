package com.upsidedowndev.vibeplayer.song.presentation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.upsidedowndev.vibeplayer.core.presentation.designSystem.button.FilterButton
import com.upsidedowndev.vibeplayer.song.presentation.scanMusic.models.SelectedFilter
import com.upsidedowndev.vibeplayer.util.hostgroteskFamily

@Composable
fun ScanFilter(
    selected: SelectedFilter,
    onClick: (SelectedFilter) -> Unit,
    optionA: String,
    optionB: String,
    filterTitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = filterTitle,
            fontFamily = hostgroteskFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
//            color = SecondaryText
        )
        Spacer(
            modifier = Modifier
                .height(10.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            FilterButton(
                ignoreText = optionA,
                selected = selected == SelectedFilter.OPTION_A,
                onClick = {
                    onClick(
                        if (SelectedFilter.OPTION_A == selected) SelectedFilter.NONE
                        else SelectedFilter.OPTION_A
                    )
                },
                modifier = Modifier
                    .weight(1f)
            )
            Spacer(
                modifier = Modifier
                    .width(8.dp)
            )
            FilterButton(
                ignoreText = optionB,
                selected = selected == SelectedFilter.OPTION_B,
                onClick = {
                    onClick(
                        if (SelectedFilter.OPTION_B == selected) SelectedFilter.NONE
                        else SelectedFilter.OPTION_B
                    )
                },
                modifier = Modifier
                    .weight(1f)
            )
        }
    }
}