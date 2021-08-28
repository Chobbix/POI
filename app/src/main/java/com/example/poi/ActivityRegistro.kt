package com.example.poi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityRegistro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val btnConfirmar: Button = findViewById(R.id.btn_ConfirmarReg)

        btnConfirmar.setOnClickListener {
            val chatIntent = Intent(this, ActivityChat::class.java)
            startActivity(chatIntent)
        }
    }
}