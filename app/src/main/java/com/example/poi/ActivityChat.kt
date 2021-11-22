package com.example.poi

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.poi.adaptadores.ChatAdaptador
import com.example.poi.encriptado.Encriptado_Mensajes
import com.example.poi.estaticos.StaticUser
import com.example.poi.modelos.Mensaje
import com.example.poi.modelos.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.util.*

class ActivityChat : AppCompatActivity() {

    private val SELECT_IMAGE = 10
    private val messageList = mutableListOf<Mensaje>()
    private lateinit var chatAdapter: ChatAdaptador
    private lateinit var rvChats: RecyclerView
    private var username: String = ""
    private lateinit var usernameChat: String
    private lateinit var chatKey: String
    private val database = FirebaseDatabase.getInstance()
    private val authen = FirebaseAuth.getInstance()
    private var imageUri: Uri? = null
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    lateinit var fileName: String
    lateinit var imgFirebase: Mensaje

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        rvChats = findViewById(R.id.rvMsgRecibidos)
        chatAdapter = ChatAdaptador(messageList)
        rvChats.adapter = chatAdapter

        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference

        usernameChat = intent.getStringExtra("usernameChat") ?: "Sin nombre"
        chatKey = intent.getStringExtra("keyChat") ?: "Sin nombre"

        val fromId = authen.uid ?: ""
        getUsername(fromId)

        Log.d("Main", "$usernameChat")
        Log.d("Main", "$chatKey")

        val btnFoto = findViewById<Button>(R.id.btn_sendphoto)
        btnFoto.setOnClickListener {

            val item: Array<CharSequence> = arrayOf("Imagen", "Documento")

            val alert = AlertDialog.Builder(this)
            alert.setTitle("Selecciona la opcion")
            alert.setNeutralButton("Imagenes") {
                dialog, which ->
                    cargarImagen(this, SELECT_IMAGE)
            }
            alert.show()
        }

        findViewById<TextView>(R.id.tvUsernameChat).text = usernameChat

        findViewById<Button>(R.id.btn_Enviar).setOnClickListener {
            val message = findViewById<EditText>(R.id.edit_EnviarMsg).text.toString()
            if (message.isNotEmpty()) {

                var encriptacion = "";
                var isMsgEncripted = false
                if(StaticUser.staticUser.isEncripted) {
                    isMsgEncripted = true
                    encriptacion = Encriptado_Mensajes.encrypt(message)!!
                } else {
                    encriptacion = message
                }

                val toId = chatKey
                val msg = Mensaje("", encriptacion, username, ServerValue.TIMESTAMP, fromId, toId, isMsgEncripted)
                sendMessage(msg)
            }
            val editMsg = findViewById<EditText>(R.id.edit_EnviarMsg)
            editMsg.setText("")
        }
        readMessage()
    }

    private fun sendMessage(message: Mensaje) {
        val reference = database.getReference("/chat-user-to-user/${message.fromid}/${message.toid}").push()
        val toReference = database.getReference("/chat-user-to-user/${message.toid}/${message.fromid}").push()

        message.id = reference.key ?: ""

        reference.setValue(message)
        toReference.setValue(message)
    }

    private fun readMessage() {
        val fromId = authen.uid ?: ""
        val toId = chatKey
        database.getReference("/chat-user-to-user/$fromId/$toId").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()

                for(snap in snapshot.children) {
                    val currentMessage: Mensaje = snap.getValue(Mensaje::class.java) as Mensaje
                    if(currentMessage.fromid == authen.uid || currentMessage.toid == authen.uid) {
                        messageList.add(currentMessage)
                    }
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

    private fun cargarImagen(activity: Activity, code: Int) {
        val imgIntent = Intent(Intent.ACTION_PICK)
        imgIntent.type = "image/*"
        activity.startActivityForResult(imgIntent, code)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when {
            requestCode == SELECT_IMAGE && resultCode == Activity.RESULT_OK -> {
                imageUri = data!!.data
                subirimagen(imageUri!!)
            }
        }
    }

    private fun subirimagen(image: Uri) {
        fileName = UUID.randomUUID().toString()
        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")
        storageReference.putFile(image).addOnSuccessListener {
            storageReference.downloadUrl.addOnSuccessListener {
                fileName = it.toString()
                val toId = chatKey
                val fromid = authen.uid ?: ""
                val msg = Mensaje("", "false", username, ServerValue.TIMESTAMP, fromid, toId, false, true, fileName)
                sendMessage(msg)
            }
        }.addOnFailureListener {
            Toast.makeText(this, "FAILED uploaded", Toast.LENGTH_SHORT).show()
        }
    }
}