package com.example.poi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnInicioSesion: Button = findViewById(R.id.btn_IniciarSesion)
        val btnRegistrar: Button = findViewById(R.id.btn_Registro)

        btnInicioSesion.setOnClickListener {
            val chatIntent = Intent(this, ActivityChat::class.java)

            startActivity(chatIntent)
        }

        btnRegistrar.setOnClickListener {
            val registroIntent = Intent(this, ActivityRegistro::class.java)
            startActivity(registroIntent)
        }
    }
}