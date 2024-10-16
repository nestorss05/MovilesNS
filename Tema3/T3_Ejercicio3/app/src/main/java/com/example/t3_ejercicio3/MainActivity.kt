package com.example.t3_ejercicio3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.t3_ejercicio3.ui.theme.T3_Ejercicio3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            T3_Ejercicio3Theme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
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
    Column (
        modifier = modifier
    ){
        Row {
            Image(
                painter = painterResource(id = R.drawable.covid),
                contentDescription = "Xd",
                modifier = Modifier.size(120.dp).padding(horizontal = 8.dp)
            )
            Column (
                modifier = Modifier.padding(horizontal = 8.dp)
            ){
                Text(
                    text = "Nestor Sanchez",
                    fontSize = 36.sp,
                )
                Text(
                    text = "18, Sevilla, España",
                    fontSize = 20.sp,
                )
            }
        }
        Row {
            Button (
                onClick = {},
                modifier = Modifier.padding(16.dp),
            ) {
                Text(
                    text = "Editar perfil",
                    fontSize = 18.sp,
                )
            }
            Button (
                onClick = {},
                modifier = Modifier.padding(16.dp),
            ) {
                Text(
                    text = "Cerrar sesion",
                    fontSize = 18.sp,
                )
            }
        }
    }
}
