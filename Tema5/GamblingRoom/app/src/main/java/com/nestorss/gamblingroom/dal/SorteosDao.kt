package com.nestorss.gamblingroom.dal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nestorss.gamblingroom.ent.tSorteos

@Dao
interface SorteosDao {
    @Query("SELECT * FROM sorteos")
    suspend fun getTodo(): List<tSorteos>

    @Query("SELECT COUNT(*) FROM sorteos WHERE idGanador = :id")
    suspend fun getVecesGanadas(id: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarJugada(sorteo: tSorteos): Long
}