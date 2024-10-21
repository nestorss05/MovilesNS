package com.nestorss.t3_ejercicio2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nestorss.t3_ejercicio2.ui.theme.T3_Ejercicio2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            T3_Ejercicio2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaBase(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

class ValoresPantalla {
    companion object {
        var precioFinal = 0f
    }
    fun sumarPrecio(precio: Float) {
        precioFinal += precio
    }
    fun restarPrecio(precio: Float) {
        precioFinal -= precio
    }
}

@Composable
fun PantallaBase(modifier: Modifier = Modifier) {
    Column (
        modifier = modifier .padding(8.dp) .fillMaxWidth()
    )
    {
        Text(
            text = "Lista de la compra",
            fontSize = 36.sp,
        )
        Row (
            modifier = Modifier.weight(1.0f)
        ){
            ItemList (
                itemList = listOf(
                    Articulos("Patacas", 0.3f),
                    Articulos("Limones", 0.4f),
                    Articulos("Peras", 1f),
                    Articulos("Aguacates", 2f),
                    Articulos("Cebolla", 0.4f),
                    Articulos("Kiwi", 0.75f),
                    Articulos("Plátano", 0.25f),
                    Articulos("Naranja", 0.6f),
                    Articulos("Manzana", 0.8f),
                    Articulos("Coco", 3.5f),
                    Articulos("Pepino", 0.5f),
                    Articulos("Berenjena", 1.3f),
                    Articulos("Zanahoria", 0.25f),
                    Articulos("Tomate", 0.5f),
                    Articulos("Mango", 1.5f),
                    Articulos("Papaya", 3f),
                    Articulos("Mandarina", 0.5f),
                    Articulos("Granada", 2.5f),
                    Articulos("Brocoli", 1.8f),
                    Articulos("Maracuya", 0.7f)
                ),
            )
        }
        Text(
            text = "Precio total: ${ValoresPantalla.precioFinal}€",
            fontSize = 18.sp,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun ItemList(itemList: List<Articulos>) {
    LazyColumn (
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
    ) {
        items(itemList.size) { i ->
            ArticulosView(articulo = itemList[i])
        }
    }
}

@Composable
fun ArticulosView(articulo: Articulos) {
    val nombre by remember {mutableStateOf(articulo.nombre)}
    val precio by remember {mutableStateOf(articulo.precio.toString() + "€")}
    var checked by remember {mutableStateOf(false)}
    val vp = ValoresPantalla()
    Row {
        Checkbox(
            checked = checked,
            onCheckedChange = {checked = it
                if (checked) {
                    vp.sumarPrecio(articulo.precio)
                } else {
                    vp.restarPrecio(articulo.precio)
                }},
        )
        Text(
            text = nombre,
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp)
                .align(Alignment.CenterVertically),
        )
        Text(
            text = precio,
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp)
                .align(Alignment.CenterVertically),
        )
    }
}
