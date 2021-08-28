package com.example.poi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.poi.adaptadores.ChatAdaptador

class ActivityChat : AppCompatActivity() {

    private val chatAdaptador = ChatAdaptador()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val rvMensajes = findViewById<RecyclerView>(R.id.rvMsgRecibidos)
        rvMensajes.adapter = chatAdaptador
    }
}