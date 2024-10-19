package com.bryan.ejercicioclase.presentation.artists

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bryan.ejercicioclase.data.local.MelodyDb
import com.bryan.ejercicioclase.data.repository.LocalArtistRepository
import com.bryan.ejercicioclase.data.repository.LocalSongRepository
import com.bryan.ejercicioclase.domain.model.Artist
import com.bryan.ejercicioclase.domain.model.Song
import com.bryan.ejercicioclase.presentation.ui.components.LoadingLayout
import com.bryan.ejercicioclase.presentation.ui.theme.EjercicioClaseTheme
import com.bryan.ejercicioclase.presentation.utilities.randomColor

@Composable
fun ArtistsRoute() {
    val context = LocalContext.current
    val owner = LocalSavedStateRegistryOwner.current

    // Utilizar la instancia singleton de la base de datos
    val db = MelodyDb.getInstance(context)
    val artistRepository = LocalArtistRepository(db.artistDao(), db.songDao())
    val songRepository = LocalSongRepository(db.songDao())

    val artistsViewModel: ArtistsViewModel = viewModel(
        factory = ArtistsViewModelFactory(
            artistRepository = artistRepository,
            songRepository = songRepository,
            owner = owner
        )
    )

    val state by artistsViewModel.uiState.collectAsStateWithLifecycle()

    ArtistsScreen(
        state = state,
        onFavClick = { songId ->
            artistsViewModel.toggleFavorite(songId)
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun ArtistsScreen(
    state: ArtistsScreenState,
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
        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface))
        {
            // Cabecera con estilo elevado
            Surface(
                color = MaterialTheme.colorScheme.secondary,
                shadowElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Lista de Artistas",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.padding(16.dp)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre ítems
            ) {
                items(state.artists) { artist ->
                    ArtistItem(
                        artist = artist,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        onFavClick = onFavClick
                    )
                }
            }
        }
    }
}

@Composable
private fun ArtistItem(
    artist: Artist,
    modifier: Modifier = Modifier,
    onFavClick: (Int) -> Unit
) {

    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Encabezado con nombre e icono de oyentes
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = artist.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "Oyentes Mensuales:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
                Text(
                    text = "${artist.monthlyListeners}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.End

                )
            }

        }

        Spacer(modifier = Modifier.height(12.dp))

        // Canciones con LazyRow
        LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(artist.songs) { song ->
                ArtistSongItem(
                    song = song,
                    modifier = Modifier.width(160.dp),
                    onFavClick = onFavClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistSongItem(
    song: Song,
    modifier: Modifier = Modifier,
    onFavClick: (Int) -> Unit = {}
) {
    OutlinedCard(
        modifier = Modifier
            .width(170.dp)
            .background(MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(song.color)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))

            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically){
                Column(modifier = Modifier.width(120.dp)) {
                    Text(
                        text = song.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                    Text(
                        text = song.genre,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Agregamos el icono de favorito
                IconButton(
                    onClick = { onFavClick(song.id) },
                    modifier = Modifier
                        .padding(end = 4.dp)
                ) {
                    if (song.isFavorite) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Outlined.Favorite,
                            contentDescription = "Quitar de favoritos",
                            tint = MaterialTheme.colorScheme.error
                        )
                    } else {
                        androidx.compose.material3.Icon(
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
fun PreviewArtistsScreen() {
    EjercicioClaseTheme {
        Surface {
            ArtistsScreen(
                state = ArtistsScreenState(
                    isLoading = false,
                    artists = listOf(
                        Artist(
                            name = "Juan Carlos",
                            monthlyListeners = 4_000_000,
                            songs = listOf(
                                Song(
                                    id = 1,
                                    name = "Himno de GT",
                                    color = randomColor(),
                                    genre = "Himno",
                                    artistName = "Rafael Álvarez Ovalle",
                                    isFavorite = false
                                ),
                                Song(
                                    id = 2,
                                    name = "Canción 2",
                                    color = randomColor(),
                                    genre = "Pop",
                                    artistName = "Artista 2",
                                    isFavorite = true
                                )
                            )
                        )
                    )
                ),
                onFavClick = {}
            )
        }
    }
}
