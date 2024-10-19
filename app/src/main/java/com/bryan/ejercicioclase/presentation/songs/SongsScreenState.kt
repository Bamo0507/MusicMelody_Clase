package com.bryan.ejercicioclase.presentation.songs

import com.bryan.ejercicioclase.domain.model.Song

data class SongsScreenState(
    val isLoading: Boolean = true,
    val songs: List<Song> = emptyList()
)