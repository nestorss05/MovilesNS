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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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

    val ganadores = rememberSaveable { mutableStateOf<Map<tJugadores, Int>>(emptyMap()) }

    LaunchedEffect(Unit) {
        val jugadas = database.sorteoDao().getTodo()
        val ganadoresTemp = mutableMapOf<tJugadores, Int>()
        for (jugada in jugadas) {
            val jugador = database.jugadorDao().getJugador(jugada.idGanador.toLong())
            ganadoresTemp[jugador] = ganadoresTemp.getOrDefault(jugador, 0) + 1
        }
        ganadores.value = ganadoresTemp
    }

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

        LazyColumn (
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            items(ganadores.value.entries.toList()) { (ganador, vecesGanadas) ->
                EstadisticaView(ganador, vecesGanadas)
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
fun EstadisticaView(jugador: tJugadores, vecesGanadas: Int) {
    Row {
        Text (
            text = jugador.nombre,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Text (
            text = vecesGanadas.toString(),
            fontSize = 16.sp,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}