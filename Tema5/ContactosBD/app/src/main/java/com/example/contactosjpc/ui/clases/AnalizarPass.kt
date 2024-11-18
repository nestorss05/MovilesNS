package com.example.contactosjpc.ui.clases

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.contactosjpc.MainActivity
import com.example.contactosjpc.ui.views.ListaContactos

/**
 * Clase AnalizarPass: analiza si el usuario y la contraseña son correctos
 * @author Nestor Sanchez
 */
class AnalizarPass : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Valores de usuario y contraseña traidos de la pantalla de login
        val usuario = intent.getStringExtra("usuario")
        val password = intent.getStringExtra("password")

        // Variable de acceso a la pantalla de inicio (solo si el usuario y/o la contraseña son incorrectas)
        var acceso = Intent(this, MainActivity::class.java)

        // If-Else: si el usuario y la contraseña son correctas, el acceso sera a la lista de contactos
        if (usuario == "nestorss" && password == "nestorss") {
            acceso = Intent(this, ListaContactos::class.java)
        } else {
            // De lo contrario, se le informara al usuario de que los datos introducidos son incorrectos
            Toast.makeText(this, "ERROR: nombre de usuario y/o contraseña incorrecta", Toast.LENGTH_SHORT).show()
        } // Fin If-Else

        // Inicia la actividad y termina el proceso
        startActivity(acceso)
        finish()
    }

}