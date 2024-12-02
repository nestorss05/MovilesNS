package com.nestorss.paroomiso2.ui.views

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nestorss.paroomiso2.MainActivity.Companion.coroutine
import com.nestorss.paroomiso2.MainActivity.Companion.database
import com.nestorss.paroomiso2.ent.tConfiguracion

@Composable
fun DetallesSala(modifier: Modifier, navController: NavController, idSala: Int) {
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
    var numAsientosOcupados by remember { mutableStateOf(0) }
    var cuantoPalomitas by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        conf = database.configuracionDao().sacaConfiguracion()
        numAsientosOcupados=database.clientesDao().cuantosClientesEnSala(idSala)
        cuantoPalomitas=database.clientesDao().cuantosPalomitasEnSala(idSala)
    }
    Column (
        modifier = modifier .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally, // Centrado horizontalmente
        verticalArrangement = Arrangement.Center // Centrado verticalmente
    ){

        // Espaciador que centra el texto (este lo echa para abajo)
        Spacer(modifier = Modifier.weight(0.00001f))

        // Titulo y nº de sala
        Text(
            text = "Sala nº $idSala",
            modifier = Modifier.padding(10.dp),
            fontSize = 24.sp
        )

        // Datos de la sala
        Text(
            text = "Numero de asientos: ${conf.numAsientos}",
            modifier = Modifier.padding(10.dp)
        )
        Text(
            text = "Numero de asientos ocupados: $numAsientosOcupados",
            modifier = Modifier.padding(10.dp)
        )
        Text(
            text = "Numero de personas con palomitas en la sala: $cuantoPalomitas",
            modifier = Modifier.padding(10.dp)
        )
        Text(
            text = "Dinero recaudado palomitas en sala: ${cuantoPalomitas * conf.precioPalomitas}€",
            modifier = Modifier.padding(10.dp)
        )

        // Boton para volverr
        Button(
            onClick = {
                navController.navigate("salas")
            },
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "Volver",
                fontSize = 18.sp,
            )
        }

        // Espaciador que centra el texto (este lo echa para arriba)
        Spacer(modifier = Modifier.weight(0.00001f))

    }
}