package com.nestorss.t3_ejercicio4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.nestorss.t3_ejercicio4.ui.theme.T3_Ejercicio4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            T3_Ejercicio4Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Main(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Main(modifier: Modifier = Modifier) {
    var modoOscuro by rememberSaveable { mutableStateOf(false) }
    var texto by rememberSaveable { mutableStateOf("")}
    var textoBtn by rememberSaveable { mutableStateOf("")}
    if (modoOscuro) {
        texto = "Maldito"
        textoBtn = "Desactivar modo oscuro"
        // https://www.youtube.com/watch?v=UXJes1qgbkk
    } else {
        texto = "Desmaldito"
        textoBtn = "Activar modo oscuro"
    }
    Column (
        modifier = modifier
    ) {
        Button(
            onClick = {modoOscuro = !modoOscuro}
        ) {
            Text(
                text = textoBtn,
            )
        }
        Text (
            text = texto
        )
    }

}