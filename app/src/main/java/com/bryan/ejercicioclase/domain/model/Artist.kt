package com.bryan.ejercicioclase.domain.model

data class Artist(
    val name: String,
    val songs: List<Song>,
    val monthlyListeners: Int
)
