package com.example.contactosjpc.ui.views

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.example.contactosjpc.R
import com.example.contactosjpc.dal.ContactoEnt
import com.example.contactosjpc.dal.ContactosDB
import com.example.contactosjpc.ui.views.ListaContactos.Companion.database
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contactosjpc.ui.theme.ContactosJPCTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Class listaContactos: lista de contactos
 * @author Nestor Sanchez
 */
class ListaContactos : ComponentActivity() {
    companion object {
        lateinit var database: ContactosDB
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Room.databaseBuilder(
            applicationContext,
            ContactosDB::class.java,
            "contactos-db"
        )
            .fallbackToDestructiveMigration()
            .build()
        enableEdgeToEdge()
        setContent {
            ContactosJPCTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "Contactos"
                ) {
                    // Pantalla de inicio
                    composable("Contactos") {
                        ItemList (
                            navController
                        )
                    }
                    composable("AñadirContacto") {
                        AniadirContacto(
                            navController
                        )
                    }
                }
            }
        }
    }
}

/**
 * ItemList: Establece dos columnas para poner ahi todos los contactos
 */
@Composable
fun ItemList(navController: NavController) {

    val listaContactos = remember { mutableStateListOf<ContactoEnt>() }
    LaunchedEffect(Unit) {
        listaContactos.addAll(database.contactoDao().getTodo())
    }
    if (listaContactos.isEmpty()) {
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally, // Centrado horizontalmente
            verticalArrangement = Arrangement.Center // Centrado verticalmente
        ) {
            // Espaciador para centrar elementos
            Spacer(modifier = Modifier.weight(0.00001f))
            // Texto que indica que no hay contactos
            Text (
                text = "No hay contactos",
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    navController.navigate("AñadirContacto")
                },
                modifier = Modifier.padding(16.dp),
            ) {
                Text(
                    text = "Añadir contacto",
                    fontSize = 18.sp,
                )
            }
            // Espaciador para centrar elementos
            Spacer(modifier = Modifier.weight(0.00001f))
        }
    } else {

        Column {
            Button(
                onClick = {
                    navController.navigate("AñadirContacto")
                },
                modifier = Modifier.padding(16.dp),
            ) {
                Text(
                    text = "Añadir contacto",
                    fontSize = 18.sp,
                )
            }

            // LazyVerticalGrid para mostrar elementos en dos columnas
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // Establece el número de columnas (2)
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(listaContactos) { contacto ->
                    ContactoView(contacto) // Card con toda la informacion del contacto
                }
            }
        }
    }
}

/**
 * Establece cada Card con la imagen y la informacion del contacto
 */
