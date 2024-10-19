package com.bryan.ejercicioclase.presentation.artists

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object ArtistDestination

fun NavGraphBuilder.artistScreen() {
    composable<ArtistDestination> {
        ArtistsRoute()
    }
}
