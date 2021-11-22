package com.example.poi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
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

        val switchEncriptado = findViewById<Switch>(R.id.switch1)

        switchEncriptado.isChecked = StaticUser.staticUser.isEncripted

        switchEncriptado.setOnClickListener {
            if(switchEncriptado.isChecked) {
                StaticUser.staticUser.isEncripted = true
                val reference = database.getReference("/usuarios/${StaticUser.staticUser.id}")
                reference.setValue(StaticUser.staticUser)
            }
            else {
                StaticUser.staticUser.isEncripted = false
                val reference = database.getReference("/usuarios/${StaticUser.staticUser.id}")
                reference.setValue(StaticUser.staticUser)
            }
        }
    }
}