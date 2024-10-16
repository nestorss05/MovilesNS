package com.example.tarea13_nss

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()
        Log.d("Creado", "Proceso creado")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Iniciado", "Proceso iniciado")
        val myButton = findViewById<Button>(R.id.button)
        myButton.setOnClickListener {
            val intent = Intent(this, ControlActivity::class.java)
            val usuario = findViewById<EditText>(R.id.etUsuario).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()
            intent.putExtra("usuario", usuario)
            intent.putExtra("password", password)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("Resumido", "Proceso resumido")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Pausado", "Proceso pausado")
        setContentView(R.layout.pausation)
    }

    override fun onStop() {
        super.onStop()
        Log.d("Parado", "Proceso parado")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Reiniciado", "Proceso reiniciado")
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Terminado", "Proceso terminado")
    }

}