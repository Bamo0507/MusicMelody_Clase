package com.bryan.ejercicioclase.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bryan.ejercicioclase.data.local.dao.ArtistDao
import com.bryan.ejercicioclase.data.local.dao.SongDao
import com.bryan.ejercicioclase.data.local.entity.ArtistEntity
import com.bryan.ejercicioclase.data.local.entity.SongEntity

@Database(
    entities = [
        ArtistEntity::class,
        SongEntity::class
    ],
    version = 1
)
abstract class MelodyDb : RoomDatabase() {
    abstract fun artistDao(): ArtistDao
    abstract fun songDao(): SongDao

    companion object {

        //Código necesario para generar la instancia de la base de datos
        //Garantizamos también que solamente se cree una instancia de la base de datos
        @Volatile
        private var INSTANCE: MelodyDb? = null

        fun getInstance(context: Context): MelodyDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MelodyDb::class.java,
                    "melody_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
