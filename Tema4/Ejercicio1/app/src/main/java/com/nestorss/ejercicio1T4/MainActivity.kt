package com.nestorss.ejercicio1T4

import android.bluetooth.BluetoothManager
import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.nfc.NfcAdapter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getSystemService
import com.nestorss.ejercicio1T4.ui.theme.Ejercicio1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ejercicio1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Main(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Main(modifier: Modifier = Modifier) {
    val context: Context = LocalContext.current
    val isWifiConnected = wifiActivo(context)
    val isBTConnected = btActivo(context)
    val isNFCConnected = nfcActivo(context)
    val isGPSConnected = gpsActivo(context)
    val isInternesConnected = hayInternes(context)
    var textoWifi = ""
    var textoBT = ""
    var textoNFC = ""
    var textoGPS = ""
    var textoInternes = ""

    if (isWifiConnected) {
        textoWifi = "Conectado"
    } else {
        textoWifi = "Desconectado"
    }

    if (isBTConnected) {
        textoBT = "Conectado"
    } else {
        textoBT = "Desconectado"
    }

    if (isNFCConnected) {
        textoNFC = "Conectado"
    } else {
        textoNFC = "Desconectado"
    }

    if (isGPSConnected) {
        textoGPS = "Conectado"
    } else {
        textoGPS = "Desconectado"
    }

    if (isInternesConnected) {
        textoInternes = "Conectado"
    } else {
        textoInternes = "Desconectado"
    }

    Column (
        modifier = modifier
    )
    {
        Text(
            text = "WIFI: $textoWifi",
        )

        Text(
            text = "Bluetooth: $textoBT",
        )

        Text(
            text = "NFC: $textoNFC",
        )

        Text(
            text = "GPS: $textoGPS",
        )

        Text(
            text = "Internet: $textoInternes",
        )
    }

}

fun wifiActivo(context: Context): Boolean {
    val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
    return wifiManager.isWifiEnabled
}

fun btActivo(context: Context): Boolean {
    val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    val bluetoothAdapter = bluetoothManager.adapter
    return bluetoothAdapter != null && bluetoothAdapter.isEnabled
}

fun nfcActivo(context: Context): Boolean {
    val nfcAdapter: NfcAdapter? = NfcAdapter.getDefaultAdapter(context)
    return nfcAdapter != null && nfcAdapter.isEnabled
}

fun gpsActivo(context: Context): Boolean {
    val locationManager = getSystemService(context, LocationManager::class.java) as LocationManager
    return locationManager.isLocationEnabled
}

fun hayInternes(context: Context): Boolean {
    val connectivityManager = getSystemService(context, ConnectivityManager::class.java) as ConnectivityManager
    val networkCapabilities = connectivityManager.getNetworkCapabilities(
        connectivityManager.activeNetwork
    )
    return networkCapabilities?.hasCapability(
        NetworkCapabilities.NET_CAPABILITY_INTERNET
    ) == true
}