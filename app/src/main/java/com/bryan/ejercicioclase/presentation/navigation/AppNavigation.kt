package com.bryan.ejercicioclase.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bryan.ejercicioclase.presentation.MainNavigationGraph
import com.bryan.ejercicioclase.presentation.mainNavigationGraph
import com.bryan.ejercicioclase.presentation.songs.SongDestination


@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = MainNavigationGraph,
        modifier = modifier
    ) {
        mainNavigationGraph()
    }
}
