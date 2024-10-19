package com.bryan.ejercicioclase

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.bryan.ejercicioclase.data.local.MelodyDb
import com.bryan.ejercicioclase.data.repository.LocalArtistRepository
import com.bryan.ejercicioclase.data.repository.LocalSongRepository
import com.bryan.ejercicioclase.presentation.navigation.AppNavigation
import com.bryan.ejercicioclase.presentation.ui.theme.EjercicioClaseTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Utilizar la instancia singleton de la base de datos
        val db = MelodyDb.getInstance(this)
        val songRepository = LocalSongRepository(db.songDao())
        val artistRepository = LocalArtistRepository(db.artistDao(), db.songDao())

        // Verificar y cargar datos
        //LOS LOGS SOLO ERAN PARA PODER DEPURAR EN EL LOGCAT SI SÍ SE CARGAN 1 VEZ
        lifecycleScope.launch {
            val songCount = db.songDao().getSongCount()
            Log.d("MainActivity", "Cantidad de canciones en la base de datos: $songCount")

            if (songCount == 0) {
                Log.d("MainActivity", "La base de datos está vacía. Cargando datos iniciales...")
                artistRepository.populateLocalArtistDatabase()
                songRepository.populateLocalSongDatabase()
                Log.d("MainActivity", "Datos iniciales cargados correctamente.")
            } else {
                Log.d("MainActivity", "La base de datos ya contiene datos. No es necesario cargar.")
            }
        }

        setContent {
            val navController = rememberNavController()
            EjercicioClaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        navController = navController
                    )
                }
            }
        }
    }
}
