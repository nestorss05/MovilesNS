import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Botonera(navController: NavController, pantallaActual: String) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom,

        ) {
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            enabled = pantallaActual != "pantalla1",
            onClick = {
                navController.navigate("pantalla1")
            }
        ) {
            Text("1")
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            enabled = pantallaActual != "pantalla2",
            onClick = {
                navController.navigate("pantalla2")
            }) {
            Text("2")
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            enabled = pantallaActual != "pantalla3",
            onClick = {
                navController.navigate("pantalla3")
            }) {
            Text("3")
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            enabled = pantallaActual != "pantalla4",
            onClick = {
                navController.navigate("pantalla4")
            }) {
            Text("4")
        }
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
fun Pantalla1(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End,
    ) {
        Text("Pantalla 1")
        Botonera(navController = navController, "pantalla1")
    }
}

@Composable
fun Pantalla2(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End,
    ) {
        Text("Pantalla 2")
        Botonera(navController = navController, "pantalla2")
    }
}

@Composable
fun Pantalla3(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End,
    ) {
        Text("Pantalla 3")
        Botonera(navController = navController, "pantalla3")
    }
}

@Composable
fun Pantalla4(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End,
    ) {
        Text("Pantalla 4")
        Row {
            Button(onClick = {
                navController.navigate("pantalla3")
            }) {
                Text("3")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                navController.navigate("pantalla5")
            }) {
                Text("Pantalla 5")
            }
        }
    }
}

@Composable
fun Pantalla5(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End,
    ) {
        Text(
            text = "5"
        )
        Button(onClick = {
            navController.navigate("pantalla4")
        }) {
            Text("4")
        }
    }
}