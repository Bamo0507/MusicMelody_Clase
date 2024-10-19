package com.bryan.ejercicioclase.presentation.songs

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bryan.ejercicioclase.data.local.MelodyDb
import com.bryan.ejercicioclase.data.repository.LocalSongRepository
import com.bryan.ejercicioclase.domain.model.Song
import com.bryan.ejercicioclase.presentation.ui.components.LoadingLayout
import com.bryan.ejercicioclase.presentation.ui.theme.EjercicioClaseTheme
import com.bryan.ejercicioclase.presentation.utilities.randomColor
import androidx.compose.material.*



@Composable
fun SongsRoute() {
    val context = LocalContext.current
    val owner = LocalSavedStateRegistryOwner.current

    // Utilizar la instancia singleton de la base de datos
    val songRepository = LocalSongRepository(MelodyDb.getInstance(context).songDao())

    val songsViewModel: SongsViewModel = viewModel(
        factory = SongsViewModelFactory(
            songRepository = songRepository,
            owner = owner
        )
    )

    val state by songsViewModel.uiState.collectAsStateWithLifecycle()

    SongsScreen(
        state = state,
        onFavClick = { songId ->
            songsViewModel.toggleFavorite(songId)
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun SongsScreen(
    state: SongsScreenState,
    onFavClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if (state.isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.fillMaxSize()
        ) {
            LoadingLayout()
        }
    } else {
        Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface)) {
            // Sticky Header (Cabecera fija)
            Surface(
                color = MaterialTheme.colorScheme.secondary,
                shadowElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Explorar Canciones",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier
                        .padding(16.dp)
                )
            }

            // Lista de canciones con LazyColumn
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp) // Espacio entre ítems
            ) {
                items(state.songs) { song ->
                    SongItem(
                        song = song,
                        onFavClick = onFavClick
                    )
                }
            }
        }
    }
}

@Composable
fun SongItem(
    song: Song,
    onFavClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        shape = RoundedCornerShape(12.dp), // Bordes redondeados
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp), // Margen interno entre ítems
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp) // Espacio interno del Card
        ) {
            // Cuadro de color que representa el álbum
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = song.color)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Información del nombre de la canción y artista
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = song.name,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = song.artistName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = song.genre,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            // Botón de favorito con animación suave
            IconButton(
                onClick = { onFavClick(song.id) }
            ) {
                Crossfade(targetState = song.isFavorite) { isFavorite ->
                    if (isFavorite) {
                        Icon(
                            imageVector = Icons.Outlined.Favorite,
                            contentDescription = "Quitar de favoritos",
                            tint = MaterialTheme.colorScheme.error
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "Añadir a favoritos",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewSongsScreen() {
    EjercicioClaseTheme {
        Surface {
            SongsScreen(
                state = SongsScreenState(
                    isLoading = false,
                    songs = listOf(
                        Song(
                            id = 1,
                            name = "Himno de GT",
                            artistName = "Rafael Álvarez Ovalle ft Juan Carlos Durini",
                            color = randomColor(),
                            genre = "Himno",
                            isFavorite = false
                        ),
                        Song(
                            id = 2,
                            name = "Himno de GT",
                            artistName = "Rafael ",
                            color = randomColor(),
                            genre = "Himno",
                            isFavorite = true
                        ),
                        Song(
                            id = 3,
                            name = "Himno de GT",
                            artistName = "Rafael Álvarez Ovalle",
                            color = randomColor(),
                            genre = "Himno",
                            isFavorite = false
                        )
                    ),
                ),
                onFavClick = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}