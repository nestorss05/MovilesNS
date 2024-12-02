package com.nestorss.paroomiso2.ui.views

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nestorss.paroomiso2.Botonera
import com.nestorss.paroomiso2.MainActivity.Companion.database
import com.nestorss.paroomiso2.ent.tConfiguracion
import kotlinx.coroutines.launch

@Composable
fun Configuracion(modifier: Modifier, navController: NavController) {

    // Context para usarlo en la funcion toaster
    val context = LocalContext.current

    var salasLocal by rememberSaveable { mutableStateOf("") }
    var asientosLocal by rememberSaveable { mutableStateOf("") }
    var precioPalomitasLocal by rememberSaveable { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column (
        modifier = modifier .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally, // Centrado horizontalmente
        verticalArrangement = Arrangement.Center // Centrado verticalmente
    ) {

        // Espaciador que centra el texto (este lo echa para arriba)
        Spacer(modifier = Modifier.weight(0.00001f))

        // Texto: inicio de sesion
        Text(
            text = "Paroomiso2",
            fontSize = 40.sp,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
        )

        // TextField que hace de input de numero de salas
        TextField(
            value = salasLocal.toString(),
            onValueChange = { salasLocal = it },
            label = { Text("Nº de salas") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // TextField que hace de input de numero de asientos
        TextField(
            value = asientosLocal.toString(),
            onValueChange = { asientosLocal = it },
            label = { Text("Nº de asientos") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // TextField que hace de input de numero de asientos
        TextField(
            value = precioPalomitasLocal.toString(),
            onValueChange = { precioPalomitasLocal = it },
            label = { Text("Coste de las palomitas (€)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Boton para guardar datos
        Button(
            onClick = {
                if (salasLocal.toInt() > 0 && asientosLocal.toInt() > 0 && precioPalomitasLocal.toFloat() > 0f) {
                    // Si los datos no son 0 o negativos, se guardaran los datos en las variables globales
                    coroutineScope.launch {
                        pasarValoresBD(salasLocal.toInt(), asientosLocal.toInt(), precioPalomitasLocal.toFloat())
                    }
                    Toast.makeText(context, "Los datos han sido guardados correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    // De lo contrario, se ejecutara la funcion toaster, con codigo 0 (una o varias cadenas vacias)
                    Toast.makeText(context, "ERROR: uno de los campos es 0", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "Guardar",
                fontSize = 18.sp,
            )
        }

        // Espaciador que centra el texto (este lo echa para abajo)
        Spacer(modifier = Modifier.weight(0.00001f))

        // Botonera
        Botonera(navController = navController, "configuracion")

    }
}

suspend fun pasarValoresBD(nSalas: Int, nAsientos: Int, precioPalomitas: Float) {

    // Valor de configuracion
    val conf = tConfiguracion()

    // Borra las configuraciones y clientes anteriores
    database.configuracionDao().borrarConfiguracion()
    database.clientesDao().borrarClientes()

    // Establece los datos
    conf.id = 1
    conf.numSalas = nSalas
    conf.numAsientos = nAsientos
    conf.precioPalomitas = precioPalomitas

    // Insertalos en la BD
    database.configuracionDao().insertarConfiguracion(conf)

}