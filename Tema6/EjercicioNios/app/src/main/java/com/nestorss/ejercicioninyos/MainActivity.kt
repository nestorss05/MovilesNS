package com.nestorss.ejercicioninyos

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nestorss.ejercicioninyos.ui.theme.EjercicioNiñosTheme
import com.nestorss.ejercicioninyos.ui.views.Inicio
import com.nestorss.ejercicioninyos.ui.views.VideoPlayerScreen

class MainActivity : ComponentActivity() {

    private lateinit var sensorManager: SensorManager
    private var shakeThreshold = 12f // Umbral de agitación
    private var lastShakeTime: Long = 0
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa el SensorManager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        val sensorListener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    val x = it.values[0]
                    val y = it.values[1]
                    val z = it.values[2]

                    // Calcula la magnitud del movimiento
                    val acceleration = Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat() - SensorManager.GRAVITY_EARTH

                    // Comprueba si se superó el umbral y previene eventos frecuentes
                    if (acceleration > shakeThreshold) {
                        val currentTime = System.currentTimeMillis()
                        if (currentTime - lastShakeTime > 1000) { // Evita múltiples disparos en menos de 1 segundo
                            lastShakeTime = currentTime
                            cristalRoto()
                        }
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                TODO("Sin necesidad de implementacion")
            }
        }

        // Registra el listener del acelerómetro
        accelerometer?.let {
            sensorManager.registerListener(sensorListener, it, SensorManager.SENSOR_DELAY_UI)
        }

        enableEdgeToEdge()
        setContent {
            EjercicioNiñosTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "inicio"
                    ) {
                        composable("inicio") {
                            Inicio (
                                modifier = Modifier.padding(innerPadding),
                                navController = navController
                            )
                        }
                        composable("page/{idAnimal}") { backStackEntry ->
                            val id = backStackEntry.arguments?.getString("idAnimal") ?:"0"
                            val videoResId = when (id.toInt()) {
                                1 -> R.raw.bird1
                                2 -> R.raw.cat
                                3 -> R.raw.cock
                                4 -> R.raw.cow
                                5 -> R.raw.dog
                                6 -> R.raw.eagle
                                7 -> R.raw.lion
                                8 -> R.raw.owl
                                9 -> R.raw.pig
                                else -> R.raw.sheep
                            }
                            val uri = "android.resource://$packageName/$videoResId"
                            VideoPlayerScreen(
                                videoUrl = uri,
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }

                }
            }
        }
    }
    private fun cristalRoto() {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, R.raw.cristalroto)
        mediaPlayer?.start()
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}