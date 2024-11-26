package com.nestorss.paroomiso.dal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CineDao {
    @Query("SELECT * FROM clientes")
    suspend fun getTodosClientes(): List<ClientesEnt>

    @Query("SELECT * FROM configuracion")
    suspend fun getTodosConfiguracion(): List<ConfiguracionEnt>

    @Query("SELECT COUNT(*) FROM clientes WHERE palomitas = 1")
    suspend fun getClientesConPalomitas(): Int

    @Query("SELECT precioPalomitas FROM configuracion LIMIT 1")
    suspend fun getPrecioPalomitas(): Float

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCliente(cliente: ClientesEnt): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarConfiguracion(configuracion: ConfiguracionEnt): Long

    @Update
    suspend fun actualizarCliente(cliente: ClientesEnt)

    @Update
    suspend fun actualizarConfiguracion(configuracion: ConfiguracionEnt)

    @Query("DELETE FROM clientes")
    suspend fun borrarClientes()

    @Query("DELETE FROM configuracion")
    suspend fun borrarConfiguracion()
}