package com.nestorss.calculadoranew

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nestorss.calculadoranew.ui.theme.CalculadoraNewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraNewTheme {
                val navController = rememberNavController()
                CalculadoraNewTheme {
                    NavHost(
                        navController = navController,
                        startDestination = "Login"
                    ) {
                        composable("Login") {
                            PantallaLogin (
                                navController
                            )
                        }
                        composable("Calculadora") {
                            PantallaCalculadora (
                                navController
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PantallaLogin(navController: NavController) {

    // var context: contexto de la aplicacion
    val context = LocalContext.current

    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // Espaciador 1
        Spacer(modifier = Modifier.weight(0.00001f))

        // TextFields: usuario y contraseña
        var text by rememberSaveable { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        // Usuario
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Usuario") },
        )

        // Espaciador 2
        Spacer(modifier = Modifier.height(12.dp))

        // Contraseña
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        // Espaciador 3
        Spacer(modifier = Modifier.height(12.dp))

        // Boton: boton de acceso a la calculadora
        Button(
            onClick = {
                if (text == "nestorss" && password == "nestorss") {
                    navController.navigate("Calculadora")
                } else {
                    toaster(context, 1)
                }

            },
            modifier = Modifier.padding(16.dp),
        ) {

            // Texto del botón
            Text(
                text = "Iniciar sesion",
                fontSize = 18.sp,
            )
        }

        // Espaciador 4
        Spacer(modifier = Modifier.weight(0.00001f))
    }
}

@Composable
fun PantallaCalculadora(navController: NavController) {

    // var context: contexto de la aplicacion
    val context = LocalContext.current

    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(0.00001f))
        Text(
            text = "Usuario",
            fontSize = 40.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(12.dp))

        var num1 by rememberSaveable { mutableStateOf("") }
        var num2 by rememberSaveable { mutableStateOf("") }
        var res by rememberSaveable { mutableStateOf("") }

        TextField(
            value = num1,
            onValueChange = { num1 = it },
            label = { Text("Numero 1") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = num2,
            onValueChange = { num2 = it },
            label = { Text("Numero 2") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            // Boton de suma
            Button(
                onClick = {
                    if (num1 == "" || num2 == "") {
                        toaster(context, 3)
                    } else {
                        res = operaciones(context, num1.toFloat(), num2.toFloat(), 1)
                    }
                },
                modifier = Modifier.padding(16.dp),
            ) {
                // Texto del botón
                Text(
                    text = "+",
                    fontSize = 18.sp,
                )
            }

            // Boton de resta
            Button(
                onClick = {
                    if (num1 == "" || num2 == "") {
                        toaster(context, 3)
                    } else {
                        res = operaciones(context, num1.toFloat(), num2.toFloat(), 2)
                    }
                },
                modifier = Modifier.padding(16.dp),
            ) {
                // Texto del botón
                Text(
                    text = "-",
                    fontSize = 18.sp,
                )
            }

            // Boton de multiplicado
            Button(
                onClick = {
                    if (num1 == "" || num2 == "") {
                        toaster(context, 3)
                    } else {
                        res = operaciones(context, num1.toFloat(), num2.toFloat(), 3)
                    }
                },
                modifier = Modifier.padding(16.dp),
            ) {
                // Texto del botón
                Text(
                    text = "*",
                    fontSize = 18.sp,
                )
            }

            // Boton de division
            Button(
                onClick = {
                    if (num1 == "" || num2 == "") {
                        toaster(context, 3)
                    } else {
                        res = operaciones(context, num1.toFloat(), num2.toFloat(), 4)
                    }
                },
                modifier = Modifier.padding(16.dp),
            ) {
                // Texto del botón
                Text(
                    text = "/",
                    fontSize = 18.sp,
                )
            }
        }

        Text(
            text = res,
            fontSize = 40.sp,
            color = Color.Red
        )

        Spacer(modifier = Modifier.weight(0.00001f))
    }

}

private fun toaster(context: android.content.Context, code: Int) {
    var mensaje = "";
    when (code) {
        1 -> {
            mensaje = "ERROR: contraseña incorrecta (C: 00)"
        }
        2 -> {
            mensaje = "ERROR: no se puede dividir entre 0 (C: 01)"
        }
        3 -> {
            mensaje = "ERROR: uno de los valores son nulos (C: 02)"
        }
    }
    Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
}

private fun operaciones(context: android.content.Context, num1: Float, num2: Float, opc: Int): String {
    var res = "";
    when (opc) {
        1 -> {
            res = (num1 + num2).toString();
        }
        2 -> {
            res = (num1 - num2).toString();
        }
        3 -> {
            res = (num1 * num2).toString();
        }
        4 -> {
            if (num2 == 0f) {
                res = "Resultado indefinido"
                toaster(context, 2);
            } else {
                res = (num1 / num2).toString();
            }
        }
    }
    return res;
}