package com.example.poi

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.poi.fragmentos.fragmentChatGrupal
import com.example.poi.fragmentos.fragmentChatPrivado

class ActivityMenuChats : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_chats)

        val btnChatPriv = findViewById<Button>(R.id.btnChatPrivMenu)
        val btnChatGrupal = findViewById<Button>(R.id.btnChatGrupMenu)

        btnChatPriv.setOnClickListener {
            changeFragments(fragmentChatPrivado(this), "fragment_chatpriv")
            btnChatPriv.textSize = 15.0F
            btnChatGrupal.textSize = 12.0F
        }

        btnChatGrupal.setOnClickListener {
            changeFragments(fragmentChatGrupal(), "fragment_chatgrupal")
            btnChatGrupal.textSize = 15.0F
            btnChatPriv.textSize = 12.0F
        }

        changeFragments(fragmentChatPrivado(this), "fragment_chatpriv")
        btnChatPriv.textSize = 15.0F
    }

    private fun changeFragments(newFragment: Fragment, etiqueta: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flMenu, newFragment, etiqueta)
            .commit()
    }
}