package com.nestorss.ejercicio1.dal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tareas")
data class TareaEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var nombre: String = "",
    var hecho: Boolean = false
)
