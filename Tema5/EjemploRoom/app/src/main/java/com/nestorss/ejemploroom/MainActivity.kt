package com.nestorss.ejemploroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.nestorss.ejemploroom.ui.theme.EjemploRoomTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    lateinit var basedatos: TareasDatabase
    lateinit var todas: List<TareaEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        basedatos = Room.databaseBuilder(
            this,
            TareasDatabase::class.java,
            "tareas-db"
        ).build()
        runBlocking {
            launch{
                todas = basedatos.tareaDao().getAll()
                var tarea = TareaEntity(
                    name="Tarea 1"
                )
                basedatos.tareaDao().insert(tarea)
            }
        }
        enableEdgeToEdge()
        setContent {
            EjemploRoomTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EjemploRoomTheme {
        Greeting("Android")
    }
}