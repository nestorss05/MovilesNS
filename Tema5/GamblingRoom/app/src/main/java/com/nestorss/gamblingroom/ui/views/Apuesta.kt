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
import com.nestorss.gamblingroom.MainActivity
import com.nestorss.gamblingroom.MainActivity.Companion.database
import com.nestorss.gamblingroom.ent.tJugadores
import com.nestorss.gamblingroom.ent.tSorteos
import com.nestorss.gamblingroom.ui.theme.GamblingRoomTheme

class Apuesta : ComponentActivity() {
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
fun Resultados(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally, // Centrado horizontalmente
        verticalArrangement = Arrangement.Center // Centrado verticalmente
    ) {

        // Espaciador que centra el texto (este lo echa para abajo)
        Spacer(modifier = Modifier.weight(0.00001f))

        // Numeros ganadores
        val numero1 = (1..10).random()
        val numero2 = (1..10).random()
        var ganadores: MutableList<tJugadores> = mutableListOf()

        LaunchedEffect(Unit) {
            ganadores.clear()
            val apuesta = tSorteos()
            apuesta.numGanador1 = numero1
            apuesta.numGanador2 = numero2
            val listaJugadores = database.jugadorDao().getTodo()
            for (jugador in listaJugadores) {
                if (jugador.numElegido1 == numero1 && jugador.numElegido2 == numero2) {
                    apuesta.idGanador = jugador.id.toInt()
                    database.sorteoDao().insertarJugada(apuesta)
                    ganadores.add(jugador)
                }
            }
        }

        // Texto: numeros
        Text(
            text = "Numeros: $numero1 y $numero2",
            fontSize = 24.sp,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
        )

        // Texto: ganadores
        Text(
            text = "Ganadores",
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(ganadores) { ganador ->
                GanadorView(ganador)
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }

        // Botonera
        Botonera(navController, "resultados")

        // Espaciador que centra el texto (este lo echa para arriba)
        Spacer(modifier = Modifier.weight(0.00001f))

    }
}

@Composable
fun GanadorView(jugador: tJugadores) {
    Row {
        Text (
            text = jugador.nombre,
            fontSize = 16.sp
        )
    }
}