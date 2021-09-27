package com.example.poi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.poi.modelos.Mensaje
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val userRef = database.getReference("usuarios")

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

    private fun BuscarUsuario(email: String, password: String) {

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(snap in snapshot.children) {
                    if(snap.child("email").equals(email)){
                        if(snap.child("contrasenia").equals(password)){

                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}