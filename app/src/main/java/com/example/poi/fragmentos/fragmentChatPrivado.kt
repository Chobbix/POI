package com.example.poi.fragmentos

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.poi.R
import com.example.poi.adaptadores.UsuariosAdapter
import com.example.poi.modelos.Usuario
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class fragmentChatPrivado (contexto : Context) : Fragment(R.layout.activity_lista_mensajes){

    private val userList = mutableListOf<Usuario>()
    private lateinit var userAdapter: UsuariosAdapter
    private lateinit var rvUsers: RecyclerView
    private lateinit var username: String
    private val database = FirebaseDatabase.getInstance()
    private val cont = contexto

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvUsers = view.findViewById(R.id.rv_usuarios)
        userAdapter = UsuariosAdapter(userList, cont)
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