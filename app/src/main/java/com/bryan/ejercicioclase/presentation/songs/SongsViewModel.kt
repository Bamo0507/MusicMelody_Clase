package com.bryan.ejercicioclase.presentation.songs

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bryan.ejercicioclase.data.repository.LocalSongRepository
import com.bryan.ejercicioclase.domain.model.Song
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SongsViewModel(
    private val songRepository: LocalSongRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState: MutableStateFlow<SongsScreenState> = MutableStateFlow(SongsScreenState())
    val uiState: StateFlow<SongsScreenState> = _uiState

    init {
        loadSongs()
    }

    private fun loadSongs() {
        viewModelScope.launch {
            songRepository.getAllSongs().collect { songs ->
                val sortedSongs = songs.sortedWith(
                    compareByDescending<Song> { it.isFavorite }.thenBy { it.name }
                )
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        songs = sortedSongs
                    )
                }
            }
        }
    }

    fun toggleFavorite(songId: Int) {
        viewModelScope.launch {
            val currentSongs = _uiState.value.songs
            val song = currentSongs.find { it.id == songId }
            song?.let {
                songRepository.setFavorite(songId, !it.isFavorite)
            }
        }
    }
}
