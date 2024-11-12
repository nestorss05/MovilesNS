package com.nestorss.pptns.ui.views

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.nestorss.pptns.ui.views.MainActivity.Companion.database
import com.nestorss.pptns.dal.TareasDatabase
import com.nestorss.pptns.dal.TareaEntity
import com.nestorss.pptns.ui.theme.PPTNSTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random
import android.util.Log
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.nestorss.pptns.R

/**
 * Clase MainActivity
 * @author Nestor Sanchez
 */
class MainActivity : ComponentActivity() {

    // Crea la BD en el programa mediante companion object
    companion object {
        lateinit var database: TareasDatabase
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Instancia la BD en la variable database
        database = Room.databaseBuilder(
            applicationContext,
            TareasDatabase::class.java,
            "tareas-db"
        )
            .fallbackToDestructiveMigration()
            .build()
        enableEdgeToEdge()
        setContent {
            PPTNSTheme {

                // NavHost
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "Inicio"
                ) {

                    // Pantalla de inicio
                    composable("Inicio") {
                        Main (
                            navController
                        )
                    }

                    // Pantalla de juego con el nombre del jugador
                    composable("Juego/{nombreJugador}") { backStackEntry ->
                        backStackEntry.arguments?.getString("nombreJugador")?.let {
                            nombreJugador -> Juego ( navController, nombreJugador )
                        }
                    }

                    // Pantalla del ganador con el nombre del ganador
                    composable("Gano/{ganador}") { backStackEntry ->
                        backStackEntry.arguments?.getString("ganador")?.let {
                            ganador -> Gano( navController, ganador )
                        }
                    }

                    // Pantalla de puntuaciones
                    composable("Puntuaciones") {
                        Puntuaciones(navController = navController)
                    }
                }
            }
        }
    }
}

/**
 * Main: pantalla principal del programa
 */
@Composable
fun Main(navController: NavController) {

    // Variable que guarda el usuario
    var usuario by rememberSaveable { mutableStateOf("") }

    // Context para usar la funcion toaster en caso de que la variable usuario este vacia
    val context = LocalContext.current

    // La pantalla consistira en una columna
    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Espaciador para centrar elementos
        Spacer(modifier = Modifier.weight(0.00001f))

        // Texto con el nombre del juego
        Text(
            text = "Piedra, papel o tijera",
            fontSize = 32.sp
        )

        // Espaciador entre el nombre del juego y el input del usuario
        Spacer(modifier = Modifier.height(16.dp))

        // TextField que hace de input de usuario
        TextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Jugador") },
        )

        // Boton para iniciar juegog
        Button(
            onClick = {
                if (!usuario.equals("")) {
                    // Si el usuario no esta vacio, se iniciara el juego
                    navController.navigate("Juego/$usuario")
                } else {
                    // De lo contrario, se ejecutara la funcion toaster, con codigo -2 (cadena usuario vacia)
                    toaster(context, -2);
                }
            },
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "Iniciar",
                fontSize = 18.sp,
            )
        }

        // Espaciador para centrar elementos
        Spacer(modifier = Modifier.weight(0.00001f))
    }

}

/**
 * Juego: pantalla que albergara el juego del piedra papel o tijera
 */
