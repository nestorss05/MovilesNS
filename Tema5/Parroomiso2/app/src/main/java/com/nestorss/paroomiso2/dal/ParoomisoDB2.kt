package com.nestorss.paroomiso2.dal

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nestorss.paroomiso2.ent.tClientes
import com.nestorss.paroomiso2.ent.tConfiguracion

@Database(entities = [tClientes::class, tConfiguracion::class], version=1)
abstract class ParoomisoDB2: RoomDatabase() {
    abstract fun clientesDao(): ClientesDao
    abstract fun configuracionDao(): ConfiguracionDao
}