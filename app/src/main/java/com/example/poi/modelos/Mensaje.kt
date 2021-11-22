package com.example.poi.modelos

data class Mensaje(
    var id: String= "",
    var content: String = "",
    var name: String = "",
    val date: Any? = null,
    val fromid: String= "",
    val toid: String= "",
    var isEncripted: Boolean= false
)