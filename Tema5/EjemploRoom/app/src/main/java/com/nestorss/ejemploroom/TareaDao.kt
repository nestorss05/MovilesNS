package com.nestorss.ejemploroom

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TareaDao {
    @Query("SELECT * FROM tareas")
    suspend fun getAll(): List<TareaEntity>
    @Query("SELECT * FROM tareas WHERE id = :id")
    suspend fun get(id: Long): TareaEntity
    @Insert
    suspend fun insert(tarea: TareaEntity): Long
    @Update
    suspend fun update(tarea: TareaEntity)
    @Delete
    suspend fun delete(tarea: TareaEntity)
}