package com.nestorss.paroomiso.ent

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "configuracion")
data class ConfiguracionEnt(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var numSalas: Int = 0,
    var numAsientos: Int = 0,
    var precioPalomitas: Float = 0f,
)
