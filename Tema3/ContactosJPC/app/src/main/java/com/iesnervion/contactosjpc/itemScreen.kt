package com.iesnervion.contactosjpc

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@Composable
fun ItemList(
    itemContacto: List<Contacto>,
    navController: NavHostController,
) {
    LazyColumn(

    ) {
        items(itemContacto) { itemContacto ->
            ContactoView(
                contacto = itemContacto,
                rojo = 35,
                navController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactoView(contacto: Contacto, rojo: Int, navController: NavHostController, modifier: Modifier = Modifier) {
    var text by rememberSaveable { mutableStateOf("Hello") }
    var password by remember { mutableStateOf("") }

    Card(Modifier.fillMaxWidth()) {
        Row {

            Column {
                Text(
                    text = contacto.nombre,
                    fontSize = 48.sp,
                    modifier = Modifier.padding(8.dp),
                    color = Color(rojo * 3, 27, 159, 255),
                )
            }
            Column(
                modifier = Modifier.height(100.dp),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = contacto.tfno,
                    fontSize = 36.sp,
                    modifier = Modifier.padding(8.dp),
                    color = Color(rojo, 131, 9, 255),
                    textAlign = TextAlign.Right
                )
            }
            Column {
                val context = LocalContext.current
                Button(
                    modifier = Modifier.padding(16.dp),
                    onClick = {
                        Toast.makeText(context, text + "/" + password, Toast.LENGTH_SHORT).show()
                        navController.navigate("main2")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.DarkGray,
                        contentColor = Color.White
                    )
                ) {
                    // Texto del botón
                    Text(
                        text = "Púlsame",
                        fontSize = 18.sp, // Tamaño de fuente
                        color = Color.White
                    )
                }
            }
        }
        Row {
            Column {
                AsyncImage(
                    model = "https://cdn.pixabay.com/photo/2018/04/26/12/14/travel-3351825_1280.jpg",
                    contentDescription = "Mi buga",
                    Modifier.size(100.dp, 100.dp)
                )
            }
            Column {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Usuario") }
                )
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

            }
        }
    }
}