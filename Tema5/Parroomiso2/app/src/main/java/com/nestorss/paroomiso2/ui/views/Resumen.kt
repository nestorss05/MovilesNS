package com.nestorss.paroomiso2.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nestorss.paroomiso2.Botonera
import com.nestorss.paroomiso2.MainActivity.Companion.database
import com.nestorss.paroomiso2.ent.tConfiguracion

@Composable
fun Resumen(modifier: Modifier, navController: NavController) {

    // Variables que se mostraran por pantalla
    var clientesRechazados by rememberSaveable { mutableIntStateOf(0) }
    var genteConPalomitas by rememberSaveable { mutableIntStateOf(0)}

    // Variable de configuracion
    var conf by remember {
        mutableStateOf(
            tConfiguracion(
                id = 0,
                numSalas = 0,
                numAsientos = 0,
                precioPalomitas = 0f
            )
        )
    }

    // Establece los valores de las variables
    LaunchedEffect(Unit) {
        conf = database.configuracionDao().sacaConfiguracion()
        clientesRechazados = database.clientesDao().cuantosSinSala()
        genteConPalomitas = database.clientesDao().cuantosPalomitas()
    }

    Column (
        modifier = modifier .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally, // Centrado horizontalmente
        verticalArrangement = Arrangement.Center // Centrado verticalmente
    ) {

        // Espaciador que centra el texto (este lo echa para abajo)
        Spacer(modifier = Modifier.weight(0.00001f))

        // Titulo
        Text(
            text = "Detalles de Salas",
            fontSize = 30.sp
        )

        // Separador entre el titulo y los datos
        Spacer(modifier = Modifier.height(60.dp))

        // Datos
        Text(
            text = "Nº de personas sin asistir: $clientesRechazados",
            fontSize = 18.sp,
        )

        Text(
            text = "Gente con palomitas: $genteConPalomitas",
            fontSize = 18.sp,
        )

        Text(
            text = "Dinero total en palomitas: ${genteConPalomitas * conf.precioPalomitas}€",
            fontSize = 18.sp,
        )

        // Espaciador que centra el texto (este lo echa para arriba)
        Spacer(modifier = Modifier.weight(0.00001f))

        // Botonera
        Botonera(navController = navController, "resumen")

    }
}