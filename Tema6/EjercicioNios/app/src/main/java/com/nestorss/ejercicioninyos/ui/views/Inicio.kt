package com.nestorss.ejercicioninyos.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nestorss.ejercicioninyos.ent.Animales

@Composable
fun Inicio(modifier: Modifier = Modifier, navController: NavController) {

    var animales by rememberSaveable { mutableStateOf<List<Animales>>(emptyList()) }
    val animalesBase = mutableListOf<Animales>()
    for (i in 1..10) {
        val animal = Animales(i, getNombre(i))
        animalesBase.add(animal)
    }
    animales = animalesBase

    Column (
        modifier = modifier .fillMaxSize()
    ) {
        Text(
            text = "Animales",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Listado de casas
        LazyColumn (
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            items(animales) { animal ->
                AnimalView(animal, navController)
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

@Composable
fun AnimalView(animal: Animales, navController: NavController) {
    // Muestra el contenido del animal
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text (
            text = animal.id.toString() + ". " + animal.nombre,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )

        // Boton que te dirige a la reproduccion del sonido del animal
        Button(
            onClick = {
                navController.navigate("page/${animal.id}")
            },
            modifier = Modifier.padding(16.dp),
        ) {
            // Texto del botón
            Text(
                text = "Reproducir",
                fontSize = 18.sp,
            )
        }
    }
}

fun getNombre(i: Int): String {
    var res = ""
    when (i) {
        1 -> res = "Pajaro"
        2 -> res = "Gato"
        3 -> res = "Gallo"
        4 -> res = "Vaca"
        5 -> res = "Perro"
        6 -> res = "Aguila"
        7 -> res = "Leon"
        8 -> res = "Buho"
        9 -> res = "Cerdo"
        10 -> res = "Oveja"
    }
    return res
}