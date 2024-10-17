package com.example.navpantallas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navpantallas.ui.theme.NavPantallasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavPantallasTheme {
                NavHost(
                    navController = navController,
                    startDestination = "Greeting"
                ) {
                    composable("Greeting") {
                        Greeting (
                            navController,
                            name = "Nestor"
                        )
                    }
                    composable("OtraPantalla") {
                        OtraPantalla(
                            navController,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(navController: NavController, name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
    Button(
        onClick = {navController.navigate("otraPantalla")}
    ) {
        Text(text = "Xd")
    }
}

@Composable
fun OtraPantalla(navController: NavController, modifier: Modifier = Modifier) {
    Text(
        text = "Esquizofrenia",
        modifier = modifier
    )
}