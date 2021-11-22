package com.example.poi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.poi.estaticos.StaticUser
import com.example.poi.modelos.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ActivityPerfil : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val authen = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val fromId = authen.uid ?: ""

    }
}