package com.upsidedowndev.vibeplayer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.upsidedowndev.vibeplayer.R

// Set of Material typography styles to start with

val fonts = FontFamily(
    Font(R.font.hostgrotesk_regular),
    Font(R.font.hostgrotesk_regular, weight = FontWeight.Normal),
    Font(R.font.hostgrotesk_medium, weight = FontWeight.Medium),
    Font(R.font.hostgrotesk_semibold, weight = FontWeight.SemiBold),
)
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 28.sp
    ),
    titleMedium = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 24.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.sp
    )

)