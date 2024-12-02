package com.nestorss.paroomiso.dal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nestorss.paroomiso.ent.ClientesEnt
import com.nestorss.paroomiso.ent.ConfiguracionEnt

@Dao
interface CineDao {
    @Query("SELECT * FROM clientes")
    suspend fun getTodosClientes(): List<ClientesEnt>

    @Query("SELECT * FROM configuracion LIMIT 1")
    suspend fun getTodosConfiguracion(): ConfiguracionEnt

    @Query("SELECT COUNT(*) FROM clientes WHERE palomitas = 1")
    suspend fun getClientesConPalomitas(): Int

    @Query("SELECT COUNT(*) FROM clientes")
    suspend fun getCantClientes(): Int

    @Query("SELECT COUNT(*) FROM clientes WHERE salaElegida != 0")
    suspend fun getClientesDentro(): Int

    @Query("SELECT COUNT(*) FROM clientes WHERE salaElegida = 0")
    suspend fun getClientesFuera(): Int

    @Query("SELECT precioPalomitas FROM configuracion LIMIT 1")
    suspend fun getPrecioPalomitas(): Float

    @Query("SELECT numSalas FROM configuracion LIMIT 1")
    suspend fun getNumSalas(): Int

    @Query("SELECT numAsientos FROM configuracion LIMIT 1")
    suspend fun getNumAsientos(): Int

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