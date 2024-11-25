package com.nestorss.loginv2

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.nestorss.loginv2.ui.theme.LoginV2Theme

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var auth: FirebaseAuth
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        auth = Firebase.auth
        setContent {
            LoginV2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "Login"
                    ) {
                        composable("Login") {
                            super.onStart()
                            // Check if user is signed in (non-null) and update UI accordingly.
                            val currentUser = auth.currentUser
                            if (currentUser != null) {
                                Welcome(
                                    modifier = Modifier.padding(innerPadding),
                                    navController
                                )
                            } else {
                                Login(
                                    modifier = Modifier.padding(innerPadding),
                                    navController
                                )
                            }
                        }
                        composable("Welcome") {
                            Welcome(
                                modifier = Modifier.padding(innerPadding),
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
fun Login(modifier: Modifier = Modifier, navController: NavController) {
    val context = LocalContext.current
    Column (
        modifier = modifier .fillMaxWidth(),
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
        var correo by rememberSaveable { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        // Correo
        TextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo") },
        )

        // Contraseña
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        // Boton: boton de registro
        Button(
            onClick = {
                if (correo != "" && password != "") {
                    registrar(correo, password, context)
                } else {
                    Toast.makeText(
                        context,
                        "ERROR: los campos estan vacios",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            },
            modifier = Modifier.padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = Color.White
            )
        ) {
            // Texto del botón
            Text(
                text = "Registrar",
                fontSize = 18.sp,
                color = Color.White
            )
        }

        // Boton: boton de inicio de sesion
        Button(
            onClick = {
                if (correo != "" && password != "") {
                    inicioSesion(correo, password, navController, context)
                } else {
                    Toast.makeText(
                        context,
                        "ERROR: los campos estan vacios",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
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

        // Boton: boton de inicio de sesion
        Button(
            onClick = {
                if (correo != "" && password != "") {
                    // TODO: Login por Gulu-gulu
                    Toast.makeText(
                        context,
                        "ERROR: no implementado. Intentelo mas tarde.",
                        Toast.LENGTH_SHORT,
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "ERROR: los campos estan vacios",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            },
            modifier = Modifier.padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = Color.White
            )
        ) {
            // Texto del botón
            Text(
                text = "WIP: Iniciar sesion por Google",
                fontSize = 18.sp,
                color = Color.White
            )
        }

        // Espaciador que centra el texto (este lo echa para abajo)
        Spacer(modifier = Modifier.weight(0.00001f))

    }

}

@Composable
fun Welcome(modifier: Modifier = Modifier, navController: NavController) {
    Column (
        modifier = modifier .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally, // Centrado horizontalmente
        verticalArrangement = Arrangement.Center // Centrado verticalmente
    ) {

        // Espaciador que centra el texto (este lo echa para abajo)
        Spacer(modifier = Modifier.weight(0.00001f))

        // Texto: inicio de sesion
        Text(
            text = "Benvenutti todo el mundi a la mafia italiana",
            fontSize = 24.sp,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
        )

        // Boton: boton de inicio de sesion
        Button(
            onClick = {
                cerrarSesion(navController);
            },
            modifier = Modifier.padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.DarkGray,
                contentColor = Color.White
            )
        ) {
            // Texto del botón
            Text(
                text = "Cerrar sesion",
                fontSize = 18.sp,
                color = Color.White
            )
        }

        // Espaciador que centra el texto (este lo echa para abajo)
        Spacer(modifier = Modifier.weight(0.00001f))

    }

}

fun registrar(correo: String, password: String, baseContext: Context) {
    MainActivity.auth.createUserWithEmailAndPassword(correo, password)
        .addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "createUserWithEmail:success")
                val user = MainActivity.auth.currentUser
                Toast.makeText(
                    baseContext,
                    "Usuario $user registrado correctamente. Puede hacer clic en iniciar sesion.",
                    Toast.LENGTH_SHORT,
                ).show()
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                Toast.makeText(
                    baseContext,
                    "ERROR: el usuario ya existe",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
}

fun inicioSesion(correo: String, password: String, navController: NavController, baseContext: Context) {
    MainActivity.auth.signInWithEmailAndPassword(correo, password)
        .addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithEmail:success")
                val user = MainActivity.auth.currentUser
                Toast.makeText(
                    baseContext,
                    "logged as " + user?.email,
                    Toast.LENGTH_SHORT,
                ).show()
                navController.navigate("Welcome")
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.exception)
                Toast.makeText(
                    baseContext,
                    "Authentication failed.",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
}

fun cerrarSesion(navController: NavController) {
    MainActivity.auth.signOut()
    navController.navigate("Login")
}