package com.nestorss.gamblingroom.dal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nestorss.gamblingroom.ent.tJugadores

@Dao
interface JugadoresDao {
    @Query("SELECT * FROM jugadores")
    suspend fun getTodo(): List<tJugadores>

    @Query("SELECT * FROM jugadores WHERE id = :id")
    suspend fun getJugador(id: Long): tJugadores

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarJugador(jugador: tJugadores): Long

}