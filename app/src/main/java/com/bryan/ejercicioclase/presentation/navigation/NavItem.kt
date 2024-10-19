package com.bryan.ejercicioclase.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.Audiotrack
import androidx.compose.material.icons.outlined.People
import androidx.compose.ui.graphics.vector.ImageVector
import com.bryan.ejercicioclase.presentation.artists.ArtistDestination
import com.bryan.ejercicioclase.presentation.songs.SongDestination

data class NavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val destination: Any
)


val navigationItems = listOf(
    NavItem(
        title = "Explorar",
        selectedIcon = Icons.Filled.Audiotrack,
        unselectedIcon = Icons.Outlined.Audiotrack,
        destination = SongDestination
    ),
    NavItem(
        title = "Artistas",
        selectedIcon = Icons.Filled.People,
        unselectedIcon = Icons.Outlined.People,
        destination = ArtistDestination
    )
)

val topLevelDestinations = listOf(
    SongDestination::class,
    ArtistDestination::class
)