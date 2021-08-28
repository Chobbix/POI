package com.example.poi.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.poi.R

class ChatAdaptador: RecyclerView.Adapter<ChatAdaptador.ChatViewHolder>() {

    class ChatViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val miView = LayoutInflater.from(parent.context)
            .inflate(R.layout.mensajes_recibidos_chat, parent, false)

        return ChatViewHolder(miView)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val tvNombre = holder.itemView.findViewById<TextView>(R.id.tv_NombreMsg)
        val tvMensaje = holder.itemView.findViewById<TextView>(R.id.tv_TxtMsg)
        val tvHora = holder.itemView.findViewById<TextView>(R.id.tv_HoraMsg)

        tvNombre.text = when (position) {
            0 -> "A"
            else -> "ZZZZ"
        }
    }
}