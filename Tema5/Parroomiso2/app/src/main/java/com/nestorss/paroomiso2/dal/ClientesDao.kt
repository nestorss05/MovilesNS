package com.nestorss.paroomiso2.dal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nestorss.paroomiso2.ent.tClientes

@Dao
interface ClientesDao {
    @Query("SELECT COUNT(*) as numCliente FROM clientes")
    suspend fun cuantosClientes(): Int

    @Query("SELECT COUNT(*) as numCliente FROM clientes WHERE salaElegida == :idSala ")
    suspend fun cuantosClientesEnSala(idSala: Int): Int

    @Query("SELECT COUNT(*) as cliente FROM clientes WHERE palomitas == 1")
    suspend fun cuantosPalomitas(): Int

    @Query("SELECT COUNT(*) as cliente FROM clientes WHERE palomitas == 1 and salaElegida == :idSala ")
    suspend fun cuantosPalomitasEnSala(idSala: Int): Int

    @Query("SELECT COUNT(*) as cliente FROM clientes WHERE salaElegida == 0")
    suspend fun cuantosSinSala(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun anadeCliente(cliente: tClientes)

    @Query("DELETE FROM Clientes")
    suspend fun borrarClientes()
}