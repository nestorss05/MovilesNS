package com.nestorss.gamblingroom.ent

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sorteos")
data class tSorteos(
    @PrimaryKey(autoGenerate = true)
    var idSorteo: Long = 0,
    var numGanador1: Int = 0,
    var numGanador2: Int = 0,
    var idGanador: Int = 0
)