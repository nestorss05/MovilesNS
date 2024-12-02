package com.nestorss.gamblingroom.ui.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nestorss.gamblingroom.MainActivity.Companion.database
import com.nestorss.gamblingroom.ent.tJugadores
import com.nestorss.gamblingroom.ui.theme.GamblingRoomTheme
import kotlinx.coroutines.launch

class Form : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GamblingRoomTheme {

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Form(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally, // Centrado horizontalmente
        verticalArrangement = Arrangement.Center // Centrado verticalmente
    ) {

        // var context: contexto de la aplicacion
        val context = LocalContext.current

        // CoroutineScope para ejecutar funciones de BD
        val coroutineScope = rememberCoroutineScope()

        // Espaciador que centra el texto (este lo echa para abajo)
        Spacer(modifier = Modifier.weight(0.00001f))

        // Texto: inicio de sesion
        Text(
            text = "Apuestas el doom",
            fontSize = 40.sp,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
        )

        // TextFields: usuario y contraseña
        var nombre by rememberSaveable { mutableStateOf("") }
        var numero1 by rememberSaveable { mutableStateOf("") }
        var numero2 by rememberSaveable { mutableStateOf("") }
        var expandido1 by remember {mutableStateOf(false)}
        var expandido2 by remember {mutableStateOf(false)}

        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
        )

        ExposedDropdownMenuBox(
            expanded = expandido1,
            onExpandedChange = {expandido1 = !expandido1}
        ) {
            TextField(
                value = numero1,
                onValueChange = {},
                readOnly = true,
                label = { Text("Numero 1") },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expandido1,
                onDismissRequest = { expandido1 = false }
            ) {
                (1..10).forEach { number ->
                    DropdownMenuItem(
                        text = { Text(text = number.toString()) },
                        onClick = {
                            numero1 = number.toString()
                            expandido1 = false
                        }
                    )
                }
            }
        }

        ExposedDropdownMenuBox(
            expanded = expandido2,
            onExpandedChange = {expandido2 = !expandido2}
        ) {
            TextField(
                value = numero2,
                onValueChange = {},
                readOnly = true,
                label = { Text("Numero 2") },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expandido2,
                onDismissRequest = { expandido2 = false }
            ) {
                (1..10).forEach { number ->
                    DropdownMenuItem(
                        text = { Text(text = number.toString()) },
                        onClick = {
                            numero2 = number.toString()
                            expandido2 = false
                        }
                    )
                }
            }
        }

        // Boton: boton de acceso a la lista de contactos
        Button(
            onClick = {
                coroutineScope.launch {
                    val jugador = tJugadores()
                    if (nombre != "" && numero1 != "" && numero2 != "") {
                        jugador.nombre = nombre
                        jugador.numElegido1 = numero1.toInt()
                        jugador.numElegido2 = numero2.toInt()
                        database.jugadorDao().insertarJugador(jugador)
                        Toast.makeText(context, "El jugador se ha añadido correctamente.", Toast.LENGTH_SHORT).show()
                        navController.navigate("resultados")
                    } else {
                        Toast.makeText(context, "ERROR: uno o varios de los campos estan vacios", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.padding(16.dp),
        ) {
            // Texto del botón
            Text(
                text = "Apostar",
                fontSize = 18.sp,
            )
        }

        // Espaciador que centra el texto (este lo echa para arriba)
        Spacer(modifier = Modifier.weight(0.00001f))
    }

}