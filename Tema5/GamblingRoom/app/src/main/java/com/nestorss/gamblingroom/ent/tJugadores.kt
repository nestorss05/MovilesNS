package com.nestorss.gamblingroom.ent

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jugadores")
data class tJugadores(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var nombre: String = "",
    var numElegido1: Int = 0,
    var numElegido2: Int = 0,
)
