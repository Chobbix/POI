package com.example.poi.adaptadores

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.poi.ActivityChat
import com.example.poi.ActivityChatGrupal
import com.example.poi.R
import com.example.poi.modelos.Mensaje
import com.example.poi.modelos.Usuario
import java.text.SimpleDateFormat
import java.util.*

class UsuariosAdapter (private val userList: List<Usuario>, private val contexto: Context):
    RecyclerView.Adapter<UsuariosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.lista_usuario, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userList[position], contexto)
    }

    override fun getItemCount(): Int = userList.size

    class ViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: Usuario, contexto: Context) {
            val txt_User = itemView.findViewById<TextView>(R.id.tv_Username)
            txt_User.text = user.nombreUsuario

            itemView.setOnClickListener {
                Log.d("Main", "${user.nombreUsuario}")
                Log.d("Main", "$contexto")
                Log.d("Main", "${itemView.context}")
                val userChatIntent = Intent(contexto, ActivityChat::class.java)
                userChatIntent.putExtra("usernameChat", user.nombreUsuario)
                userChatIntent.putExtra("keyChat", user.id)
                itemView.context.startActivity(userChatIntent)
            }
        }
    }
}