package com.bryan.ejercicioclase.presentation.artists

import com.bryan.ejercicioclase.domain.model.Artist

data class ArtistsScreenState(
    val isLoading: Boolean = true,
    val artists: List<Artist> = emptyList()
)
