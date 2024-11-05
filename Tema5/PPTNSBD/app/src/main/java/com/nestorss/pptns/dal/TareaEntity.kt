package com.nestorss.pptns.dal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "partidas")
data class TareaEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var resultadoJ1: Int = 0,
    var resultadoJ2: Int = 0,
    var ganador: Int = 0
)
