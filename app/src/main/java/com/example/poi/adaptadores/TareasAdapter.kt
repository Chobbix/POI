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
import com.example.poi.R
import com.example.poi.modelos.Tarea
import com.example.poi.modelos.Usuario

class TareasAdapter(private val tareaList: List<Tarea>, private val contexto: Context):
    RecyclerView.Adapter<TareasAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.diseno_de_tarea, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tareaList[position], contexto)
    }

    override fun getItemCount(): Int = tareaList.size

    class ViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(tarea: Tarea, contexto: Context) {
            val txt_User = itemView.findViewById<TextView>(R.id.tvTituloTarea)

            txt_User.text = tarea.titulo
        }
    }
}