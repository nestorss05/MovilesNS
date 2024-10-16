package com.nestorss.t3_ejercicio1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nestorss.t3_ejercicio1.ui.theme.T3_Ejercicio1Theme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            T3_Ejercicio1Theme {
                ItemList(
                    // Lista de contactos
                    itemAnimales = listOf(
                        Animales(R.drawable.elefante,"Elefante", "El elefante es el mamífero terrestre más grande, conocido por su trompa flexible, grandes orejas y su inteligencia. Viven en manadas y tienen una vida social compleja."),
                        Animales(R.drawable.tigre,"Tigre", "Este gran felino, famoso por su pelaje anaranjado con rayas negras, es un cazador solitario. Habita en selvas y bosques y es un símbolo de fuerza y belleza."),
                        Animales(R.drawable.delfin,"Delfin", "Mamífero marino muy inteligente, conocido por su sociabilidad y habilidades comunicativas. Suele vivir en grupos y es famoso por sus saltos acrobáticos en el agua."),
                        Animales(R.drawable.aguila,"Aguila", "Ave de presa con una vista excepcional y garras poderosas. Se encuentra en diversos hábitats y es conocida por su habilidad para cazar en vuelo."),
                        Animales(R.drawable.rinoceronte,"Rinoceronte", "Gran mamífero herbívoro con una o dos cuernos en su nariz. Vive en sabanas y selvas, y es conocido por su piel gruesa y su comportamiento solitario o en pequeños grupos."),
                        Animales(R.drawable.canguro,"Canguro", "Marsupial australiano famoso por su capacidad de saltar grandes distancias. Tiene patas traseras fuertes y una bolsa en la que lleva a sus crías."),
                        Animales(R.drawable.pingu,"Pinguino", "Ave no voladora que vive en el hemisferio sur, especialmente en la Antártida. Son conocidos por su andar torpe y su plumaje blanco y negro."),
                        Animales(R.drawable.jirafa,"Jirafa", "El mamífero más alto, con un cuello largo que le permite alcanzar hojas en los árboles altos. Vive en sabanas y es conocido por su aspecto distintivo y su comportamiento tranquilo."),
                        Animales(R.drawable.koala,"Koala", "Marsupial australiano que se alimenta principalmente de hojas de eucalipto. Pasan la mayor parte del tiempo durmiendo en los árboles y son conocidos por su apariencia adorable."),
                        Animales(R.drawable.serpiente,"Serpiente", "Reptil sin extremidades que se desplaza de manera ondulante. Existen muchas especies, algunas venenosas y otras no, y se encuentran en diversos hábitats alrededor del mundo."),
                        Animales(R.drawable.osopolar,"Oso polar", "Mamífero carnívoro que habita en el Ártico. Está adaptado al frío, con una gruesa capa de grasa y pelaje blanco, lo que le ayuda a camuflarse en la nieve."),
                        Animales(R.drawable.cebra,"Cebra", "Mamífero herbívoro conocido por sus distintivas rayas blancas y negras. Viven en manadas en la sabana africana y son sociales, formando lazos fuertes entre ellos."),
                        Animales(R.drawable.panda,"Panda gigante", "Mamífero herbívoro originario de China, famoso por su pelaje blanco y negro y su dieta basada en bambú. Es un símbolo de conservación y vulnerabilidad."),
                        Animales(R.drawable.canislupus,"Lobo", "Mamífero carnívoro que vive en manadas, conocido por su aguda comunicación y habilidades de caza. Juegan un papel crucial en el equilibrio de sus ecosistemas.")
                    )
                )
            }
        }
    }
}

@Composable
fun ItemList(itemAnimales: List<Animales>) {
    // LazyColumn para mostrar los elementos en una columna
    LazyColumn(
        modifier = Modifier.fillMaxWidth() .padding(4.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(itemAnimales.size) { i ->
            AnimalView(animal = itemAnimales[i]) // Card con toda la informacion del animal
        }
    }
}

@Composable
fun AnimalView(animal: Animales) {
    val nombre by remember { mutableStateOf(animal.nombre) }
    val imagen by remember { mutableIntStateOf(animal.imagen) }
    var descripcion by remember { mutableStateOf("") }
    var textoModificado by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .animateContentSize()
            .clickable {
                if (textoModificado) {
                    descripcion = ""
                    textoModificado = false
                } else {
                    descripcion = animal.descripcion
                    textoModificado = true
                }
            }
    )
    {
        Row {
            Image(
                painter = painterResource(id = imagen),
                contentDescription = "Foto del animal",
                modifier = Modifier.fillMaxHeight(),
            )
        }
        Row {
            Text(
                text = nombre,
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp),
            )
        }
        Row {
            Text(
                text = descripcion,
                fontSize = 16.sp,
                modifier = Modifier.padding(8.dp),
            )
        }
    }
}

