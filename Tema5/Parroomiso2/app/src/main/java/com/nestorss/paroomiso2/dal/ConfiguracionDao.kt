package com.nestorss.paroomiso2.dal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nestorss.paroomiso2.ent.tConfiguracion

@Dao
interface ConfiguracionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarConfiguracion(configuracion: tConfiguracion)

    @Query("SELECT * FROM Configuracion Where Id = 1")
    suspend fun sacaConfiguracion():tConfiguracion

    @Query("DELETE FROM configuracion")
    suspend fun borrarConfiguracion()
}