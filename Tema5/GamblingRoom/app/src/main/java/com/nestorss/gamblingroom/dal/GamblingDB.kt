package com.nestorss.gamblingroom.dal

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nestorss.gamblingroom.ent.tJugadores
import com.nestorss.gamblingroom.ent.tSorteos

@Database(entities = [tJugadores::class, tSorteos::class], version=1)
abstract class GamblingDB: RoomDatabase() {
    abstract fun jugadorDao(): JugadoresDao
    abstract fun sorteoDao(): SorteosDao
}