@Composable
fun Juego(navController: NavController, nombreJugador: String) {

    // Context para usarlo en la funcion toaster
    val context = LocalContext.current

    // CoroutineScope para ejecutar funciones de BD
    val coroutineScope = rememberCoroutineScope()

    // puntosJ1: puntos del jugador 1
    var puntosJ1 by rememberSaveable { mutableIntStateOf(0) }

    // puntosCPU: puntos del jugador 2
    var puntosCPU by rememberSaveable { mutableIntStateOf(0) }

    // partidas: nº de partidas jugadas
    var partidas by rememberSaveable { mutableIntStateOf(0) }

    // eleccionJ1: eleccion del jugador
    var eleccionJ1 by rememberSaveable { mutableIntStateOf(0) }

    // eleccionCPU: eleccion de la CPU
    var eleccionCPU by rememberSaveable { mutableIntStateOf(0) }

    // Imagen para lo que saque la CPU (piedra, papel o tijera)
    var idImage by rememberSaveable { mutableIntStateOf(R.drawable.blank) }

    // res: resultado de la ronda
    var res by rememberSaveable { mutableIntStateOf(0) }

    // Los elementos de la pantalla estaran dentro de una columna
    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Espaciador para centrar elementos
        Spacer(modifier = Modifier.weight(0.00001f))

        // Fila de la CPU, que tiene una columna dentro
        Row {
            Column (
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                // Imagen que mostrara lo que va sacando la CPU mediante van pasando las rondas
                Image(
                    painter = painterResource(id = idImage),
                    contentDescription = "Imagen editable de la CPU",
                    modifier = Modifier
                        .padding(16.dp)
                        .size(128.dp),
                )

                // Marcador de la CPU
                Text(
                    text = "CPU - $puntosCPU",
                    fontSize = 24.sp,
                )
            }
        }

        // Espaciador para centrar elementos
        Spacer(modifier = Modifier.weight(0.00001f))

        // Fila del jugador, con una columna dentro
        Row {
            Column (
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Fila con el marcador de J1
                Row {
                    Text(
                        text = "Jugador - $puntosJ1",
                        fontSize = 24.sp,
                    )
                }

                // Espaciador entre la puntuacion y las opciones del jugador
                Spacer(modifier = Modifier.height(48.dp))

                // Opciones del jugador: piedra, papel o tijera
                Row {
                    IconButton(
                        onClick = {
                            eleccionJ1 = 0 // Eleccion 0 (piedra)
                            eleccionCPU = Random.nextInt(0, 3) // Eleccion random de la CPU
                            idImage = cambiarImagen(eleccionCPU) // Segun la eleccion, se cambia la imagen
                            res = jugada(eleccionJ1, eleccionCPU) // Se obtiene el resultado a base de la jugada
                            toaster(context, res) // Inicializa el Toaster a base de lo obtenido
                            if (res == 1) {
                                puntosJ1++ // res = 1 => jugador gana la ronda
                            } else if (res == -1) {
                                puntosCPU++ // res = -1 => CPU gana la ronda
                            } // res = 0 => empate
                            partidas++
                            if (partidas == 5) {
                                // Al terminar la quinta ronda, se declara el ganador
                                prepararGanador(puntosJ1, puntosCPU, navController, coroutineScope, nombreJugador)
                            }
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .size(64.dp),
                    ) {

                        // Imagen de piedra
                        Image(
                            painter = painterResource(id = R.drawable.piedra),
                            contentDescription = "Piedra",
                        )
                    }
                    IconButton(
                        onClick = {
                            eleccionJ1 = 1 // Eleccion 1 (papel)
                            eleccionCPU = Random.nextInt(0, 3)
                            idImage = cambiarImagen(eleccionCPU)
                            res = jugada(eleccionJ1, eleccionCPU)
                            toaster(context, res)
                            if (res == 1) {
                                puntosJ1++
                            } else if (res == -1) {
                                puntosCPU++
                            }
                            partidas++
                            if (partidas == 5) {
                                prepararGanador(puntosJ1, puntosCPU, navController, coroutineScope, nombreJugador)
                            }
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .size(64.dp),
                    ) {

                        // Imagen de papel
                        Image(
                            painter = painterResource(id = R.drawable.papel),
                            contentDescription = "Papel",
                        )
                    }
                    IconButton(
                        onClick = {
                            eleccionJ1 = 2 // Eleccion 2 (tijera)
                            eleccionCPU = Random.nextInt(0, 3)
                            idImage = cambiarImagen(eleccionCPU)
                            res = jugada(eleccionJ1, eleccionCPU)
                            toaster(context, res)
                            if (res == 1) {
                                puntosJ1++
                            } else if (res == -1) {
                                puntosCPU++
                            }
                            partidas++
                            if (partidas == 5) {
                                prepararGanador(puntosJ1, puntosCPU, navController, coroutineScope, nombreJugador)
                            }
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .size(64.dp),
                    ) {

                        // Imagen de tijera
                        Image(
                            painter = painterResource(id = R.drawable.tijera),
                            contentDescription = "Tijera",
                        )
                    }
                }
            }
        }

        // Espaciador para centrar elementos
        Spacer(modifier = Modifier.weight(0.00001f))
    }

}

/**
 * Gano: pantalla donde se muestra el ganador, con un boton para volver al menu inicio y otro para ver las puntuaciones maximas
 */
@Composable
fun Gano(navController: NavController, ganador: String) {

    // Los elementos de la pantalla estaran dentro de un Column
    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Espaciador para centrar elementos
        Spacer(modifier = Modifier.weight(0.00001f))

        // Texto que muestra quien ha ganado
        Text(
            text = "$ganador ha ganado",
            fontSize = 24.sp,
        )

        // Boton que redirige a la pantalla de inicio
        Button(
            onClick = {
                navController.navigate("Inicio")
            },
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "Volver al inicio",
                fontSize = 18.sp,
            )
        }

        // Boton que redirige a la pantalla de puntuaciones maximas
        Button(
            onClick = {
                navController.navigate("Puntuaciones")
            },
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "Puntuaciones maximas",
                fontSize = 18.sp,
            )
        }

        // Espaciador para centrar elementos
        Spacer(modifier = Modifier.weight(0.00001f))
    }
}

