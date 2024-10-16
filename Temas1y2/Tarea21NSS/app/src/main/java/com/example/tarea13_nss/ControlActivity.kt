package com.example.tarea13_nss

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ControlActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()
        Log.d("Creado", "Proceso creado")
        val usuario = intent.getStringExtra("usuario")
        val password = intent.getStringExtra("password")
        var acceso = Intent(this, MainActivity::class.java)
        if (usuario == "nestorss" && password == "nestorss") {
            acceso = Intent(this, CalculatorActivity::class.java)
        }
        acceso.putExtra("usuario", usuario)
        startActivity(acceso)
        finish()

    }
}