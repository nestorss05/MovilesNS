package com.example.contactosjpc.dal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContactoDao {
    @Query("SELECT * FROM contactos")
    suspend fun getTodo(): List<ContactoEnt>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(contacto: ContactoEnt): Long

    @Query("SELECT EXISTS(SELECT 1 FROM contactos WHERE nombre = :nombre)")
    suspend fun getNombre(nombre: String): Boolean
}