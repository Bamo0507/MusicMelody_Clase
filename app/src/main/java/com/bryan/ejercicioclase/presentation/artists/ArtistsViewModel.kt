package com.bryan.ejercicioclase.presentation.artists

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bryan.ejercicioclase.data.repository.LocalArtistRepository
import com.bryan.ejercicioclase.data.repository.LocalSongRepository
import com.bryan.ejercicioclase.domain.model.Artist
import com.bryan.ejercicioclase.domain.model.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArtistsViewModel(
    private val artistRepository: LocalArtistRepository,
    private val songRepository: LocalSongRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState: MutableStateFlow<ArtistsScreenState> = MutableStateFlow(ArtistsScreenState())
    val uiState: StateFlow<ArtistsScreenState> = _uiState

    init {
        loadArtists()
    }

    private fun loadArtists() {
        viewModelScope.launch {
            val artists = artistRepository.getArtists()
            val sortedArtists = artists.map { artist ->
                val sortedSongs = artist.songs.sortedWith(
                    compareByDescending<Song> { it.isFavorite }.thenBy { it.name }
                )
                artist.copy(songs = sortedSongs)
            }
            _uiState.update { state ->
                state.copy(
                    isLoading = false,
                    artists = sortedArtists
                )
            }
        }
    }

    fun toggleFavorite(songId: Int) {
        viewModelScope.launch {
            val song = findSongById(songId)
            song?.let {
                songRepository.setFavorite(songId, !it.isFavorite)
                // Recargar artistas para reflejar el cambio en la UI
                loadArtists()
            }
        }
    }

    private fun findSongById(songId: Int): Song? {
        return _uiState.value.artists.flatMap(Artist::songs).find { it.id == songId }
    }
}
