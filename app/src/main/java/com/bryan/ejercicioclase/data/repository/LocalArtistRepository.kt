package com.bryan.ejercicioclase.data.repository

import com.bryan.ejercicioclase.data.local.MusicApi
import com.bryan.ejercicioclase.data.local.dao.ArtistDao
import com.bryan.ejercicioclase.data.local.dao.SongDao
import com.bryan.ejercicioclase.data.local.entity.mapToEntity
import com.bryan.ejercicioclase.data.local.entity.mapToModel
import com.bryan.ejercicioclase.domain.model.Artist

class LocalArtistRepository(
    private val artistDao: ArtistDao,
    private val songDao: SongDao
) {
    suspend fun getArtists(): List<Artist> {
        val localArtists = artistDao.getAllArtists()

        return localArtists.map { localArtist ->
            val localSongs = songDao.getSongsFromArtist(localArtist.id)
            localArtist.mapToModel(localSongs)
        }
    }

    suspend fun populateLocalArtistDatabase() {
        val remoteArtists = MusicApi.getArtists()
        val localArtists = remoteArtists.map { remoteArtist ->
            remoteArtist.mapToEntity()
        }
        artistDao.insertAll(localArtists)
    }
}