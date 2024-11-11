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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.nestorss.pptns.R
import com.nestorss.pptns.ui.views.MainActivity.Companion.database
import com.nestorss.pptns.dal.TareasDatabase
import com.nestorss.pptns.dal.TareaEntity
import com.nestorss.pptns.ui.theme.PPTNSTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var database: TareasDatabase
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Room.databaseBuilder(
            applicationContext,
            TareasDatabase::class.java,
            "tareas-db"
        ).build()
        enableEdgeToEdge()
        setContent {
            val tareasViewModel: TareasVM = viewModel(
                factory = TareasViewModelFactory(database.tareaDao())
            )
            PPTNSTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "Inicio"
                ) {
                    composable("Inicio") {
                        Main (
                            navController
                        )
                    }
                    composable("Juego") {
                        Juego (
                            navController
                        )
                    }
                    composable("Gano/{ganador}") { backStackEntry ->
                        backStackEntry.arguments?.getString("ganador")?.let {
                            Gano (
                                navController,
                                it
                            )
                        }
                    }
                    composable("Puntuaciones") {
                        Puntuaciones(navController = navController, tareasVM = tareasViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun Main(navController: NavController) {
    var text by rememberSaveable { mutableStateOf("") }
    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.00001f))
        Text(
            text = "Piedra, papel o tijera",
            fontSize = 32.sp
        )
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Jugador") },
        )
        Button(
            onClick = {
                navController.navigate("Juego")
            },
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "Iniciar",
                fontSize = 18.sp,
            )
        }
        Spacer(modifier = Modifier.weight(0.00001f))
    }

}

@Composable
fun Juego(navController: NavController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var puntosJ1 by rememberSaveable { mutableIntStateOf(0) }
    var puntosCPU by rememberSaveable { mutableIntStateOf(0) }
    var partidas by rememberSaveable { mutableIntStateOf(0) }
    var eleccionJ1 by rememberSaveable { mutableIntStateOf(0) }
    var eleccionCPU by rememberSaveable { mutableIntStateOf(0) }
    var idImage by rememberSaveable { mutableIntStateOf(R.drawable.blank) }
    var res by rememberSaveable { mutableIntStateOf(0) }

    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.00001f))
        Row {
            Column (
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Image(
                    painter = painterResource(id = idImage),
                    contentDescription = "Imagen editable de la CPU",
                    modifier = Modifier.padding(16.dp) .size(128.dp),
                )
                Text(
                    text = "CPU - $puntosCPU",
                    fontSize = 24.sp,
                )
            }
        }
        Spacer(modifier = Modifier.weight(0.00001f))
        Row {
            Column (
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Text(
                        text = "Jugador - $puntosJ1",
                        fontSize = 24.sp,
                    )
                }
                Spacer(modifier = Modifier.height(48.dp))
                Row {
                    IconButton(
                        onClick = {
                            eleccionJ1 = 0
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
                                prepararGanador(puntosJ1, puntosCPU, navController, coroutineScope)
                            }
                        },
                        modifier = Modifier.padding(16.dp) .size(64.dp),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.piedra),
                            contentDescription = "Piedra",
                        )
                    }
                    IconButton(
                        onClick = {
                            eleccionJ1 = 1
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
                                prepararGanador(puntosJ1, puntosCPU, navController, coroutineScope)
                            }
                        },
                        modifier = Modifier.padding(16.dp) .size(64.dp),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.papel),
                            contentDescription = "Papel",
                        )
                    }
                    IconButton(
                        onClick = {
                            eleccionJ1 = 2
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
                                prepararGanador(puntosJ1, puntosCPU, navController, coroutineScope)
                            }
                        },
                        modifier = Modifier.padding(16.dp) .size(64.dp),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.tijera),
                            contentDescription = "Tijera",
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(0.00001f))
    }

}

@Composable
fun Gano(navController: NavController, ganador: String) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.00001f))
        Text(
            text = "$ganador ha ganado",
            fontSize = 24.sp,
        )
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
        Spacer(modifier = Modifier.weight(0.00001f))
    }
}

@Composable
fun Puntuaciones(navController: NavController, tareasVM: TareasVM) {
    val tareas by tareasVM.todasLasTareas.collectAsState()
    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.00001f))
        LazyColumn {
            items(tareas) { tarea ->
                TareaItem(tarea)
            }
        }
        Button(
            onClick = {
                tareasVM.borrarTodasLasTareas()
            },
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "Borrar records",
                fontSize = 18.sp,
            )
        }
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
        Spacer(modifier = Modifier.weight(0.00001f))
    }
}

@Composable
fun TareaItem(tarea: TareaEntity) {
    ListItem(
        headlineContent = { Text("Ganador: ${tarea.ganador}") },
        supportingContent = { Text("Resultado J1: ${tarea.resultadoJ1}, Resultado J2: ${tarea.resultadoJ2}") }
    )
}

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

    var res = 1

    if (eleccionJ1 == eleccionCPU) {
        res = 0
    } else if ((eleccionJ1 == 0 && eleccionCPU == 1) || (eleccionJ1 == 1 && eleccionCPU == 2) || (eleccionJ1 == 2 && eleccionCPU == 0)) {
        res = -1
    }

    return res
}

private fun declararGanador(puntosJ1: Int, puntosCPU: Int): String {
    var ganador = ""
    if (puntosJ1 > puntosCPU) {
        ganador = "Jugador 1"
    } else if (puntosCPU > puntosJ1) {
        ganador = "CPU"
    } else {
        ganador = "Nadie"
    }
    return ganador
}

private fun declararFila(puntosJ1: Int, puntosCPU: Int, ganador: String): TareaEntity {
    val partida = TareaEntity()
    partida.resultadoJ1 = puntosJ1
    partida.resultadoJ2 = puntosCPU
    partida.ganador = ganador
    return partida
}

private fun prepararGanador(puntosJ1: Int, puntosCPU: Int, navController: NavController, coroutineScope: CoroutineScope) {
    val ganador = declararGanador(puntosJ1, puntosCPU)
    val partida = declararFila(puntosJ1, puntosCPU, ganador)
    coroutineScope.launch {
        database.tareaDao().insertar(partida)
    }
    navController.navigate("Gano/$ganador")
}

private fun toaster(context: android.content.Context, code: Int) {
    var mensaje = "";
    when (code) {
        1 -> {
            mensaje = "Enhorabuena, has ganado."
        }
        0 -> {
            mensaje = "Empate."
        }
        -1 -> {
            mensaje = "Lo sentimos, has perdido."
        }
    }
    Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
}