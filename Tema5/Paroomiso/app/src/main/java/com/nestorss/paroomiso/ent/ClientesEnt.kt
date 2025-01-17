package com.nestorss.paroomiso.ent

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clientes")
data class ClientesEnt(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var salaElegida: Int = 0,
    var palomitas: Int = 0,
)
