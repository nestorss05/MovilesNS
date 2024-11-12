package com.nestorss.pptns.dal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "partidas")
data class TareaEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var username: String = "",
    var partidasJugadas: Int = 0,
    var partidasGanadas: Int = 0,
    var luchasGanadas: Int = 0
)
