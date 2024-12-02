package com.nestorss.paroomiso2.ent

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clientes")
data class tClientes(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var salaElegida: Int = 0,
    var palomitas: Int = 0,
)
