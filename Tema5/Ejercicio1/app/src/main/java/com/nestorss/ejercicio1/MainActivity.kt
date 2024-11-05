package com.nestorss.ejercicio1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.nestorss.ejercicio1.dal.TareasDatabase
import com.nestorss.ejercicio1.ui.theme.Ejercicio1Theme
import com.nestorss.ejercicio1.ui.views.miApp

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var database: TareasDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Room.databaseBuilder(
            applicationContext,
            TareasDatabase::class.java,
            "tareas-db"
        ).build()

        enableEdgeToEdge()
        setContent {
            Ejercicio1Theme {
                miApp()
            }
        }
    }
}
