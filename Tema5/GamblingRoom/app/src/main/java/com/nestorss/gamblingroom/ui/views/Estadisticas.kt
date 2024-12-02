package com.nestorss.gamblingroom.ui.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nestorss.gamblingroom.Botonera
import com.nestorss.gamblingroom.MainActivity.Companion.database
import com.nestorss.gamblingroom.ent.tJugadores
import com.nestorss.gamblingroom.ui.theme.GamblingRoomTheme

class Estadisticas : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GamblingRoomTheme {

            }
        }
    }
}

@Composable
fun Estadisticas(modifier: Modifier = Modifier, navController: NavController) {

    Column(
        modifier = modifier .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally, // Centrado horizontalmente
        verticalArrangement = Arrangement.Center // Centrado verticalmente
    ) {

        // Espaciador que centra el texto (este lo echa para abajo)
        Spacer(modifier = Modifier.weight(0.00001f))

        // Texto: estadisticas
        Text(
            text = "Estadisticas",
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
        )

        val ganadores: MutableList<tJugadores> = mutableListOf()

        LaunchedEffect(Unit) {
            val jugadas = database.sorteoDao().getTodo().toMutableList()
            for (jugada in jugadas) {
                val jugador = database.jugadorDao().getJugador(jugada.idGanador.toLong())
                ganadores.add(jugador)
            }
        }

        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(ganadores) { ganador ->
                EstadisticaView(ganador)
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }

        // Botonera
        Botonera(navController, "estadisticas")

        // Espaciador que centra el texto (este lo echa para arriba)
        Spacer(modifier = Modifier.weight(0.00001f))

    }

}

@Composable
fun EstadisticaView(jugador: tJugadores) {
    Row {
        var vecesGanadas = 0
        LaunchedEffect(Unit) {
            vecesGanadas = database.sorteoDao().getVecesGanadas(jugador.id)
        }
        Text (
            text = jugador.nombre,
            fontSize = 16.sp
        )
        Text (
            text = vecesGanadas.toString(),
            fontSize = 16.sp
        )
    }
}