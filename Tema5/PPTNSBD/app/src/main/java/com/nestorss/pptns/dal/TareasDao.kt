package com.nestorss.pptns.dal;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TareasDao {
    @Query("SELECT * FROM partidas")
    suspend fun getTodo(): List<TareaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(tarea: TareaEntity): Long

    @Update
    suspend fun actualizar(tarea: TareaEntity)

    @Query("SELECT * FROM partidas WHERE username = :user")
    suspend fun getNombre(user: String): List<TareaEntity>

    @Query("DELETE FROM PARTIDAS")
    suspend fun borrarTodo()
}
