package com.example.poi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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

            crearUsuarioAuthentication(newUsuario.email, newUsuario.contrasenia, newUsuario)

            //val chatIntent = Intent(this, ActivityChat::class.java)
            //val chatIntent = Intent(this, ActivityChatGrupal::class.java)
        }
    }


    private fun crearUsuarioAuthentication(email: String, password: String, newUsuario: Usuario){
        authen.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = authen.currentUser?.uid!!
                    val userRef = database.getReference("/usuarios").child(uid)
                    newUsuario.id = uid
                    userRef.setValue(newUsuario)

                    val UserListIntent = Intent(this, ActivityMenuChats::class.java)
                    startActivity(UserListIntent)
                } else {
                    Toast.makeText(this, "Ha ocurrido un error.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Log.d("Main", "No se pudo crear el usuario: ${it.message}")
            }
    }
}