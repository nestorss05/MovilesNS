package com.example.contactosjpc

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Clase MainActivity: pantalla de login
 * @author Nestor Sanchez
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            // Agrupa el programa entero en una columna
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centrado horizontalmente
                verticalArrangement = Arrangement.Center // Centrado verticalmente
            ) {

                // Espaciador que centra el texto (este lo echa para abajo)
                Spacer(modifier = Modifier.weight(0.00001f))

                // Texto: inicio de sesion
                Text(
                    text = "Inicio de sesion",
                    fontSize = 48.sp,
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                )

                // TextFields: usuario y contraseña
                var text by rememberSaveable { mutableStateOf("") }
                var password by remember { mutableStateOf("") }

                // Usuario
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Usuario") },
                )

                // Contraseña
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                // Boton: boton de acceso a la lista de contactos
                Button(
                    onClick = {
                        // Intent para acceder a AnalizarPass
                        val intent = Intent(this@MainActivity, AnalizarPass::class.java)
                        intent.putExtra("usuario", text)
                        intent.putExtra("password", password)
                        startActivity(intent)
                    },
                    modifier = Modifier.padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.DarkGray,
                        contentColor = Color.White
                    )
                ) {
                    // Texto del botón
                    Text(
                        text = "Iniciar sesion",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }

                // Espaciador que centra el texto (este lo echa para arriba)
                Spacer(modifier = Modifier.weight(0.00001f))

            }
        }
    }
}