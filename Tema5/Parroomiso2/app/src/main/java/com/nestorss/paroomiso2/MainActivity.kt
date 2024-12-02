package com.nestorss.paroomiso2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.nestorss.paroomiso2.dal.ParoomisoDB2
import com.nestorss.paroomiso2.ui.theme.Paroomiso2Theme
import com.nestorss.paroomiso2.ui.views.Configuracion
import com.nestorss.paroomiso2.ui.views.DetallesSala
import com.nestorss.paroomiso2.ui.views.Resumen
import com.nestorss.paroomiso2.ui.views.Salas
import kotlinx.coroutines.CoroutineScope

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var database: ParoomisoDB2
        lateinit var coroutine: CoroutineScope
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Room.databaseBuilder(
            applicationContext,
            ParoomisoDB2::class.java,
            "paroomiso-db"
        )
            .fallbackToDestructiveMigration()
            .build()
        enableEdgeToEdge()
        setContent {
            coroutine = rememberCoroutineScope()
            Paroomiso2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "configuracion"
                    ) {
                        composable("configuracion") {
                            Configuracion (
                                modifier = Modifier.padding(innerPadding),
                                navController = navController
                            )
                        }
                        composable("salas") {
                            Salas (
                                modifier = Modifier.padding(innerPadding),
                                navController = navController
                            )
                        }
                        composable("resumen") {
                            Resumen (
                                modifier = Modifier.padding(innerPadding),
                                navController = navController
                            )
                        }
                        composable("detallesala/{idSala}") { backStackEntry ->
                            var id = backStackEntry.arguments?.getString("idSala") ?:"0"
                            DetallesSala (
                                modifier = Modifier.padding(innerPadding),
                                navController = navController,
                                idSala = id.toInt()
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Botonera(navController: NavController, pantallaActual: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            enabled = pantallaActual != "configuracion",
            onClick = {
                navController.navigate("configuracion")
            }
        ) {
            Text("Configuracion")
        }
        Button(
            enabled = pantallaActual != "salas",
            onClick = {
                navController.navigate("salas")
            }
        ) {
            Text("Salas")
        }
        Button(
            enabled = pantallaActual != "resumen",
            onClick = {
                navController.navigate("resumen")
            }
        ) {
            Text("Resumen")
        }
    }
}