package com.example.tarea13_nss

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("Creado", "Proceso creado")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Creado", "Proceso iniciado")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Creado", "Proceso reanudado")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Creado", "Proceso pausado")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Creado", "Proceso parado")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Creado", "Proceso reiniciado")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Creado", "Proceso terminado")
    }

}