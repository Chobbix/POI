package com.example.poi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.poi.adaptadores.UsuariosAdapter
import com.example.poi.modelos.Usuario
import com.google.firebase.database.*

class ActivityUserChats : AppCompatActivity() {

    private val userList = mutableListOf<Usuario>()
    private lateinit var userAdapter: UsuariosAdapter
    private lateinit var rvUsers: RecyclerView
    private lateinit var username: String
    private val database = FirebaseDatabase.getInstance()
    //private val userChatIntent = Intent(this, ActivityChat::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_mensajes)

        rvUsers = findViewById(R.id.rv_usuarios)


        userAdapter = UsuariosAdapter(userList, this)
        rvUsers.adapter = userAdapter

        fetchUsers()
    }

    private fun fetchUsers() {
        val ref = database.getReference("/usuarios")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()

                for(snap in snapshot.children) {
                    val currentUser: Usuario = snap.getValue(Usuario::class.java) as Usuario
                    userList.add(currentUser)
                }

                if(userList.size > 0) {
                    userAdapter.notifyDataSetChanged()
                    rvUsers.smoothScrollToPosition(userList.size - 1)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}