package com.example.poi.estaticos

import android.app.Application
import com.example.poi.modelos.Usuario

class StaticUser() :Application() {
    companion object {
        lateinit var staticUser:Usuario

        fun createGlobalUser(newUser: Usuario) {
            staticUser = Usuario()
            staticUser = newUser
        }
    }
}