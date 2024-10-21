package com.nestorss.t3_ejercicio5

import android.os.Bundle
import android.provider.CalendarContract.Colors
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.nestorss.t3_ejercicio5.ui.theme.T3_Ejercicio5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var modoOscuro by rememberSaveable { mutableStateOf(false) }
            var texto by rememberSaveable { mutableStateOf("") }
            var textoBtn by rememberSaveable { mutableStateOf("") }
            var colorTextoBoton by remember { mutableStateOf(Color.Yellow) }
            var colorFondo by remember { mutableStateOf(Color.Blue) }
            var colorTexto by remember { mutableStateOf(Color.Red)}
            T3_Ejercicio5Theme (
                darkTheme = modoOscuro
            ){
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .statusBarsPadding(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    if (modoOscuro) {
                        texto = "Modo oscuro activado"
                        textoBtn = "Desactivar modo oscuro"
                        colorTextoBoton = Color.Blue
                        colorTexto = Color.Red
                        colorFondo = Color.Yellow
                    } else {
                        texto = "Modo oscuro desactivado"
                        textoBtn = "Activar modo oscuro"
                        colorTextoBoton = Color.Yellow
                        colorTexto = MaterialTheme.colorScheme.primary
                        colorFondo = Color.Blue
                    }
                    Column (
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Button(
                            onClick = {modoOscuro = !modoOscuro},
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorFondo
                            )
                        ) {
                            Text(
                                text = textoBtn,
                                color = colorTextoBoton
                            )
                        }
                        Text (
                            text = texto,
                            color = colorTexto
                        )
                    }
                }
            }
        }
    }
}