/**
 * Puntuaciones: pantalla donde se muestran las puntuaciones de los jugadores
 */
@Composable
fun Puntuaciones(navController: NavController) {

    // CoroutineScope para ejecutar funciones de BD
    val coroutineScope = rememberCoroutineScope()

    // listaJugadores: lista de jugadores a mostrar por pantalla
    val listaJugadores = remember {mutableStateListOf<TareaEntity>()}

    // Añade todos los datos de la BD a listaJugadores mediante un LaunchedEffect
    LaunchedEffect(Unit) {
        listaJugadores.addAll(database.tareaDao().getTodo())
    }

    // Todos los elementos mostrados estan dentro de un column
    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Espaciador para centrar elementos
        Spacer(modifier = Modifier.weight(0.00001f))

        // LazyColumn que almacena cada jugador y sus respectivos datos
        LazyColumn {
            items(items = listaJugadores) { jugador ->
                TareaItem(jugador)
            }
        }

        // Boton para borrar los datos de la BD
        // NOTA: hace su trabajo, pero no se mostrara en pantalla hasta que se vuelva a jugar otra ronda
        Button(
            onClick = {
                coroutineScope.launch {
                    database.tareaDao().borrarTodo()
                }
            },
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "Borrar records",
                fontSize = 18.sp,
            )
        }

        // Boton para volver a la pantalla de inicio
        Button(
            onClick = {
                navController.navigate("Inicio")
            },
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "Volver al inicio",
                fontSize = 18.sp,
            )
        }

        // Espaciador para centrar elementos
        Spacer(modifier = Modifier.weight(0.00001f))
    }
}

/**
 * TareaItem: elementos de los que consistira el LazyColumn de la pantalla de puntuaciones
 */
@Composable
fun TareaItem(tarea: TareaEntity) {
    ListItem(
        headlineContent = { Text("Usuario: ${tarea.username}") }, // Usuario
        supportingContent = { Text("Partidas jugadas: ${tarea.partidasJugadas}, Partidas ganadas: ${tarea.partidasGanadas}, Luchas ganadas: ${tarea.luchasGanadas}") } // Partidas jugadas, partidas ganadas y rondas ganadas
    ) // TODO: rondas ganadas
}

/**
 * Funcion cambiarImagen: cambia la imagen de la CPU a base de su eleccion
 * @return imagen a mostrar por pantalla como eleccion de la CPU
 */
private fun cambiarImagen(eleccionCPU: Int): Int {
    val idImage: Int
    if (eleccionCPU == 0) {
        idImage = R.drawable.piedra
    } else if (eleccionCPU == 1) {
        idImage = R.drawable.papel
    } else {
        idImage = R.drawable.tijera
    }
    return idImage
}

/**
 * Funcion jugada: a base de lo que cada uno haya sacado, establece si ha ganado uno de los dos jugadores o ha habido empate
 * @return resultado de la ronda
 */
