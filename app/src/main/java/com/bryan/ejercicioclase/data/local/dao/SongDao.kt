package com.bryan.ejercicioclase.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bryan.ejercicioclase.data.local.entity.SongEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {
    @Query("SELECT * FROM SongEntity")
    fun getAllSongs(): Flow<List<SongEntity>>

    @Insert
    suspend fun insertAll(songs: List<SongEntity>)

    @Query("UPDATE SongEntity SET isFavorite = :isFav WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFav: Boolean)

    @Query("SELECT * FROM SongEntity WHERE artistId = :artistId")
    suspend fun getSongsFromArtist(artistId: String): List<SongEntity>

    //este comentario

    //Este método solo se utiliza en el archivo de MainActivity
    //Servirá para saber si hay o no canciones cargadas
    //Y por lo tanto dictará la lógica de si se debe cargar la base de datos
    @Query("SELECT COUNT(*) FROM SongEntity")
    suspend fun getSongCount(): Int

    
}
