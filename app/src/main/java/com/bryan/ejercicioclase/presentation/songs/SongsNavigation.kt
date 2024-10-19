package com.bryan.ejercicioclase.presentation.songs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object SongDestination

fun NavGraphBuilder.songScreen() {
    composable<SongDestination> {
        SongsRoute()
    }
}