private fun jugada(eleccionJ1: Int, eleccionCPU: Int): Int {
    /*
    JUGADAS
    0 - Piedra
    1 - Papel
    2 - Tijera

    RES
    -1 - Gana CPU
    0 - Empate
    1 - Gana J1
     */

    // res: restulado de la ronda (por defecto, gana el jugador)
    var res = 1

    if (eleccionJ1 == eleccionCPU) {
        res = 0 // res sera 0 si la eleccion del jugador es la misma que la de la CPU
    } else if ((eleccionJ1 == 0 && eleccionCPU == 1) || (eleccionJ1 == 1 && eleccionCPU == 2) || (eleccionJ1 == 2 && eleccionCPU == 0)) {
        res = -1 // En caso de que gane la CPU, res sera -1
    }

    return res
}

/**
 * Funcion declararGanador: declara un ganador a base de los puntos que tienen al terminar la quinta ronda
 * @return ganador
 */
private fun declararGanador(puntosJ1: Int, puntosCPU: Int, username: String): String {
    var ganador = ""
    if (puntosJ1 > puntosCPU) {
        ganador = username
    } else if (puntosCPU > puntosJ1) {
        ganador = "CPU"
    } else {
        ganador = "Nadie"
    }
    return ganador
}

/**
 * Funcion declararFila: declara una fila para poner o modificar en la BD
 */
private fun declararFila(puntosJ1: Int, puntosCPU: Int, username: String, coroutineScope: CoroutineScope) {
    coroutineScope.launch {

        // noEstaVacio: comprobador para espacios vacios en la BD del jugador que ha jugado esta partida
        val noEstaVacio = database.tareaDao().getNombre(username)

        // Si no esta vacio, se actualizara el usuario en cuestion
        if (noEstaVacio) {

            // Obten todos los datos del usuario de la BD y guardalos en la variable dbOg
            val dbOg = database.tareaDao().getContUser(username)

            // Sumale 1 a partidasJugadas
            dbOg.partidasJugadas += 1

            // Sumale los puntos de J1 a luchasGanadas
            dbOg.luchasGanadas += puntosJ1

            // Si se ha ganado la ronda, sumale 1 a partidasGanadas
            if (puntosJ1 > puntosCPU) {
                dbOg.partidasGanadas += 1
            }

            // Actualiza la fila en la DB
            database.tareaDao().actualizar(dbOg)
        } else { // De lo contrario, se insertara el usuario
            val partida = TareaEntity()

            // Usuario que ha jugado
            partida.username = username

            // Sumale 1 a partidasJugadas
            partida.partidasJugadas += 1

            // Sumale los puntos de J1 a luchasGanadas
            partida.luchasGanadas += puntosJ1

            // Si se ha ganado la ronda, sumale 1 a partidasGanadas
            if (puntosJ1 > puntosCPU) {
                partida.partidasGanadas += 1
            }

            // Inserta la fila en la DB
            database.tareaDao().insertar(partida)
        }

    }
}

/**
 * Funcion prepararGanador: funcion intermediaria que inicia otras funciones
 */
private fun prepararGanador(puntosJ1: Int, puntosCPU: Int, navController: NavController, coroutineScope: CoroutineScope, username: String) {

    // val ganador: nombre del jugador que ha ganado la partida
    val ganador = declararGanador(puntosJ1, puntosCPU, username)

    // Inserta / Actualiza la fila en la BD
    declararFila(puntosJ1, puntosCPU, username, coroutineScope)

    // Navega hacia la pantalla Gano
    navController.navigate("Gano/$ganador")

}

/**
 * Funcion toaster: manda toasts a base del codigo que recibe
 */
private fun toaster(context: android.content.Context, code: Int) {

    // mensaje: variable donde se guaradara el mensaje a ser mostrado por el toast
    var mensaje = "";

    when (code) {
        1 -> {
            mensaje = "Enhorabuena, has ganado esta ronda." // Codigo 1: el usuario gana la ronda
        }
        0 -> {
            mensaje = "Empate en ronda." // Codigo 0: hay empate
        }
        -1 -> {
            mensaje = "Lo sentimos, has perdido esta ronda." // Codigo -1: la CPU gana la ronda
        }
        -2 -> {
            mensaje = "ERROR: nombre de usuario vacio (0x02)" // Codigo -2: el textfield usuario esta vacio
        }
    }

    // Muestra el toast
    Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
}