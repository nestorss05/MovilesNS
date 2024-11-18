package com.example.contactosjpc.dal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contactos")
data class ContactoEnt(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var nombre: String = "",
    var apellidos: String = "",
    var telefono: Long = 0,
    var foto: String = ""
)
