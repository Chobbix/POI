package com.example.poi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.poi.modelos.Mensaje
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val authen = FirebaseAuth.getInstance()

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

            authen.signInWithEmailAndPassword(txtEmail, txtPass)
                .addOnCompleteListener {
                    Log.d("Main", "Usuario encontrado")
                }
                .addOnFailureListener {
                    Log.d("Main", "No se pudo encontrar el usuario: ${it.message}")
                }

            val chatIntent = Intent(this, ActivityUserChats::class.java)
            startActivity(chatIntent)
        }

        btnRegistrar.setOnClickListener {
            val registroIntent = Intent(this, ActivityRegistro::class.java)
            startActivity(registroIntent)
        }
    }
}