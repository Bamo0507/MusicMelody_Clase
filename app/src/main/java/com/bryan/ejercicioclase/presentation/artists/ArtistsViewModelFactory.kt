package com.bryan.ejercicioclase.presentation.artists

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.bryan.ejercicioclase.data.repository.LocalArtistRepository
import com.bryan.ejercicioclase.data.repository.LocalSongRepository

class ArtistsViewModelFactory(
    private val artistRepository: LocalArtistRepository,
    private val songRepository: LocalSongRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String, modelClass: Class<T>, handle: SavedStateHandle
    ): T {
        return ArtistsViewModel(artistRepository, songRepository, handle) as T
    }
}
