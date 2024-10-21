package com.example.tarea13_nss

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CalculatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculator)
        Log.d("Pasado", "Pantalla cambiada")
        val name = findViewById<TextView>(R.id.name)
        val usuario = intent.getStringExtra("usuario")
        name.text = usuario
    }

    override fun onStart() {
        super.onStart()
        val dato1 = findViewById<EditText>(R.id.dato1)
        val dato2 = findViewById<EditText>(R.id.dato2)
        val resultado = findViewById<TextView>(R.id.res)
        val botonVolver = findViewById<ImageButton>(R.id.imageButton)
        val botonSuma = findViewById<Button>(R.id.suma)
        val botonResta = findViewById<Button>(R.id.restar)
        val botonMultip = findViewById<Button>(R.id.multip)
        val botonDividir = findViewById<Button>(R.id.dividir)
        botonVolver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        botonSuma.setOnClickListener {
            if (dato1.text.toString() == null || dato1.text.toString().equals("") || dato2.text.toString() == null || dato2.text.toString().equals("")) {
                Toast.makeText(this, "Uno o mas campos estan vacios", Toast.LENGTH_SHORT).show()
                dato1.setText("0")
                dato2.setText("0")
            } else {
                val num1 = dato1.text.toString().toFloat()
                val num2 = dato2.text.toString().toFloat()
                val res = num1 + num2
                resultado.text = res.toString()
            }

        }
        botonResta.setOnClickListener {
            if (dato1.text.toString() == null || dato1.text.toString().equals("") || dato2.text.toString() == null || dato2.text.toString().equals("")) {
                Toast.makeText(this, "Uno o mas campos estan vacios", Toast.LENGTH_SHORT).show()
                dato1.setText("0")
                dato2.setText("0")
            } else {
                val num1 = dato1.text.toString().toFloat()
                val num2 = dato2.text.toString().toFloat()
                val res = num1 - num2
                resultado.text = res.toString()
            }
        }
        botonMultip.setOnClickListener {
            if (dato1.text.toString() == null || dato1.text.toString().equals("") || dato2.text.toString() == null || dato2.text.toString().equals("")) {
                Toast.makeText(this, "Uno o mas campos estan vacios", Toast.LENGTH_SHORT).show()
                dato1.setText("0")
                dato2.setText("0")
            } else {
                val num1 = dato1.text.toString().toFloat()
                val num2 = dato2.text.toString().toFloat()
                val res = num1 * num2
                resultado.text = res.toString()
            }
        }
        botonDividir.setOnClickListener {
            if (dato1.text.toString() == null || dato1.text.toString().equals("") || dato2.text.toString() == null || dato2.text.toString().equals("")) {
                Toast.makeText(this, "Uno o mas campos estan vacios", Toast.LENGTH_SHORT).show()
                dato1.setText("0")
                dato2.setText("0")
            } else {
                val num1 = dato1.text.toString().toFloat()
                val num2 = dato2.text.toString().toFloat()
                if (num2 == 0f) {
                    Toast.makeText(this, "No es posible la division entre 0", Toast.LENGTH_SHORT).show()
                } else {
                    val res = num1 / num2
                    resultado.text = res.toString()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("Pausado", "Proceso pausado (sesion iniciada)")
        setContentView(R.layout.pausation)
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Reiniciado", "Proceso reiniciado (sesion iniciada)")
        val usuario = intent.getStringExtra("usuario")
        setContentView(R.layout.calculator)
        Toast.makeText(this, "Bienvenido de nuevo, $usuario", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Terminado", "Sesion cerrada. Proceso terminado")
    }

}