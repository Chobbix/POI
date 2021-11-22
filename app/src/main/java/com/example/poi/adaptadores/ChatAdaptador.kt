package com.example.poi.adaptadores

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.poi.R
import com.example.poi.encriptado.Encriptado_Mensajes
import com.example.poi.modelos.Mensaje
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class ChatAdaptador(private val messageList: List<Mensaje>):
    RecyclerView.Adapter<ChatAdaptador.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.mensajes_recibidos_chat, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(messageList[position])
    }

    override fun getItemCount(): Int = messageList.size

    class ViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: Mensaje) {

            if(message.isEncripted)
                message.content = Encriptado_Mensajes.decrypt(message.content)!!

            if (!message.isImage) {
                itemView.findViewById<TextView>(R.id.tv_Username).text = message.content
                val imgView = itemView.findViewById<ImageView>(R.id.imageViewMenssage)
                imgView.visibility = View.INVISIBLE
            } else {
                itemView.findViewById<TextView>(R.id.tv_Username).visibility = View.INVISIBLE
                val imgView = itemView.findViewById<ImageView>(R.id.imageViewMenssage)
                Picasso.get().load(message.urlFile).into(imgView)
                imgView.visibility = View.VISIBLE
            }

            itemView.findViewById<TextView>(R.id.tv_NombreMsg).text = message.name
            val tvDate = itemView.findViewById<TextView>(R.id.tv_HoraMsg)
            val date = message.date as Long
            tvDate.text = SimpleDateFormat("dd/MM/yyyy - HH:mm:ss", Locale("es", "MX")).format(date)

            val messageContainer = itemView.findViewById<LinearLayout>(R.id.message_container)
            val params = messageContainer.layoutParams

            var newParams = FrameLayout.LayoutParams(params.width, params.height, Gravity.START)

            if (FirebaseAuth.getInstance().uid == message.fromid)
                newParams = FrameLayout.LayoutParams(params.width, params.height, Gravity.END)

            messageContainer.layoutParams = newParams
        }
    }
}