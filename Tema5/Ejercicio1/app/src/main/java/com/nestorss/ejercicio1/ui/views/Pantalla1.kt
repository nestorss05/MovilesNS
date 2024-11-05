package com.nestorss.ejercicio1.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nestorss.ejercicio1.MainActivity.Companion.database
import com.nestorss.ejercicio1.dal.TareaEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * La lista
 */
@Composable
fun lista(listaTareas: List<TareaEntity>, coroutineScope: CoroutineScope) {
    LazyColumn {
        items(listaTareas) { tarea ->
            filaTarea(tarea, coroutineScope)
        }
    }
}

/**
 * Nueva tarea
 */
@Composable
fun nuevaTarea(coroutineScope: CoroutineScope, listaTareas: MutableList<TareaEntity>) {
    var texto by remember { mutableStateOf("")}
    Row {
        TextField(
            value=texto,
            onValueChange= {
                texto = it
            },
            label = {
                Text(text = "Nueva tarea")
            }
        )
        Button(
            onClick = {
                var tarea = TareaEntity()
                tarea.nombre = texto
                coroutineScope.launch {
                    database.tareaDao().insertar(tarea)
                    listaTareas.add(tarea)
                    texto = ""
                }
            }
        ) {
            Text(text="Añadir")
        }
    }
}

/**
 * Fila tarea
 */
@Composable
fun filaTarea(tarea: TareaEntity, coroutineScope: CoroutineScope) {
    var checked by remember {mutableStateOf(false)}
    Row{
        Checkbox(
            checked = checked,
            onCheckedChange = {
                checked = it
                tarea.hecho = checked
                coroutineScope.launch {
                    database.tareaDao().actualizar(tarea)
                }
            }
        )
        Text(text = tarea.nombre)
    }
}

/**
 * Vista App
 */
@Composable
fun miApp(){
    val coroutineScope = rememberCoroutineScope()
    var listaTareas = remember { mutableStateListOf<TareaEntity>()}

    LaunchedEffect(Unit) {
        listaTareas.clear()
        listaTareas.addAll(database.tareaDao().getTodo())
    }
    Column {
        Spacer(Modifier.height(50.dp))
        nuevaTarea(coroutineScope, listaTareas)
        lista(listaTareas, coroutineScope)
    }
}