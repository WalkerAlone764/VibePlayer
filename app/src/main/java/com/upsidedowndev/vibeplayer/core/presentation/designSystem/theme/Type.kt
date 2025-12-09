package com.upsidedowndev.vibeplayer.core.presentation.designSystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.upsidedowndev.vibeplayer.R

val Inter = FontFamily(
    Font(
        resId = R.font.hostgrotesk_bold,
        weight = FontWeight.Bold
    ),
    Font(
        resId = R.font.hostgrotesk_extrabold,
        weight = FontWeight.ExtraBold
    ),
    Font(
        resId = R.font.hostgrotesk_light,
        weight = FontWeight.Light
    ),
    Font(
        resId = R.font.hostgrotesk_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.hostgrotesk_regular,
        weight = FontWeight.Normal
    ),
    Font(
        resId = R.font.hostgrotesk_semibold,
        weight = FontWeight.SemiBold
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    /*labelLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp
    ),*/
    bodyLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    /*bodySmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),*/
    titleLarge = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp
    ),
    /*titleSmall = TextStyle(
        fontFamily = Inter,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    )*/
)