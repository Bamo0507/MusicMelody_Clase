package com.bryan.ejercicioclase.presentation.utilities

import androidx.compose.ui.graphics.Color

fun randomColor(): Color {
    val red = (0..255).random() / 255f
    val green = (0..255).random() / 255f
    val blue = (0..255).random() / 255f
    println("R=$red, G=$green, B=$blue")
    return Color(
        red = red,
        green = green,
        blue = blue,
        alpha = 1f
    )
}