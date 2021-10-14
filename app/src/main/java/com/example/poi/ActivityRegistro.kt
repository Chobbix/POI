package com.example.poi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.poi.modelos.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ActivityRegistro : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val authen = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val btnConfirmar: Button = findViewById(R.id.btn_ConfirmarReg)
        val editNombre: EditText = findViewById(R.id.edit_RegNomCom)
        val editUsuario: EditText = findViewById(R.id.edit_RegUser)
        val editTel: EditText = findViewById(R.id.edit_RegTelefono)
        val editEmail: EditText = findViewById(R.id.edit_RegEmail)
        val editContra: EditText = findViewById(R.id.edit_RegPassword)

        btnConfirmar.setOnClickListener {
            val txtNombre = editNombre.text.toString()
            val txtUsuario = editUsuario.text.toString()
            val txtTel = editTel.text.toString()
            val txtEmail = editEmail.text.toString()
            val txtContra = editContra.text.toString()

            val newUsuario = Usuario("", txtNombre, txtUsuario, txtTel, txtEmail, txtContra)

            crearUsuarioAuthentication(newUsuario.email, newUsuario.contrasenia)
            crearUsuarioDatabase(newUsuario)

            //val chatIntent = Intent(this, ActivityChat::class.java)
            val chatIntent = Intent(this, ActivityChatGrupal::class.java)
            startActivity(chatIntent)
        }
    }

    private fun crearUsuarioDatabase(user: Usuario){
        val uid = authen.uid ?: ""
        val userRef = database.getReference("/usuarios/$uid")

        user.id = uid
        userRef.setValue(user)
    }

    private fun crearUsuarioAuthentication(email: String, password: String){
        authen.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                Log.d("Main", "Creado con exito el usuario")
            }
            .addOnFailureListener {
                Log.d("Main", "No se pudo crear el usuario: ${it.message}")
            }
    }
}