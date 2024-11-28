package com.nestorss.paroomiso

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.nestorss.paroomiso.MainActivity.Companion.conf
import com.nestorss.paroomiso.MainActivity.Companion.database
import com.nestorss.paroomiso.dal.CineDB
import com.nestorss.paroomiso.dal.ClientesEnt
import com.nestorss.paroomiso.dal.ConfiguracionEnt
import com.nestorss.paroomiso.ui.theme.ParoomisoTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var database: CineDB
        val conf = ConfiguracionEnt()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Instancia la BD en la variable database
        database = Room.databaseBuilder(
            applicationContext,
            CineDB::class.java,
            "paroomiso-db"
        )
            .fallbackToDestructiveMigration()
            .build()
        enableEdgeToEdge()
        setContent {
            ParoomisoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "configuracion"
                    ) {
                        composable("configuracion") {
                            Configuracion(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController
                            )
                        }
                        composable("salas") {
                            Salas(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController
                            )
                        }
                        composable("asistencia") {
                            Asistencia(
                                modifier = Modifier.padding(innerPadding),
                                navController = navController
                            )
                        }
                        composable("detalles") {
                            Detalles(
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
        verticalAlignment = Alignment.Bottom,

        ) {
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            enabled = pantallaActual != "configuracion",
            onClick = {
                navController.navigate("configuracion")
            }
        ) {
            Text("Configuracion")
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            enabled = pantallaActual != "salas",
            onClick = {
                navController.navigate("salas")
            }) {
            Text("Salas")
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            enabled = pantallaActual != "asistencia",
            onClick = {
                navController.navigate("asistencia")
            }) {
            Text("Asistencia")
        }
    }
}

