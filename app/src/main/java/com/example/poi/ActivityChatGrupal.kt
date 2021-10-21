package com.example.poi

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.poi.adaptadores.ChatAdaptador
import com.example.poi.modelos.Mensaje
import com.example.poi.modelos.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ActivityChatGrupal : AppCompatActivity() {

    private val messageList = mutableListOf<Mensaje>()
    private lateinit var chatAdapter: ChatAdaptador
    private lateinit var rvChats: RecyclerView
    private lateinit var licNombre: String
    private var username: String = ""
    private lateinit var licId: String
    private val database = FirebaseDatabase.getInstance()
    private val authen = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        rvChats = findViewById(R.id.rvMsgRecibidos)

        chatAdapter = ChatAdaptador(messageList)
        rvChats.adapter = chatAdapter

        licNombre = intent.getStringExtra("licenciaturaChat") ?: "Sin nombre"
        licId = intent.getStringExtra("keyChat") ?: "Sin nombre"

        val fromId = authen.uid ?: ""
        getUsername(fromId)

        findViewById<Button>(R.id.btn_Enviar).setOnClickListener {
            val message = findViewById<EditText>(R.id.edit_EnviarMsg).text.toString()
            if (message.isNotEmpty()) {
                val fromId = authen.uid ?: ""
                val toId = licId
                val msg = Mensaje("", message, username, ServerValue.TIMESTAMP, fromId, toId)
                sendMessage(msg)
            }
            val editMsg = findViewById<EditText>(R.id.edit_EnviarMsg)
            editMsg.setText("")
        }
        readMessage()
    }

    private fun sendMessage(message: Mensaje) {
        val reference = database.getReference("/chat-group/$licId").push()

        message.id = reference.key ?: ""

        reference.setValue(message)
    }

    private fun readMessage() {
        val fromId = authen.uid ?: ""
        val toId = licId
        database.getReference("/chat-group/$toId").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()

                for(snap in snapshot.children) {
                    val currentMessage: Mensaje = snap.getValue(Mensaje::class.java) as Mensaje
                    messageList.add(currentMessage)
                }

                if(messageList.size > 0) {
                    chatAdapter.notifyDataSetChanged()
                    rvChats.smoothScrollToPosition(messageList.size - 1)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    public fun getUsername(id : String){
        val ref = database.getReference("/usuarios/$id")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentUser: Usuario = snapshot.getValue(Usuario::class.java) as Usuario
                username = currentUser.nombreUsuario
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}