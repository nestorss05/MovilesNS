package com.nestorss.exament1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nestorss.exament1.ui.theme.ExamenT1Theme
import java.util.stream.IntStream.range

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExamenT1Theme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "Numeros"
                ) {
                    composable("Numeros") {
                        PantallaNumeros (
                            navController
                        )
                    }
                    composable("Nombres/{numPersonas}/{cantidadTotal}") { backStackEntry ->
                        PantallaNombres (
                            navController,
                            backStackEntry.arguments?.getString("numPersonas"),
                            backStackEntry.arguments?.getString("cantidadTotal")
                        )
                    }
                    composable("Resumen/{numPersonas}/{cantidadTotal}/{nombres}") { backStackEntry ->
                        PantallaResumen (
                            navController,
                            backStackEntry.arguments?.getString("numPersonas"),
                            backStackEntry.arguments?.getString("cantidadTotal"),
                            backStackEntry.arguments?.getStringArray("nombres")
                        )
                    }
                }
            }
        }
    }
}

/**
 * PantallaNumeros: pantalla donde indicas la cantidad de personas que tienen que pagar y la cantidad a pagar entre todos
 */
@Composable
fun PantallaNumeros(navController: NavController) {

    // numPersonas: numero de personas que tienen que pagar
    var numPersonas by rememberSaveable{mutableStateOf("")}

    // cantAPagar: cantidad de dinero a pagar
    var cantAPagar by rememberSaveable{mutableStateOf("")}

    // Valores de prueba
    var valorPruebaInt = 0
    var valorPruebaFloat = 0f

    // Contenido del mensaje de error
    var error = ""

    // La columna se agrupa en un surface
    Surface (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2B284))
            .statusBarsPadding(),
        color = Color(0xFFF2B284)
    ) {

        // Los contenidos son agrupados en una columna
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){

            // Espaciador
            Spacer(modifier = Modifier.weight(0.00001f))

            // Campo de numero de personas
            TextField (
                value = numPersonas,
                onValueChange = {numPersonas = it},
                label = {Text("Nº de personas")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // Espaciador
            Spacer(modifier = Modifier.height(12.dp))

            // Campo de cantidad a pagar
            TextField (
                value = cantAPagar,
                onValueChange = {cantAPagar = it},
                label = {Text("Total a pagar")},
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // Espaciador
            Spacer(modifier = Modifier.height(12.dp))

            // Boton para ir al siguiente
            Button (
                onClick = {
                    navController.navigate("Nombres/$numPersonas/$cantAPagar")
                }
            ) {
                Text (
                    text = "Siguiente"
                )
            }

            // Espaciador
            Spacer(modifier = Modifier.weight(0.00001f))
        }
    }
}

/**
 * PantallaNombres: pantalla donde se inserta el nombre de cada persona
 */
@Composable
fun PantallaNombres(navController: NavController, numPersonas: String?, cantidadTotal: String?) {

    // Array de nombres de cada persona que tiene que pagar
    var nombres = arrayListOf(String);

    // Nº de personas sin ?
    var nPersonas = numPersonas?.toInt()

    // Nombre a añadir
    val nombreAdd = ""

    // La columna se agrupa en un surface
    Surface (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2B284))
            .statusBarsPadding(),
        color = Color(0xFFF2B284)
    ) {

        // Los contenidos son agrupados en una columna
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){

            // Espaciador
            Spacer(modifier = Modifier.weight(0.00001f))

            for (i in nPersonas?.let { range(1, it+1) }!!) {
                Column (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    TextField (
                        value = nombreAdd,
                        onValueChange = {},
                        label = {Text("Nombre $i")}
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Boton que dirigira al resumen del pago
            Button (
                onClick = {

                    // Añade nombres al arrayList
                    for (i in nPersonas?.let { range(1, it+1) }!!) {
                        nombres.add(nombreAdd) // No funciona
                    } // Fin For

                    // Navega a la pantalla de resumen
                    navController.navigate("Resumen/$numPersonas/$cantidadTotal/$nombres")
                }
            ) {
                Text (
                    text = "Siguiente"
                )
            }

            // Espaciador
            Spacer(modifier = Modifier.weight(0.00001f))
        }
    }

}

/**
 * PantallaResumen: muestra la cantidad que debe pagar cada persona
 */
@Composable
fun PantallaResumen(navController: NavController, numPersonas: String?, cantidadTotal: String?, listaNombres: Array<String>?) {

    // cantidadPorPersona: cantidad total de dinero entre cantidad de personas
    var cantidadPorPersona = cantidadTotal?.toFloat()?.div(numPersonas?.toInt()!!)

    // La columna se agrupa en un surface
    Surface (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2B284))
            .statusBarsPadding(),
        color = Color(0xFFF2B284)
    ) {

        // Los contenidos son agrupados en una columna
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){

            // Espaciador
            Spacer(modifier = Modifier.weight(0.00001f))

            // Recorre la lista de nombres mostrando nombre y cantidad a pagar
            if (listaNombres != null) {
                for (nombre in listaNombres) {
                    Row {
                        Text (
                            text = nombre,
                            color = Color(0xFFBAF4A2)
                        )
                        Text (
                            text = cantidadPorPersona.toString(),
                            color = Color(0xFFBAF4A2)
                        )
                    }
                }
            }

            // Boton para volver a la pantalla inicial
            Button (
                onClick = {
                    navController.navigate("Numeros")
                }
            ) {
                Text (
                    text = "Siguiente"
                )
            }

            // Espaciador
            Spacer(modifier = Modifier.weight(0.00001f))

        }
    }
}