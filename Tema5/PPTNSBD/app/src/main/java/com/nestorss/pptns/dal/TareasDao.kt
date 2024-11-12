package com.nestorss.pptns.dal;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TareasDao {
    @Query("SELECT * FROM partidas ORDER BY partidasGanadas desc LIMIT 10")
    suspend fun getTodo(): List<TareaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(tarea: TareaEntity): Long

    @Update
    suspend fun actualizar(tarea: TareaEntity)

    @Query("SELECT * FROM partidas WHERE username = :user")
    suspend fun getContUser(user: String): TareaEntity

    @Query("SELECT EXISTS(SELECT 1 FROM partidas WHERE username = :user)")
    suspend fun getNombre(user: String): Boolean

    @Query("DELETE FROM PARTIDAS")
    suspend fun borrarTodo()
}
