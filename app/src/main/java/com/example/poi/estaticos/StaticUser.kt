package com.example.poi.estaticos

import android.app.Application
import com.example.poi.modelos.Usuario

class StaticUser() :Application() {
    companion object {
        lateinit var staticUser:Usuario

        fun createGlobalUser(newUser: Usuario) {
            staticUser = Usuario()
            staticUser.id = newUser.id
            staticUser.nombreCompleto = newUser.nombreCompleto
            staticUser.nombreUsuario = newUser.nombreUsuario
            staticUser.telefono = newUser.telefono
            staticUser.email = newUser.email
            staticUser.contrasenia = newUser.contrasenia
        }
    }
}