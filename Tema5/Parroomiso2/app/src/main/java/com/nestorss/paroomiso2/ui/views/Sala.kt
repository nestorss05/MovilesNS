package com.nestorss.paroomiso2.ui.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nestorss.paroomiso2.Botonera
import com.nestorss.paroomiso2.MainActivity.Companion.coroutine
import com.nestorss.paroomiso2.MainActivity.Companion.database
import com.nestorss.paroomiso2.ent.tClientes
import com.nestorss.paroomiso2.ent.tConfiguracion
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun Salas(modifier: Modifier, navController: NavController) {

    // Variable de configuracion
    var conf by remember { mutableStateOf(tConfiguracion(id = 0, numSalas = 0, numAsientos = 0, precioPalomitas = 0f)) }

    // Nº de clientes insertados
    var numClientes by remember { mutableStateOf(0) }

    // Activacion de la insercion de clientes
    var enabled by remember { mutableStateOf(true) }

    // Trae la configuracion a conf
    LaunchedEffect(Unit) {
        conf = extraeCfg()
    }

    // Rellena las salas de clientes
    LaunchedEffect(enabled) {
        if (enabled) {
            while (numClientes < 100) {
                delay(1000)
                numClientes += entraCliente(conf)
            }
        } else {
            enabled = false
        }
    }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {

        // Espaciador que centra el texto (este lo echa para abajo)
        Spacer(modifier = Modifier.weight(0.00001f))

        // Titulo
        Text(
            text = "Lista de Salas:",
            fontSize = 30.sp
        )

        // Separador entre el titulo y el LazyColumn
        Spacer(modifier = Modifier.height(60.dp))

        // LazyColumn con las salas
        LazyColumn (
            modifier = Modifier.padding(10.dp)
        ){
            items(conf.numSalas) { index ->
                SalasItem(index = index + 1, conf, numClientes, navController)
            }
        }

        // Espaciador que centra el texto (este lo echa para arriba)
        Spacer(modifier = Modifier.weight(0.00001f))

        // Botonera
        Botonera(navController = navController, "salas")

    }
}

suspend fun extraeCfg(): tConfiguracion {
    return database.configuracionDao().sacaConfiguracion()
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SalasItem(index: Int, conf: tConfiguracion, numClientes: Int, navController: NavController) {

    // Color del cuadro de la sala
    var color : Color by remember { mutableStateOf(Color.White) }

    // Cambia el color de la sala
    coroutine.launch {
        color = colorCorrespondiente(index, conf)
    }

    // Cuadro de la sala
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color)
            .padding(16.dp)
            .clickable {
                navController.navigate("detallesala/${index}")
            }
    ) {
        Text(
            text = "Sala $index",
            color = Color.White
        )
    }
    Spacer(
        modifier = Modifier.height(10.dp)
    )
}

suspend fun colorCorrespondiente(numSala:Int, conf: tConfiguracion): Color{

    // Cantidad de clientes en la sala
    var clientes=database.clientesDao().cuantosClientesEnSala(numSala)

    // Color del recuadro
    var color: Color

    // Si no hay clientes, mete el primero
    if (clientes==0) {
        clientes=1
    }

    // Porcentaje de clientes en sala
    val porCiento=(clientes*100)/conf.numAsientos

    // Establece el color de la sala segun como se vaya llenando
    color = if (porCiento<50){
        Color.Green
    } else if (porCiento<90){
        Color.Yellow
    } else {
        Color.Red
    }

    // Devuelve el color
    return color
}

suspend fun entraCliente(conf: tConfiguracion) : Int{

    // Nº de salas introducidas en la pantalla de configuracion
    var numSalas = conf.numSalas

    // Nº de clientes a añadir
    var numClientes = Random.nextInt(1, 5)

    // Sala elegida
    var salaElegida : Int

    // Datos del cliente a añadir a la BD
    var Cliente : tClientes

    // Añade a los clientes
    for (i in 1..numClientes) {
        salaElegida = Random.nextInt(1, numSalas+1)
        var numero = database.clientesDao().cuantosClientesEnSala(salaElegida)
        if (numero>=conf.numAsientos) {
            salaElegida=0
        }
        Cliente = tClientes(salaElegida = salaElegida, palomitas = (0..1).random())
        database.clientesDao().anadeCliente(Cliente)
    }

    // Devuelve el nº de clientes a añadir
    return numClientes;
}