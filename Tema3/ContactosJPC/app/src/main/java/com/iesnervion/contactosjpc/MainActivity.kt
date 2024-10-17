package com.iesnervion.contactosjpc

import Pantalla1
import Pantalla2
import Pantalla3
import Pantalla4
import Pantalla5
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iesnervion.contactosjpc.ui.theme.LightBlue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            var modifier = Modifier.fillMaxSize().background(LightBlue).padding(50.dp)
            Column(
                modifier=modifier,
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                NavHost(
                    navController = navController,
                    startDestination = "pantalla1"
                ) {
                    composable(route="pantalla1") { Pantalla1(navController,modifier) }
                    composable(route="pantalla2") { Pantalla2(navController,modifier) }
                    composable(route="pantalla3") { Pantalla3(navController,modifier) }
                    composable(route="pantalla4") { Pantalla4(navController,modifier) }
                    composable(route="pantalla5") { Pantalla5(navController,modifier) }
                }
            }

        }
    }

}




