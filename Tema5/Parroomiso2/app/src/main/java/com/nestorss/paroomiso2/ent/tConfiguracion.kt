package com.nestorss.paroomiso2.ent

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "configuracion")
data class tConfiguracion(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var numSalas: Int = 0,
    var numAsientos: Int = 0,
    var precioPalomitas: Float = 0f,
)
