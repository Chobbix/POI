package com.example.poi.fragmentos

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.poi.R
import com.example.poi.adaptadores.GruposChatAdapter
import com.example.poi.adaptadores.UsuariosAdapter
import com.example.poi.modelos.Licenciatura
import com.example.poi.modelos.Usuario
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class fragmentChatGrupal (contexto : Context) : Fragment(R.layout.activity_lista_mensajes) {

    private val licenciaturaList = mutableListOf<Licenciatura>()
    private lateinit var licenciaturaAdapter: GruposChatAdapter
    private lateinit var rvLicenciaturas: RecyclerView
    private lateinit var username: String
    private val database = FirebaseDatabase.getInstance()
    private val cont = contexto

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvLicenciaturas = view.findViewById(R.id.rv_usuarios)
        licenciaturaAdapter = GruposChatAdapter(licenciaturaList, cont)
        rvLicenciaturas.adapter = licenciaturaAdapter

        fetchLicenciaturas()
    }

    private fun fetchLicenciaturas() {
        val ref = database.getReference("/licenciaturas")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                licenciaturaList.clear()

                for(snap in snapshot.children) {
                    val currentLicenciatura: Licenciatura = snap.getValue(Licenciatura::class.java) as Licenciatura
                    licenciaturaList.add(currentLicenciatura)
                }

                if(licenciaturaList.size > 0) {
                    licenciaturaAdapter.notifyDataSetChanged()
                    rvLicenciaturas.smoothScrollToPosition(licenciaturaList.size - 1)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}