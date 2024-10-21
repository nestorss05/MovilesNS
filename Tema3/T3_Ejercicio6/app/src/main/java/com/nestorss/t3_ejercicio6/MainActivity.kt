package com.nestorss.t3_ejercicio6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nestorss.t3_ejercicio6.ui.theme.T3_Ejercicio6Theme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            T3_Ejercicio6Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Cronometro(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Cronometro(modifier: Modifier = Modifier) {

    var milisegundos by remember { mutableIntStateOf(0) }
    var segundos by remember { mutableIntStateOf (0) }
    var minutos by remember { mutableIntStateOf(0) }
    var horas by remember { mutableIntStateOf(0) }
    var tiempoColor by remember {mutableStateOf(Color.Red)}
    var estaEjecutando by remember {mutableStateOf(false)}

    LaunchedEffect(estaEjecutando) {
        while (estaEjecutando) {
            delay(1L)
            milisegundos++
            if (milisegundos == 1000) {
                segundos++
                milisegundos = 0
            }
            if (segundos == 60) {
                minutos++
                segundos = 0
            }
            if (minutos == 60) {
                horas++
                minutos = 0
            }
        }
    }

    if (estaEjecutando) {
        tiempoColor = MaterialTheme.colorScheme.primary
    } else {
        tiempoColor = Color.Red
    }

    Column(
        modifier = modifier .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.weight(0.00001f))

        Text(
            text = String.format("%02d:%02d:%02d.%03d", horas, minutos, segundos, milisegundos),
            fontSize = 48.sp,
            textAlign = TextAlign.Center,
            color = tiempoColor
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button (
                modifier = Modifier.padding(4.dp),
                onClick = {
                    estaEjecutando = false;
                }
            ) {
                Text(
                    text = "Pausar",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
            Button (
                modifier = Modifier.padding(4.dp),
                onClick = {
                    estaEjecutando = true;

                }
            ) {
                Text(
                    text = "Renaudar",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
            Button (
                modifier = Modifier.padding(4.dp),
                onClick = {
                    if (!estaEjecutando) {
                        horas = 0
                        minutos = 0
                        segundos = 0
                        milisegundos = 0
                    }
                }
            ) {
                Text(
                    text = "Reiniciar",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.weight(0.00001f))

    }
}