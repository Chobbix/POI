package com.example.poi.adaptadores

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.poi.ActivityChat
import com.example.poi.ActivityChatGrupal
import com.example.poi.R
import com.example.poi.modelos.Licenciatura
import com.example.poi.modelos.Usuario

class GruposChatAdapter (private val Grouplist: List<Licenciatura>, private val contexto: Context):
    RecyclerView.Adapter<GruposChatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.lista_usuario, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(Grouplist[position], contexto)
    }

    override fun getItemCount(): Int = Grouplist.size

    class ViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(licenciatura: Licenciatura, contexto: Context) {
            val txt_User = itemView.findViewById<TextView>(R.id.tv_Username)
            txt_User.text = licenciatura.nombre

            txt_User.setOnClickListener {
                Log.d("Main", "${licenciatura.nombre}")
                Log.d("Main", "$contexto")
                Log.d("Main", "${itemView.context}")
                val userChatIntent = Intent(contexto, ActivityChatGrupal::class.java)
                userChatIntent.putExtra("licenciaturaChat", licenciatura.nombre)
                userChatIntent.putExtra("keyChat", licenciatura.id)
                itemView.context.startActivity(userChatIntent)
            }
        }
    }
}