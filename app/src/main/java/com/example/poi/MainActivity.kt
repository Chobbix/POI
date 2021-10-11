package com.example.poi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
        val editEmail: EditText = findViewById(R.id.edit_IniEmail)
        val editPass: EditText = findViewById(R.id.edit_IniPassword)


        btnInicioSesion.setOnClickListener {


            val txtEmail = editEmail.text.toString()
            val txtPass = editPass.text.toString()

            Toast.makeText(this, BuscarUsuario(txtEmail, txtPass), Toast.LENGTH_LONG).show()

            //val chatIntent = Intent(this, ActivityChat::class.java)
            //startActivity(chatIntent)
        }

        btnRegistrar.setOnClickListener {
            val registroIntent = Intent(this, ActivityRegistro::class.java)
            startActivity(registroIntent)
        }
    }

    private fun BuscarUsuario(email: String, password: String): String {
        var isExist = false
        var nombre = ""

        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(snap in snapshot.children) {
                    nombre = snap.child("email").getValue().toString()
                    if(snap.child("email").equals(email)){
                        if(snap.child("contrasenia").equals(password)){
                            isExist = true
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        return nombre
    }
}