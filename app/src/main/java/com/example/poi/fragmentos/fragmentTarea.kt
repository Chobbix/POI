package com.example.poi.fragmentos

import android.content.Context
import android.media.Image
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.poi.R
import com.example.poi.adaptadores.TareasAdapter
import com.example.poi.adaptadores.UsuariosAdapter
import com.example.poi.modelos.Tarea
import com.example.poi.modelos.Usuario
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class fragmentTarea(contexto: Context): Fragment(R.layout.activity_assigments) {

    private val tareaList = mutableListOf<Tarea>()
    private lateinit var tareaAdapter: TareasAdapter
    private lateinit var rvTareas: RecyclerView
    private val cont = contexto

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val asigment = Tarea()
        asigment.titulo = "Hola"
        tareaList.add(asigment)

        rvTareas = view.findViewById<RecyclerView>(R.id.rvTareas)
        tareaAdapter = TareasAdapter(tareaList, cont)
        rvTareas.adapter = tareaAdapter

    }
}