package com.nestorss.gamblingroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.nestorss.gamblingroom.dal.GamblingDB
import com.nestorss.gamblingroom.ui.theme.GamblingRoomTheme
import com.nestorss.gamblingroom.ui.views.Estadisticas
import com.nestorss.gamblingroom.ui.views.Form
import com.nestorss.gamblingroom.ui.views.Resultados

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var database: GamblingDB
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Room.databaseBuilder(
            applicationContext,
            GamblingDB::class.java,
            "gambling-db"
        )
            .fallbackToDestructiveMigration()
            .build()
        enableEdgeToEdge()
        setContent {
            GamblingRoomTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "form"
                    ) {
                        composable("form") {
                            Form (
                                modifier = Modifier.padding(innerPadding),
                                navController = navController
                            )
                        }
                        composable("resultados") {
                            Resultados (
                                modifier = Modifier.padding(innerPadding),
                                navController = navController
                            )
                        }
                        composable("estadisticas") {
                            Estadisticas (
                                modifier = Modifier.padding(innerPadding),
                                navController = navController
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
            .fillMaxSize()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
        ) {
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            enabled = pantallaActual != "resultados",
            onClick = {
                navController.navigate("resultados")
            }
        ) {
            Text("Apuesta")
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            enabled = pantallaActual != "estadisticas",
            onClick = {
                navController.navigate("estadisticas")
            }) {
            Text("Estadisticas")
        }
    }
}