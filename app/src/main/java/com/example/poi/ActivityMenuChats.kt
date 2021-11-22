package com.example.poi

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.poi.estaticos.StaticUser
import com.example.poi.fragmentos.fragmentChatGrupal
import com.example.poi.fragmentos.fragmentChatPrivado
import com.example.poi.modelos.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ActivityMenuChats : AppCompatActivity() {

    private val database = FirebaseDatabase.getInstance()
    private val authen = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_chats)

        val btnChatPriv = findViewById<Button>(R.id.btnChatPrivMenu)
        val btnChatGrupal = findViewById<Button>(R.id.btnChatGrupMenu)
        val tvPerfil = findViewById<TextView>(R.id.tv_Username)
        val switchActivo = findViewById<Switch>(R.id.switchActivo)

        val fromId = authen.currentUser?.uid!!
        getUser(fromId, tvPerfil, switchActivo)

        btnChatPriv.setOnClickListener {
            changeFragments(fragmentChatPrivado(this), "fragment_chatpriv")
            btnChatPriv.textSize = 15.0F
            btnChatGrupal.textSize = 12.0F
        }

        btnChatGrupal.setOnClickListener {
            changeFragments(fragmentChatGrupal(this), "fragment_chatgrupal")
            btnChatGrupal.textSize = 15.0F
            btnChatPriv.textSize = 12.0F
        }

        tvPerfil.setOnClickListener {
            val perfilIntent = Intent(this, ActivityPerfil::class.java)
            startActivity(perfilIntent)
        }

        switchActivo.setOnClickListener {
            if(switchActivo.isChecked) {
                switchActivo.text = "On"
                StaticUser.staticUser.isActivo = true
                val reference = database.getReference("/usuarios/${StaticUser.staticUser.id}")
                reference.setValue(StaticUser.staticUser)
            } else {
                switchActivo.text = "Off"
                StaticUser.staticUser.isActivo = false
                val reference = database.getReference("/usuarios/${StaticUser.staticUser.id}")
                reference.setValue(StaticUser.staticUser)
            }
        }


        changeFragments(fragmentChatPrivado(this), "fragment_chatpriv")
        btnChatPriv.textSize = 15.0F
    }

    private fun changeFragments(newFragment: Fragment, etiqueta: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flMenu, newFragment, etiqueta)
            .commit()
    }

    public fun getUser(id : String, tvUsername: TextView, switchActivo: Switch){
        val ref = database.getReference("/usuarios/$id")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentUser: Usuario = snapshot.getValue(Usuario::class.java) as Usuario
                StaticUser.createGlobalUser(currentUser)
                tvUsername.text = StaticUser.staticUser.nombreUsuario
                switchActivo.isChecked = StaticUser.staticUser.isActivo
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}