@Composable
fun Configuracion(modifier: Modifier, navController: NavController) {

    // CoroutineScope para ejecutar funciones de BD
    val coroutineScope = rememberCoroutineScope()

    // Context para usarlo en la funcion toaster
    val context = LocalContext.current

    Column (
        modifier = modifier .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally, // Centrado horizontalmente
        verticalArrangement = Arrangement.Center // Centrado verticalmente
    ) {

        // Espaciador que centra el texto (este lo echa para arriba)
        Spacer(modifier = Modifier.weight(0.00001f))
        Spacer(modifier = Modifier.height(200.dp))

        var salasLocal by rememberSaveable { mutableIntStateOf(0) }
        var asientosLocal by rememberSaveable { mutableIntStateOf(0) }
        var precioPalomitasLocal by rememberSaveable { mutableFloatStateOf(0f) }

        // TextField que hace de input de numero de salas
        TextField(
            value = salasLocal.toString(),
            onValueChange = { salasLocal = it.toInt() },
            label = { Text("Nº de salas") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // TextField que hace de input de numero de asientos
        TextField(
            value = asientosLocal.toString(),
            onValueChange = { asientosLocal = it.toInt() },
            label = { Text("Nº de asientos") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // TextField que hace de input de numero de asientos
        TextField(
            value = precioPalomitasLocal.toString(),
            onValueChange = { precioPalomitasLocal = it.toFloat() },
            label = { Text("Coste de las palomitas (€)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Boton para guardar datos
        Button(
            onClick = {
                if (salasLocal > 0 && asientosLocal > 0 && precioPalomitasLocal > 0f) {
                    // Si los datos no son 0 o negativos, se guardaran los datos en las variables globales
                    pasarValoresBD(coroutineScope, salasLocal, asientosLocal, precioPalomitasLocal)
                    toaster(context, 1);
                } else {
                    // De lo contrario, se ejecutara la funcion toaster, con codigo 0 (una o varias cadenas vacias)
                    toaster(context, 0);
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

fun pasarValoresBD(coroutineScope: CoroutineScope, nSalas: Int, nAsientos: Int, precioPalomitas: Float) {
    coroutineScope.launch {
        conf.numSalas = nSalas
        conf.numAsientos = nAsientos
        conf.precioPalomitas = precioPalomitas
        database.cineDao().insertarConfiguracion(conf)
    }
}

@Composable
fun Salas(modifier: Modifier, navController: NavController) {

    // CoroutineScope para ejecutar funciones de BD
    val coroutineScope = rememberCoroutineScope()

    Column (
        modifier = modifier .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally, // Centrado horizontalmente
        verticalArrangement = Arrangement.Center // Centrado verticalmente
    ) {

        var idSala = 0
        var salaElegida: Int
        var palomitas: Int
        var listaClientes = mutableListOf<ClientesEnt>()
        var sala = ConfiguracionEnt()

        // Espaciador que centra el texto (este lo echa para abajo)
        Spacer(modifier = Modifier.weight(0.00001f))
        Spacer(modifier = Modifier.height(200.dp))

        // TODO: Arreglar Pantalla 2

        LaunchedEffect(Unit) {
            sala = database.cineDao().getTodosConfiguracion()
            while (listaClientes.size <= 100) {
                val cantPersAniadir = (1..5).random()
                val salaMax = database.cineDao().getNumSalas()
                for (i in 1..cantPersAniadir) {
                    val cliente = ClientesEnt()
                    salaElegida = Random.nextInt(0, salaMax+1)
                    palomitas = Random.nextInt(0, 2)
                    cliente.salaElegida = salaElegida
                    cliente.palomitas = palomitas
                    listaClientes.add(cliente)
                }
                delay(1200)
            }
        }

        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            for (i in 1..sala.numSalas + 1) {
                idSala++
                SalaView(
                    sala,
                    id = idSala
                )
            }
        }

        // Espaciador que centra el texto (este lo echa para arriba)
        Spacer(modifier = Modifier.weight(0.00001f))

        // Botonera
        Botonera(navController = navController, "salas")

    }
}

@Composable
fun SalaView(cfg: ConfiguracionEnt, id: Int) {
    val i = id
    val asientos = cfg.numAsientos
    val precioPalomitas = cfg.precioPalomitas
    Row {
        Text (
            text = i.toString(),
            fontSize = 24.sp
        )
        Text (
            text = asientos.toString(),
            fontSize = 24.sp
        )
        Text (
            text = precioPalomitas.toString(),
            fontSize = 24.sp
        )
    }
}

@Composable
fun Asistencia(modifier: Modifier, navController: NavController) {

    // CoroutineScope para ejecutar funciones de BD
    val coroutineScope = rememberCoroutineScope()

    Column (
        modifier = modifier .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally, // Centrado horizontalmente
        verticalArrangement = Arrangement.Center // Centrado verticalmente
    ) {

        // Variables que se mostraran por pantalla
        var asistencia by rememberSaveable { mutableIntStateOf(0) }
        var nPersonas by rememberSaveable { mutableIntStateOf(0) }
        var precioPalomitas by rememberSaveable { mutableFloatStateOf(0f) }

        // Establece los valores de las variables
        // TODO: Establecer valores de asistencia y nPersonas
        precioPalomitas = definirPrecioPalomitas(coroutineScope)

        // Espaciador que centra el texto (este lo echa para abajo)
        Spacer(modifier = Modifier.weight(0.00001f))
        Spacer(modifier = Modifier.height(200.dp))

        Text(
            text = "Resumen de asistencia: $asistencia",
            fontSize = 18.sp,
        )

        Text(
            text = "Nº de personas sin asistir: $nPersonas",
            fontSize = 18.sp,
        )

        Text(
            text = "Dinero total en palomitas: $precioPalomitas",
            fontSize = 18.sp,
        )

        // Espaciador que centra el texto (este lo echa para arriba)
        Spacer(modifier = Modifier.weight(0.00001f))

        // Botonera
        Botonera(navController = navController, "asistencia")

    }
}

fun definirPrecioPalomitas(coroutineScope: CoroutineScope): Float {
    var res = 0f
    coroutineScope.launch {
        val cantidad = database.cineDao().getClientesConPalomitas()
        val precio = database.cineDao().getPrecioPalomitas()
        res = precio * cantidad
    }
    return res
}

@Composable
fun Detalles(modifier: Modifier, navController: NavController) {
    Column (
        modifier = modifier .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally, // Centrado horizontalmente
        verticalArrangement = Arrangement.Center // Centrado verticalmente
    ) {

        // Variables que se mostraran por pantalla
        var nSala by rememberSaveable { mutableIntStateOf(0) }
        var nAsientos by rememberSaveable { mutableIntStateOf(0) }
        var nAsientosOcupados by rememberSaveable { mutableIntStateOf(0) }
        var nPersonasConPalomitas by rememberSaveable { mutableIntStateOf(0) }
        var precioPalomitas by rememberSaveable { mutableFloatStateOf(0f) }

        // TODO: dar valor a las variables

        // Espaciador que centra el texto (este lo echa para abajo)
        Spacer(modifier = Modifier.weight(0.00001f))

        Text(
            text = "Nº de sala: $nSala",
            fontSize = 18.sp,
        )

        Text(
            text = "Nº de asientos: $nAsientos",
            fontSize = 18.sp,
        )

        Text(
            text = "Nº de asientos ocupados: $nAsientosOcupados",
            fontSize = 18.sp,
        )

        Text(
            text = "Nº de personas con palomitas: $nPersonasConPalomitas",
            fontSize = 18.sp,
        )

        Text(
            text = "Dinero de la sala en palomitas: $precioPalomitas",
            fontSize = 18.sp,
        )

        // Espaciador que centra el texto (este lo echa para arriba)
        Spacer(modifier = Modifier.weight(0.00001f))

    }
}

/**
 * Funcion toaster: manda toasts a base del codigo que recibe
 */
private fun toaster(context: Context, code: Int) {

    // mensaje: variable donde se guaradara el mensaje a ser mostrado por el toast
    var mensaje = "";

    when (code) {
        1 -> {
            mensaje = "Los datos han sido guardados correctamente" // Codigo 1: guardado de datos en la primera pantalla
        }
        0 -> {
            mensaje = "ERROR: uno de los campos es 0" // Codigo 0: uno de los campos estan vacios (0x00)
        }
    }

    // Muestra el toast
    Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
}