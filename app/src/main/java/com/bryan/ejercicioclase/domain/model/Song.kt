package com.bryan.ejercicioclase.domain.model

import androidx.compose.ui.graphics.Color

data class Song(
    val id: Int,
    val name: String,
    val artistName: String,
    val color: Color,
    val genre: String,
    val isFavorite: Boolean
)