@SuppressLint("SuspiciousIndentation")
@Composable
fun ContactoView(contacto: ContactoEnt) {

    // var context: contexto de la aplicacion
    val context = LocalContext.current

    // var foto: foto de un hombre o una mujer dependiendo de la foto escogida
    var foto = R.drawable.muiller
        if (contacto.foto == "Foto 1") {
            foto = R.drawable.onvre
        }

    // var textoModificado: variable que contiene la informacion de un contacto para plasmarla en la Card respectiva
    var textoModificado by remember {mutableStateOf(contacto.nombre.first().toString())}

    // var numTel: variable donde se guarda el numero de telefono del contacto para poder realizar llamadas
    var numTel by remember {mutableStateOf("")}

    // var mostrarTextoNuevo: variable que sirve para detectar si el texto es el acortado o el completo
    var mostrarTextoNuevo by remember { mutableStateOf(false)}

    // var animacion: variable que indica si se esta realizando una animacion o no
    var animacion by remember { mutableStateOf(false)}

    // var permisoLlamada: variable que indica si la aplicacion tiene permiso de llamada o no
    var permisoLlamada by remember { mutableStateOf(false)}

    // val darPermisosLlamada: codigo que llama al gestor de permisos para permitir o no, en este caso, la funcion de llamadas
    val darPermisosLlamada = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        permisoLlamada = isGranted
    }

    // val transicion: transicion usada para cuando se hace clic en un contacto
    val transicion = updateTransition(targetState = animacion, label = "CrossfadeTransition")

    // val tamaño: tamaño de la Card cuando se abre y se cierra
    val tamano: Dp by transicion.animateDp(label = "Size") { state ->
        if (state) 140.dp else 100.dp
    }

    // LaunchedEffect: Verifica el permiso de llamada al iniciar el proceso de intento de llamada
    LaunchedEffect(Unit) {
        permisoLlamada = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CALL_PHONE
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Card: tarjeta con el contacto
    Card(
        modifier = Modifier
            .size(tamano) // Tamaño variable
            .fillMaxWidth()
            .clickable {

                // Al hacer clic, se activa la animacion y se expande o se contrae la tarjeta
                animacion = !animacion
                if (mostrarTextoNuevo) {
                    // Contrae la tarjeta
                    textoModificado = contacto.nombre.first().toString()
                    numTel = ""
                    mostrarTextoNuevo = false
                } else {
                    // Expande la tarjeta
                    textoModificado = contacto.nombre
                    numTel = contacto.telefono.toString()
                    mostrarTextoNuevo = true
                }

            }
    )
    {
        Row {
            // Imagen del contacto
            Image(
                painter = painterResource(id = foto),
                contentDescription = "Foto contacto",
                modifier = Modifier.height(48.dp)
            )
        }
        Row {
            // Nombre del contacto
            Text(
                text = textoModificado,
                fontSize = 24.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
        Row {
            // Numero de telefono del contacto
            Text(
                text = numTel,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        // Revisa si el sistema tiene permisos de llamada
                        if (permisoLlamada) {
                            llamar(context, numTel)
                        } else {
                            darPermisosLlamada.launch(Manifest.permission.CALL_PHONE)
                            Toast.makeText(context, "ERROR: el sistema no tiene permisos de llamada. Activalos, por favor.", Toast.LENGTH_SHORT).show()
                        }
                    }
            )
        }
    }
}

@Composable
fun AniadirContacto(navController: NavController) {

    // var context: contexto de la aplicacion
    val context = LocalContext.current

    // CoroutineScope para ejecutar funciones de BD
    val coroutineScope = rememberCoroutineScope()

    var usuario by remember { mutableStateOf("") }
    var apellidos by remember { mutableStateOf("") }
    var telefono by remember { mutableLongStateOf(0) }
    var foto by remember { mutableStateOf("Foto 1") }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally, // Centrado horizontalmente
        verticalArrangement = Arrangement.Center // Centrado verticalmente
    ) {
        // Espaciador para centrar elementos
        Spacer(modifier = Modifier.weight(0.00001f))
        // Texto: inicio de sesion
        Text(
            text = "Añadir contacto",
            fontSize = 36.sp,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Center,
        )
        TextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = { Text("Usuario") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        TextField(
            value = apellidos,
            onValueChange = { apellidos = it },
            label = { Text("Apellidos") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        TextField(
            value = telefono.toString(),
            onValueChange = { telefono = it.toLong() },
            label = { Text("Telefono") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        Text(
            text = "Selecciona una foto:",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
        ) {
            RadioButton(
                selected = foto == "Foto 1",
                onClick = { foto = "Foto 1" }
            )
            Text(
                text = "Foto 1",
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
        ) {
            RadioButton(
                selected = foto == "Foto 2",
                onClick = { foto = "Foto 2" }
            )
            Text(
                text = "Foto 2",
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Button(
            onClick = {
                aniadirFila(usuario, apellidos, telefono, foto, coroutineScope, context)
            },
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "Añadir contacto",
                fontSize = 18.sp,
            )
        }
        Button(
            onClick = {
                navController.navigate("Contactos")
            },
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "Volver",
                fontSize = 18.sp,
            )
        }
        // Espaciador para centrar elementos
        Spacer(modifier = Modifier.weight(0.00001f))
    }
}

/**
 * Funcion llamar: llama al numero que has pulsado
 */
private fun llamar(context: android.content.Context, numTel: String) {
    val intent = Intent(Intent.ACTION_CALL)
    intent.data = Uri.parse("tel:$numTel")
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "ERROR: el sistema no tiene permisos de llamada. La llamada no se realizara.", Toast.LENGTH_SHORT).show()
    }
}

private fun aniadirFila(nombre: String, apellidos: String, telefono: Long, foto: String, coroutineScope: CoroutineScope, context: android.content.Context) {
    coroutineScope.launch {
        if (database.contactoDao().getNombre(nombre)) {
            Toast.makeText(context, "ERROR: el contacto ya existe.", Toast.LENGTH_SHORT).show()
        } else if (nombre == "" || apellidos == "" || telefono.toInt() == 0) {
            Toast.makeText(context, "ERROR: uno de los campos estan vacios.", Toast.LENGTH_SHORT).show()
        } else {
            val contacto = ContactoEnt()
            contacto.nombre = nombre
            contacto.apellidos = apellidos
            contacto.telefono = telefono
            contacto.foto = foto
            database.contactoDao().insertar(contacto)
            Toast.makeText(context, "El contacto se ha añadido correctamente.", Toast.LENGTH_SHORT).show()
        }
    }
}