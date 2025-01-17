package com.nestorss.paroomiso.dal

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nestorss.paroomiso.ent.ClientesEnt
import com.nestorss.paroomiso.ent.ConfiguracionEnt

@Database(entities = [ClientesEnt::class, ConfiguracionEnt::class], version = 1)
abstract class CineDB: RoomDatabase() {
    abstract fun cineDao(): CineDao
}