package com.example.poi

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.example.poi.fragmentos.*

class ActivityFragment : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_muro)
        val btn_chat = findViewById<Button>(R.id.btn_chat)
        val btn_grupo = findViewById<Button>(R.id.btn_grupo)
        val btn_tarea = findViewById<Button>(R.id.btn_tarea)

        btn_chat.setOnClickListener{
           cambiarfragmentos(fragmentchat(),"fragmentochat")
        }
        btn_grupo.setOnClickListener{
            cambiarfragmentos(fragmentGrupos(),"fragmentochat")
        }
        btn_tarea.setOnClickListener{
            cambiarfragmentos(fragmentTarea(),"fragmentoTarea")
        }
    }
    private fun  cambiarfragmentos(fragmentoNuevo: Fragment, etiqueta:String){
        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedor, fragmentoNuevo,etiqueta)
            .commit()
    }
}